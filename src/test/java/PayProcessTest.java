import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.conn.DBConnect;
import com.servlet.payprocess;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class PayProcessTest {

    private static final String VALID_CUSTOMER_NAME = "TestCustomer";
    private static final String VALID_CITY = "TestCity";
    private static final String VALID_TOTAL = "100";
    private static final String VALID_PRODUCT_NAME = "TestProduct";
    private static final String EMPTY_CUSTOMER_NAME = "empty";
    private static final String ORDERS_JSP = "orders.jsp";
    private static final String PAYMENT_FAIL_JSP = "paymentfail.jsp";

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    private ResultSet resultSet;

    private payprocess servlet;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        servlet = new payprocess();
    }

    @Test
    void shouldProcessPaymentSuccessfullyForEmptyCustomer() throws Exception {
        setupValidRequestParameters(EMPTY_CUSTOMER_NAME);
        try (MockedStatic<DBConnect> dbConnect = mockStatic(DBConnect.class)) {
            dbConnect.when(DBConnect::getConn).thenReturn(connection);
            when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            when(resultSet.next()).thenReturn(true);
            when(preparedStatement.executeUpdate()).thenReturn(1);
            servlet.doPost(request, response);
            verify(response).sendRedirect(ORDERS_JSP);
        }
    }

    @Test
    void shouldProcessPaymentSuccessfullyForSpecificCustomer() throws Exception {
        setupValidRequestParameters(VALID_CUSTOMER_NAME);
        try (MockedStatic<DBConnect> dbConnect = mockStatic(DBConnect.class)) {
            dbConnect.when(DBConnect::getConn).thenReturn(connection);
            when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            when(resultSet.next()).thenReturn(true);
            when(preparedStatement.executeUpdate()).thenReturn(1);
            servlet.doPost(request, response);
            verify(response).sendRedirect(ORDERS_JSP);
        }
    }

    @Test
    void shouldRedirectToPaymentFailWhenCityIsNull() throws Exception {
        setupRequestParameters(VALID_CUSTOMER_NAME, "null", VALID_TOTAL, VALID_PRODUCT_NAME);
        servlet.doPost(request, response);
        verify(response).sendRedirect(PAYMENT_FAIL_JSP + "?msgf=Select any item first.");
    }

    @Test
    void shouldRedirectToPaymentFailWhenTotalIsNull() throws Exception {
        setupRequestParameters(VALID_CUSTOMER_NAME, VALID_CITY, "null", VALID_PRODUCT_NAME);
        servlet.doPost(request, response);
        verify(response).sendRedirect(PAYMENT_FAIL_JSP + "?msgf=Select any item first.");
    }

    @Test
    void shouldRedirectToPaymentFailWhenCartIsEmptyForEmptyCustomer() throws Exception {
        setupValidRequestParameters(EMPTY_CUSTOMER_NAME);
        try (MockedStatic<DBConnect> dbConnect = mockStatic(DBConnect.class)) {
            dbConnect.when(DBConnect::getConn).thenReturn(connection);
            when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            when(resultSet.next()).thenReturn(false);
            servlet.doPost(request, response);
            verify(response).sendRedirect(PAYMENT_FAIL_JSP + "?msgf=Add items to cart first(empty).");
        }
    }

    @Test
    void shouldRedirectToPaymentFailWhenCartIsEmptyForSpecificCustomer() throws Exception {
        setupValidRequestParameters(VALID_CUSTOMER_NAME);
        try (MockedStatic<DBConnect> dbConnect = mockStatic(DBConnect.class)) {
            dbConnect.when(DBConnect::getConn).thenReturn(connection);
            when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            when(resultSet.next()).thenReturn(false);
            servlet.doPost(request, response);
            verify(response).sendRedirect(PAYMENT_FAIL_JSP + "?msgf=Add items to cart first.");
        }
    }

    @Test
    void shouldHandleDatabaseExceptionGracefully() throws Exception {
        setupValidRequestParameters(VALID_CUSTOMER_NAME);
        try (MockedStatic<DBConnect> dbConnect = mockStatic(DBConnect.class)) {
            dbConnect.when(DBConnect::getConn).thenReturn(connection);
            when(connection.prepareStatement(anyString())).thenThrow(new RuntimeException("Database error"));
            servlet.doPost(request, response);
            verify(response).sendRedirect(PAYMENT_FAIL_JSP + "?msgf=Add items to cart first.");
        }
    }

    @Test
    void shouldHandleInvalidTotalPriceFormat() throws Exception {
        setupRequestParameters(VALID_CUSTOMER_NAME, VALID_CITY, "invalid", VALID_PRODUCT_NAME);
        assertThrows(NumberFormatException.class, () -> {
            servlet.doPost(request, response);
        });
    }

    @Test
    void shouldHandleNullParameters() throws Exception {
        when(request.getParameter("CName")).thenReturn(null);
        
        assertThrows(NullPointerException.class, () -> {
            servlet.doPost(request, response);
        });
    }

    @Test
    void shouldHandleEmptyStringParameters() throws Exception {
        when(request.getParameter("CName")).thenReturn("");
        when(request.getParameter("CusName")).thenReturn("");
        when(request.getParameter("City")).thenReturn("");
        when(request.getParameter("Total")).thenReturn("");
        when(request.getParameter("N2")).thenReturn("");
        
        assertThrows(NumberFormatException.class, () -> {
            servlet.doPost(request, response);
        });
    }

    private void setupValidRequestParameters(String customerName) {
        setupRequestParameters(customerName, VALID_CITY, VALID_TOTAL, VALID_PRODUCT_NAME);
    }

    private void setupRequestParameters(String customerName, String city, String total, String productName) {
        when(request.getParameter("CName")).thenReturn(" " + customerName + " ");
        when(request.getParameter("CusName")).thenReturn(customerName);
        when(request.getParameter("City")).thenReturn(" " + city + " ");
        when(request.getParameter("Total")).thenReturn(total);
        when(request.getParameter("N2")).thenReturn(" " + productName + " ");
    }
} 