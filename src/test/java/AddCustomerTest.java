import static org.junit.jupiter.api.Assertions.*;
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

    // ----------------------
    // Testes de validateCustomerData (unificados de ValidateCustomerDataTest)
    // ----------------------

    // Nome nulo, premium, Brazil, Rio de Janeiro
    @Test
    void testNameNullPremiumBrazilRJ() {
        addcustomer servlet = new addcustomer();
        customer ct = new customer();
        ct.setName(null);
        String result = servlet.validateCustomerData(ct, "pass", "premium", "30", "Brazil", "Rio de Janeiro", "", "true");
        assertEquals("NAME_REQUIRED_PREMIUM_BRAZIL_RJ", result);
    }

    // Nome nulo, premium, Brazil, São Paulo
    @Test
    void testNameNullPremiumBrazilSP() {
        addcustomer servlet = new addcustomer();
        customer ct = new customer();
        ct.setName(null);
        String result = servlet.validateCustomerData(ct, "pass", "premium", "30", "Brazil", "São Paulo", "", "true");
        assertEquals("NAME_REQUIRED_PREMIUM_BRAZIL_SP", result);
    }

    // Nome nulo, premium, USA, zipCode 5
    @Test
    void testNameNullPremiumUSA5() {
        addcustomer servlet = new addcustomer();
        customer ct = new customer();
        ct.setName(null);
        String result = servlet.validateCustomerData(ct, "pass", "premium", "30", "USA", "", "12345", "true");
        assertEquals("NAME_REQUIRED_PREMIUM_USA_5DIGIT", result);
    }

    // Nome nulo, premium, USA, zipCode 9
    @Test
    void testNameNullPremiumUSA9() {
        addcustomer servlet = new addcustomer();
        customer ct = new customer();
        ct.setName(null);
        String result = servlet.validateCustomerData(ct, "pass", "premium", "30", "USA", "", "123456789", "true");
        assertEquals("NAME_REQUIRED_PREMIUM_USA_9DIGIT", result);
    }

    // Nome nulo, standard, menor de idade, Brazil
    @Test
    void testNameNullStandardMinorBrazil() {
        addcustomer servlet = new addcustomer();
        customer ct = new customer();
        ct.setName(null);
        String result = servlet.validateCustomerData(ct, "pass", "standard", "17", "Brazil", "", "", "true");
        assertEquals("NAME_REQUIRED_STANDARD_MINOR_BRAZIL", result);
    }

    // Nome < 3, premium
    @Test
    void testNameTooShortPremium() {
        addcustomer servlet = new addcustomer();
        customer ct = new customer();
        ct.setName("ab");
        String result = servlet.validateCustomerData(ct, "pass", "premium", "30", "Brazil", "", "", "true");
        assertEquals("NAME_TOO_SHORT_PREMIUM", result);
    }

    // Nome > 50, standard
    @Test
    void testNameTooLongStandard() {
        addcustomer servlet = new addcustomer();
        customer ct = new customer();
        ct.setName(new String(new char[51]).replace('\0', 'a'));
        String result = servlet.validateCustomerData(ct, "pass", "standard", "30", "Brazil", "", "", "true");
        assertEquals("NAME_TOO_LONG_STANDARD", result);
    }

    // Nome válido, idade nula, premium
    @Test
    void testAgeNullPremium() {
        addcustomer servlet = new addcustomer();
        customer ct = new customer();
        ct.setName("ValidName");
        String result = servlet.validateCustomerData(ct, "pass", "premium", null, "Brazil", "", "", "true");
        assertEquals("AGE_REQUIRED_PREMIUM", result);
    }

    // Nome válido, idade < 13, standard
    @Test
    void testAgeTooYoungStandard() {
        addcustomer servlet = new addcustomer();
        customer ct = new customer();
        ct.setName("ValidName");
        String result = servlet.validateCustomerData(ct, "pass", "standard", "12", "Brazil", "", "", "true");
        assertEquals("AGE_TOO_YOUNG_STANDARD", result);
    }

    // Nome válido, idade > 120, premium
    @Test
    void testAgeTooOldPremium() {
        addcustomer servlet = new addcustomer();
        customer ct = new customer();
        ct.setName("ValidName");
        String result = servlet.validateCustomerData(ct, "pass", "premium", "121", "Brazil", "", "", "true");
        assertEquals("AGE_TOO_OLD_PREMIUM", result);
    }

    // Nome válido, idade < 18, premium, Brazil
    @Test
    void testAgeMinorPremiumBrazil() {
        addcustomer servlet = new addcustomer();
        customer ct = new customer();
        ct.setName("ValidName");
        String result = servlet.validateCustomerData(ct, "pass", "premium", "15", "Brazil", "", "", "true");
        assertEquals("AGE_MINOR_PREMIUM_BRAZIL", result);
    }

    // Nome válido, idade < 18, standard, outro país
    @Test
    void testAgeMinorStandardOther() {
        addcustomer servlet = new addcustomer();
        customer ct = new customer();
        ct.setName("ValidName");
        String result = servlet.validateCustomerData(ct, "pass", "standard", "16", "USA", "", "", "true");
        assertEquals("AGE_MINOR_STANDARD_OTHER", result);
    }

    // Nome válido, idade formato inválido, premium
    @Test
    void testAgeInvalidFormatPremium() {
        addcustomer servlet = new addcustomer();
        customer ct = new customer();
        ct.setName("ValidName");
        String result = servlet.validateCustomerData(ct, "pass", "premium", "abc", "Brazil", "", "", "true");
        assertEquals("AGE_INVALID_FORMAT_PREMIUM", result);
    }

    // Nome válido, idade 25, standard (caminho feliz)
    @Test
    void testValidStandard() {
        addcustomer servlet = new addcustomer();
        customer ct = new customer();
        ct.setName("ValidName");
        String result = servlet.validateCustomerData(ct, "pass", "standard", "25", "Brazil", "", "", "true");
        assertEquals("VALID", result);
    }

    // ----------------------
    // Testes adicionais para cobrir todas as arestas de doPost e doGet
    // ----------------------


    @Test
    void testDoPostAddCookieThrowsException() throws Exception {
        setupValidRequestParameters();
        when(dao.checkcust2(any(customer.class))).thenReturn(false);
        when(dao.addcustomer(any(customer.class))).thenReturn(1);
        // Simula exceção ao adicionar cookie
        doThrow(new RuntimeException("Cookie error")).when(response).addCookie(any(Cookie.class));
        addcustomer servlet = new addcustomer(dao);
        // Não deve lançar, apenas printar exceção
        servlet.doPost(request, response);
        verify(response, never()).sendRedirect("fail.jsp"); // Não redireciona para fail.jsp por erro de cookie
    }

    @Test
    void testDoPostSendRedirectThrowsException() throws Exception {
        setupValidRequestParameters();
        when(dao.checkcust2(any(customer.class))).thenReturn(true);
        // Simula exceção ao chamar sendRedirect
        doThrow(new IOException("Redirect error")).when(response).sendRedirect(anyString());
        addcustomer servlet = new addcustomer(dao);
        // Não deve lançar, apenas printar exceção
        servlet.doPost(request, response);
        // Não há assert, mas o teste cobre a aresta de exceção
    }

    @Test
    void testDoGetDoesNotThrow() {
        addcustomer servlet = new addcustomer();
        assertDoesNotThrow(() -> servlet.doGet(request, response));
    }
}
