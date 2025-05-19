import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.sql.Connection;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
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
import com.dao.DAO2;
import com.entity.customer;
import com.servlet.addcustomer;

@ExtendWith(MockitoExtension.class)
class AddCustomerTest {

    private static final String VALID_USERNAME = "testUser";
    private static final String VALID_PASSWORD = "testPass";
    private static final String VALID_EMAIL = "test@test.com";
    private static final String VALID_CONTACT = "1234567890";
    private static final String VALID_TOTAL = "100";
    private static final String VALID_CUSTOMER_NAME = "TestCustomer";

    private static final String FAIL_JSP = "fail.jsp";
    private static final String SUCCESS_REDIRECT_TEMPLATE = "customerlogin.jsp?Total=%s&CusName=%s";

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private DAO2 dao;

    @Mock
    private Connection connection;

    private addcustomer servlet;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        servlet = new addcustomer(dao);

        try (MockedStatic<DBConnect> dbConnect = mockStatic(DBConnect.class)) {
            dbConnect.when(DBConnect::getConn).thenReturn(connection);
        }
    }

    @Test
    void shouldSuccessfullyRegisterNewCustomer() throws ServletException, IOException {
        // Arrange
        setupValidRequestParameters();
        setupSuccessfulRegistrationMocks();

        // Act
        servlet.doPost(request, response);

        // Assert
        verify(response).addCookie(any(Cookie.class));
        verify(response).sendRedirect(String.format(
                SUCCESS_REDIRECT_TEMPLATE, VALID_TOTAL, VALID_CUSTOMER_NAME));
    }

    @Test
    void shouldRedirectToFailWhenCustomerAlreadyExists() throws ServletException, IOException {
        // Arrange
        setupValidRequestParameters();
        when(dao.checkcust2(any(customer.class))).thenReturn(true);

        // Act
        servlet.doPost(request, response);

        // Assert
        verify(response).sendRedirect(FAIL_JSP);
        verify(dao, never()).addcustomer(any(customer.class));
    }

    @Test
    void shouldRedirectToFailWhenRegistrationFails() throws ServletException, IOException {
        // Arrange
        setupValidRequestParameters();
        when(dao.checkcust2(any(customer.class))).thenReturn(false);
        when(dao.addcustomer(any(customer.class))).thenReturn(0);

        // Act
        servlet.doPost(request, response);

        // Assert
        verify(response).sendRedirect(FAIL_JSP);
    }

    @Test
    void shouldHandleDatabaseException() throws ServletException, IOException {
        // Arrange
        setupValidRequestParameters();
        when(dao.checkcust2(any(customer.class)))
                .thenThrow(new RuntimeException("Database error"));

        // Act
        servlet.doPost(request, response);

        // Assert
        verify(response, never()).sendRedirect(anyString());
    }

    @Test
    void shouldHandleInvalidContactNumber() throws ServletException, IOException {
        // Arrange
        setupRequestParameters(
                VALID_USERNAME,
                VALID_PASSWORD,
                VALID_EMAIL,
                "invalid",
                VALID_TOTAL,
                VALID_CUSTOMER_NAME
        );

        // Act & Assert
        try {
            servlet.doPost(request, response);
        } catch (NumberFormatException e) {
            // Expected exception
        }
    }

    private void setupValidRequestParameters() {
        setupRequestParameters(
                VALID_USERNAME,
                VALID_PASSWORD,
                VALID_EMAIL,
                VALID_CONTACT,
                VALID_TOTAL,
                VALID_CUSTOMER_NAME
        );
    }

    private void setupRequestParameters(
            String username,
            String password,
            String email,
            String contactNo,
            String total,
            String cusName) {
        when(request.getParameter("Username")).thenReturn(" " + username + " ");
        when(request.getParameter("Password")).thenReturn(" " + password + " ");
        when(request.getParameter("Email_Id")).thenReturn(" " + email + " ");
        when(request.getParameter("Contact_No")).thenReturn(" " + contactNo + " ");
        when(request.getParameter("Total")).thenReturn(total);
        when(request.getParameter("CusName")).thenReturn(cusName);
    }

    private void setupSuccessfulRegistrationMocks() {
        when(dao.checkcust2(any(customer.class))).thenReturn(false);
        when(dao.addcustomer(any(customer.class))).thenReturn(1);
    }
}
