import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.entity.customer;
import com.servlet.addcustomer;
import com.servlet.addcustomer.CustomerValidationContext;


@ExtendWith(MockitoExtension.class)
class AddCustomerTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    private addcustomer servlet;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        servlet = new addcustomer();
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
        CustomerValidationContext ctx = new CustomerValidationContext("premium", "30", "Brazil", "Rio de Janeiro", "");
        String result = servlet.validateCustomerData(ct, ctx);
        assertEquals("NAME_REQUIRED_PREMIUM_BRAZIL_RJ", result);
    }

    // Nome nulo, premium, Brazil, São Paulo
    @Test
    void testNameNullPremiumBrazilSP() {
        addcustomer servlet = new addcustomer();
        customer ct = new customer();
        ct.setName(null);
        CustomerValidationContext ctx = new CustomerValidationContext("premium", "30", "Brazil", "São Paulo", "");
        String result = servlet.validateCustomerData(ct, ctx);
        assertEquals("NAME_REQUIRED_PREMIUM_BRAZIL_SP", result);
    }

    // Nome nulo, premium, USA, zipCode 5
    @Test
    void testNameNullPremiumUSA5() {
        addcustomer servlet = new addcustomer();
        customer ct = new customer();
        ct.setName(null);
        CustomerValidationContext ctx = new CustomerValidationContext("premium", "30", "USA", "", "12345");
        String result = servlet.validateCustomerData(ct, ctx);
        assertEquals("NAME_REQUIRED_PREMIUM_USA_5DIGIT", result);
    }

    // Nome nulo, premium, USA, zipCode 9
    @Test
    void testNameNullPremiumUSA9() {
        addcustomer servlet = new addcustomer();
        customer ct = new customer();
        ct.setName(null);
        CustomerValidationContext ctx = new CustomerValidationContext("premium", "30", "USA", "", "123456789");
        String result = servlet.validateCustomerData(ct, ctx);
        assertEquals("NAME_REQUIRED_PREMIUM_USA_9DIGIT", result);
    }

    // Nome nulo, standard, menor de idade, Brazil
    @Test
    void testNameNullStandardMinorBrazil() {
        addcustomer servlet = new addcustomer();
        customer ct = new customer();
        ct.setName(null);
        CustomerValidationContext ctx = new CustomerValidationContext("standard", "17", "Brazil", "", "");
        String result = servlet.validateCustomerData(ct, ctx);
        assertEquals("NAME_REQUIRED_STANDARD_MINOR_BRAZIL", result);
    }

    // Nome < 3, premium
    @Test
    void testNameTooShortPremium() {
        addcustomer servlet = new addcustomer();
        customer ct = new customer();
        ct.setName("ab");
        CustomerValidationContext ctx = new CustomerValidationContext("premium", "30", "Brazil", "", "");
        String result = servlet.validateCustomerData(ct, ctx);
        assertEquals("NAME_TOO_SHORT_PREMIUM", result);
    }

    // Nome > 50, standard
    @Test
    void testNameTooLongStandard() {
        addcustomer servlet = new addcustomer();
        customer ct = new customer();
        ct.setName(new String(new char[51]).replace('\0', 'a'));
        CustomerValidationContext ctx = new CustomerValidationContext("standard", "30", "Brazil", "", "");
        String result = servlet.validateCustomerData(ct, ctx);
        assertEquals("NAME_TOO_LONG_STANDARD", result);
    }

    // Nome válido, idade nula, premium
    @Test
    void testAgeNullPremium() {
        addcustomer servlet = new addcustomer();
        customer ct = new customer();
        ct.setName("ValidName");
        CustomerValidationContext ctx = new CustomerValidationContext("premium", null, "Brazil", "", "");
        String result = servlet.validateCustomerData(ct, ctx);
        assertEquals("AGE_REQUIRED_PREMIUM", result);
    }

    // Nome válido, idade < 13, standard
    @Test
    void testAgeTooYoungStandard() {
        addcustomer servlet = new addcustomer();
        customer ct = new customer();
        ct.setName("ValidName");
        CustomerValidationContext ctx = new CustomerValidationContext("standard", "12", "Brazil", "", "");
        String result = servlet.validateCustomerData(ct, ctx);
        assertEquals("AGE_TOO_YOUNG_STANDARD", result);
    }

    // Nome válido, idade > 120, premium
    @Test
    void testAgeTooOldPremium() {
        addcustomer servlet = new addcustomer();
        customer ct = new customer();
        ct.setName("ValidName");
        CustomerValidationContext ctx = new CustomerValidationContext("premium", "121", "Brazil", "", "");
        String result = servlet.validateCustomerData(ct, ctx);
        assertEquals("AGE_TOO_OLD_PREMIUM", result);
    }

    // Nome válido, idade < 18, premium, Brazil
    @Test
    void testAgeMinorPremiumBrazil() {
        addcustomer servlet = new addcustomer();
        customer ct = new customer();
        ct.setName("ValidName");
        CustomerValidationContext ctx = new CustomerValidationContext("premium", "15", "Brazil", "", "");
        String result = servlet.validateCustomerData(ct, ctx);
        assertEquals("AGE_MINOR_PREMIUM_BRAZIL", result);
    }

    // Nome válido, idade < 18, standard, outro país
    @Test
    void testAgeMinorStandardOther() {
        addcustomer servlet = new addcustomer();
        customer ct = new customer();
        ct.setName("ValidName");
        CustomerValidationContext ctx = new CustomerValidationContext("standard", "16", "USA", "", "");
        String result = servlet.validateCustomerData(ct, ctx);
        assertEquals("AGE_MINOR_STANDARD_OTHER", result);
    }

    // Nome válido, idade formato inválido, premium
    @Test
    void testAgeInvalidFormatPremium() {
        addcustomer servlet = new addcustomer();
        customer ct = new customer();
        ct.setName("ValidName");
        CustomerValidationContext ctx = new CustomerValidationContext("premium", "abc", "Brazil", "", "");
        String result = servlet.validateCustomerData(ct, ctx);
        assertEquals("AGE_INVALID_FORMAT_PREMIUM", result);
    }

    // Nome válido, idade 25, standard (caminho feliz)
    @Test
    void testValidStandard() {
        addcustomer servlet = new addcustomer();
        customer ct = new customer();
        ct.setName("ValidName");
        CustomerValidationContext ctx = new CustomerValidationContext("standard", "25", "Brazil", "", "");
        String result = servlet.validateCustomerData(ct, ctx);
        assertEquals("VALID", result);
    }

    // ----------------------
    // Testes adicionais para cobrir todas as arestas de doPost e doGet
    // ----------------------

    @Test
    void testDoGetDoesNotThrow() {
        addcustomer servlet = new addcustomer();
        assertDoesNotThrow(() -> servlet.doGet(request, response));
    }

    // --- Testes extras para matar mutantes sobreviventes do PITEST ---

    // Testa limite inferior da idade (age = 18) para standard
    @Test
    void testNameNullStandardAge18() {
        addcustomer servlet = new addcustomer();
        customer ct = new customer();
        ct.setName(null);
        CustomerValidationContext ctx = new CustomerValidationContext("standard", "18", "Brazil", "", "");
        String result = servlet.validateCustomerData(ct, ctx);
        assertEquals("NAME_REQUIRED_STANDARD_ADULT", result);
    }

    // Testa limite superior da idade (age = 65) para standard
    @Test
    void testNameNullStandardAge65() {
        addcustomer servlet = new addcustomer();
        customer ct = new customer();
        ct.setName(null);
        CustomerValidationContext ctx = new CustomerValidationContext("standard", "65", "Brazil", "", "");
        String result = servlet.validateCustomerData(ct, ctx);
        assertEquals("NAME_REQUIRED_STANDARD_ADULT", result);
    }

    // Testa idade > 65 (age = 66) para standard, Brazil
    @Test
    void testNameNullStandardSeniorBrazil() {
        addcustomer servlet = new addcustomer();
        customer ct = new customer();
        ct.setName(null);
        CustomerValidationContext ctx = new CustomerValidationContext("standard", "66", "Brazil", "", "");
        String result = servlet.validateCustomerData(ct, ctx);
        assertEquals("NAME_REQUIRED_STANDARD_SENIOR_BRAZIL", result);
    }

    // Testa idade > 65 (age = 66) para standard, outro país
    @Test
    void testNameNullStandardSeniorOther() {
        addcustomer servlet = new addcustomer();
        customer ct = new customer();
        ct.setName(null);
        CustomerValidationContext ctx = new CustomerValidationContext("standard", "66", "USA", "", "");
        String result = servlet.validateCustomerData(ct, ctx);
        assertEquals("NAME_REQUIRED_STANDARD_SENIOR_OTHER", result);
    }

    // Testa nome de tamanho exatamente 3
    @Test
    void testNameLengthExactly3() {
        addcustomer servlet = new addcustomer();
        customer ct = new customer();
        ct.setName("abc");
        CustomerValidationContext ctx = new CustomerValidationContext("standard", "25", "Brazil", "", "");
        String result = servlet.validateCustomerData(ct, ctx);
        assertEquals("VALID", result);
    }

    // Testa nome de tamanho exatamente 50
    @Test
    void testNameLengthExactly50() {
        addcustomer servlet = new addcustomer();
        customer ct = new customer();
        ct.setName(new String(new char[50]).replace('\0', 'a'));
        CustomerValidationContext ctx = new CustomerValidationContext("standard", "25", "Brazil", "", "");
        String result = servlet.validateCustomerData(ct, ctx);
        assertEquals("VALID", result);
    }

    // Testa idade exatamente 13
    @Test
    void testAgeExactly13() {
        addcustomer servlet = new addcustomer();
        customer ct = new customer();
        ct.setName("ValidName");
        CustomerValidationContext ctx = new CustomerValidationContext("standard", "13", "Brazil", "", "");
        String result = servlet.validateCustomerData(ct, ctx);
        assertEquals("AGE_MINOR_STANDARD_BRAZIL", result);
    }

    // Testa idade exatamente 18
    @Test
    void testAgeExactly18() {
        addcustomer servlet = new addcustomer();
        customer ct = new customer();
        ct.setName("ValidName");
        CustomerValidationContext ctx = new CustomerValidationContext("standard", "18", "Brazil", "", "");
        String result = servlet.validateCustomerData(ct, ctx);
        assertEquals("VALID", result);
    }

    // Testa idade exatamente 120
    @Test
    void testAgeExactly120() {
        addcustomer servlet = new addcustomer();
        customer ct = new customer();
        ct.setName("ValidName");
        CustomerValidationContext ctx = new CustomerValidationContext("standard", "120", "Brazil", "", "");
        String result = servlet.validateCustomerData(ct, ctx);
        assertEquals("VALID", result);
    }

    // Testa country diferente de Brazil para senior
    @Test
    void testNameNullStandardSeniorOtherCountry() {
        addcustomer servlet = new addcustomer();
        customer ct = new customer();
        ct.setName(null);
        CustomerValidationContext ctx = new CustomerValidationContext("standard", "70", "Argentina", "", "");
        String result = servlet.validateCustomerData(ct, ctx);
        assertEquals("NAME_REQUIRED_STANDARD_SENIOR_OTHER", result);
    }

    // Testa userType diferente de premium e standard
    @Test
    void testNameNullUnknownUserType() {
        addcustomer servlet = new addcustomer();
        customer ct = new customer();
        ct.setName(null);
        CustomerValidationContext ctx = new CustomerValidationContext("unknown", "25", "Brazil", "", "");
        String result = servlet.validateCustomerData(ct, ctx);
        assertEquals("NAME_REQUIRED_UNKNOWN_TYPE", result);
    }

    // Testa nome vazio (apenas espaços)
    @Test
    void testNameEmptyWithSpaces() {
        addcustomer servlet = new addcustomer();
        customer ct = new customer();
        ct.setName("   ");
        CustomerValidationContext ctx = new CustomerValidationContext("standard", "25", "Brazil", "", "");
        String result = servlet.validateCustomerData(ct, ctx);
        assertEquals("NAME_REQUIRED_STANDARD_ADULT", result);
    }

    // Testa idade vazia (apenas espaços)
    @Test
    void testAgeEmptyWithSpaces() {
        addcustomer servlet = new addcustomer();
        customer ct = new customer();
        ct.setName("ValidName");
        CustomerValidationContext ctx = new CustomerValidationContext("standard", "   ", "Brazil", "", "");
        String result = servlet.validateCustomerData(ct, ctx);
        assertEquals("AGE_REQUIRED_STANDARD", result);
    }
    
    @Test
    void testNameNullPremiumBrazilOtherCity() {
        addcustomer servlet = new addcustomer();
        customer ct = new customer();
        ct.setName(null);
        CustomerValidationContext ctx = new CustomerValidationContext("premium", "30", "Brazil", "Brasília", "");
        String result = servlet.validateCustomerData(ct, ctx);
        assertEquals("NAME_REQUIRED_PREMIUM_BRAZIL_OTHER", result);
    }

    // Testa premium, USA, zipCode inválido (não 5 nem 9 dígitos)
    @Test
    void testNameNullPremiumUSAInvalidZipCode() {
        addcustomer servlet = new addcustomer();
        customer ct = new customer();
        ct.setName(null);
        CustomerValidationContext ctx = new CustomerValidationContext("premium", "30", "USA", "", "123");
        String result = servlet.validateCustomerData(ct, ctx);
        assertEquals("NAME_REQUIRED_PREMIUM_USA_INVALID", result);
    }

    // Testa premium, país diferente de Brazil e USA
    @Test
    void testNameNullPremiumOtherCountry() {
        addcustomer servlet = new addcustomer();
        customer ct = new customer();
        ct.setName(null);
        CustomerValidationContext ctx = new CustomerValidationContext("premium", "30", "Canada", "", "");
        String result = servlet.validateCustomerData(ct, ctx);
        assertEquals("NAME_REQUIRED_PREMIUM_OTHER", result);
    }

    // Testa idade exatamente 12 (limite inferior)
    @Test
    void testAgeExactly12() {
        addcustomer servlet = new addcustomer();
        customer ct = new customer();
        ct.setName("ValidName");
        CustomerValidationContext ctx = new CustomerValidationContext("standard", "12", "Brazil", "", "");
        String result = servlet.validateCustomerData(ct, ctx);
        assertEquals("AGE_TOO_YOUNG_STANDARD", result);
    }

    // Testa idade exatamente 121 (limite superior)
    @Test
    void testAgeExactly121() {
        addcustomer servlet = new addcustomer();
        customer ct = new customer();
        ct.setName("ValidName");
        CustomerValidationContext ctx = new CustomerValidationContext("standard", "121", "Brazil", "", "");
        String result = servlet.validateCustomerData(ct, ctx);
        assertEquals("AGE_TOO_OLD_STANDARD", result);
    }

    // Testa idade exatamente 17 (limite inferior para minor)
    @Test
    void testAgeExactly17() {
        addcustomer servlet = new addcustomer();
        customer ct = new customer();
        ct.setName("ValidName");
        CustomerValidationContext ctx = new CustomerValidationContext("standard", "17", "Brazil", "", "");
        String result = servlet.validateCustomerData(ct, ctx);
        assertEquals("AGE_MINOR_STANDARD_BRAZIL", result);
    }

    // Testa idade exatamente 66 (limite inferior para senior)
    @Test
    void testAgeExactly66() {
        addcustomer servlet = new addcustomer();
        customer ct = new customer();
        ct.setName("ValidName");
        CustomerValidationContext ctx = new CustomerValidationContext("standard", "66", "Brazil", "", "");
        String result = servlet.validateCustomerData(ct, ctx);
        assertEquals("VALID", result);
    }

    // Testa nome exatamente 2 caracteres (limite inferior)
    @Test
    void testNameLengthExactly2() {
        addcustomer servlet = new addcustomer();
        customer ct = new customer();
        ct.setName("ab");
        CustomerValidationContext ctx = new CustomerValidationContext("standard", "25", "Brazil", "", "");
        String result = servlet.validateCustomerData(ct, ctx);
        assertEquals("NAME_TOO_SHORT_STANDARD", result);
    }

    // Testa nome exatamente 51 caracteres (limite superior)
    @Test
    void testNameLengthExactly51() {
        addcustomer servlet = new addcustomer();
        customer ct = new customer();
        ct.setName(new String(new char[51]).replace('\0', 'a'));
        CustomerValidationContext ctx = new CustomerValidationContext("standard", "25", "Brazil", "", "");
        String result = servlet.validateCustomerData(ct, ctx);
        assertEquals("NAME_TOO_LONG_STANDARD", result);
    }

    // Testa idade nula para standard
    @Test
    void testAgeNullStandard() {
        addcustomer servlet = new addcustomer();
        customer ct = new customer();
        ct.setName("ValidName");
        CustomerValidationContext ctx = new CustomerValidationContext("standard", null, "Brazil", "", "");
        String result = servlet.validateCustomerData(ct, ctx);
        assertEquals("AGE_REQUIRED_STANDARD", result);
    }

    // Testa idade formato inválido para standard
    @Test
    void testAgeInvalidFormatStandard() {
        addcustomer servlet = new addcustomer();
        customer ct = new customer();
        ct.setName("ValidName");
        CustomerValidationContext ctx = new CustomerValidationContext("standard", "xyz", "Brazil", "", "");
        String result = servlet.validateCustomerData(ct, ctx);
        assertEquals("AGE_INVALID_FORMAT_STANDARD", result);
    }

    // Testa idade < 13 para premium
    @Test
    void testAgeTooYoungPremium() {
        addcustomer servlet = new addcustomer();
        customer ct = new customer();
        ct.setName("ValidName");
        CustomerValidationContext ctx = new CustomerValidationContext("premium", "10", "Brazil", "", "");
        String result = servlet.validateCustomerData(ct, ctx);
        assertEquals("AGE_TOO_YOUNG_PREMIUM", result);
    }

    // Testa idade > 120 para standard
    @Test
    void testAgeTooOldStandard() {
        addcustomer servlet = new addcustomer();
        customer ct = new customer();
        ct.setName("ValidName");
        CustomerValidationContext ctx = new CustomerValidationContext("standard", "125", "Brazil", "", "");
        String result = servlet.validateCustomerData(ct, ctx);
        assertEquals("AGE_TOO_OLD_STANDARD", result);
    }

    // Testa idade < 18 para premium, outro país
    @Test
    void testAgeMinorPremiumOther() {
        addcustomer servlet = new addcustomer();
        customer ct = new customer();
        ct.setName("ValidName");
        CustomerValidationContext ctx = new CustomerValidationContext("premium", "16", "USA", "", "");
        String result = servlet.validateCustomerData(ct, ctx);
        assertEquals("AGE_MINOR_PREMIUM_OTHER", result);
    }

    // Testa nome nulo, standard, idade exatamente 19 (adulto)
    @Test
    void testNameNullStandardAge19() {
        addcustomer servlet = new addcustomer();
        customer ct = new customer();
        ct.setName(null);
        CustomerValidationContext ctx = new CustomerValidationContext("standard", "19", "Brazil", "", "");
        String result = servlet.validateCustomerData(ct, ctx);
        assertEquals("NAME_REQUIRED_STANDARD_ADULT", result);
    }

    // Testa nome nulo, standard, idade exatamente 64 (adulto)
    @Test
    void testNameNullStandardAge64() {
        addcustomer servlet = new addcustomer();
        customer ct = new customer();
        ct.setName(null);
        CustomerValidationContext ctx = new CustomerValidationContext("standard", "64", "Brazil", "", "");
        String result = servlet.validateCustomerData(ct, ctx);
        assertEquals("NAME_REQUIRED_STANDARD_ADULT", result);
    }

    // Testa nome nulo, standard, idade exatamente 67 (senior)
    @Test
    void testNameNullStandardAge67() {
        addcustomer servlet = new addcustomer();
        customer ct = new customer();
        ct.setName(null);
        CustomerValidationContext ctx = new CustomerValidationContext("standard", "67", "Brazil", "", "");
        String result = servlet.validateCustomerData(ct, ctx);
        assertEquals("NAME_REQUIRED_STANDARD_SENIOR_BRAZIL", result);
    }

    // Testa nome nulo, standard, idade exatamente 67, outro país
    @Test
    void testNameNullStandardAge67OtherCountry() {
        addcustomer servlet = new addcustomer();
        customer ct = new customer();
        ct.setName(null);
        CustomerValidationContext ctx = new CustomerValidationContext("standard", "67", "Canada", "", "");
        String result = servlet.validateCustomerData(ct, ctx);
        assertEquals("NAME_REQUIRED_STANDARD_SENIOR_OTHER", result);
    }

    // Testa nome nulo, standard, idade exatamente 16 (minor), outro país
    @Test
    void testNameNullStandardAge16OtherCountry() {
        addcustomer servlet = new addcustomer();
        customer ct = new customer();
        ct.setName(null);
        CustomerValidationContext ctx = new CustomerValidationContext("standard", "16", "Canada", "", "");
        String result = servlet.validateCustomerData(ct, ctx);
        assertEquals("NAME_REQUIRED_STANDARD_MINOR_OTHER", result);
    }
}
