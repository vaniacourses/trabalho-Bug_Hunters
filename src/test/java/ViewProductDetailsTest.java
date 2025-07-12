import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.dao.DAO2;
import com.entity.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@DisplayName("Testes Avançados para Visualização de Detalhes do Produto")
public class ViewProductDetailsTest {

    private Connection mockConnection;
    private PreparedStatement mockPreparedStatement;
    private ResultSet mockResultSet;
    private DAO2 dao;

    @BeforeEach
    void setup() throws SQLException {
        mockConnection = mock(Connection.class);
        mockPreparedStatement = mock(PreparedStatement.class);
        mockResultSet = mock(ResultSet.class);

        lenient().when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        lenient().when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        lenient().when(mockPreparedStatement.executeUpdate()).thenReturn(1);
        dao = new DAO2(mockConnection);
    }

    private customer buildMockCustomer() { return mock(customer.class); }
    private cart buildMockCart() { return mock(cart.class); }
    private usermaster buildMockUsermaster() { return mock(usermaster.class); }
    private orders buildMockOrder() { return mock(orders.class); }

    @Nested
    @DisplayName("Testes de Visualização de Itens")
    class ItemViewTests {

        @Test
        void getSelecteditem_shouldReturnListWhenProductExists() throws SQLException {
            String testImage = "produto.jpg";
            when(mockResultSet.next()).thenReturn(true, false);
            List<viewlist> result = dao.getSelecteditem(testImage);
            assertFalse(result.isEmpty());
            verify(mockPreparedStatement).setObject(1, testImage);
        }

        @ParameterizedTest
        @NullAndEmptySource
        @ValueSource(strings = { " ", "\t" })
        void getSelecteditem_shouldReturnEmptyListForInvalidInput(String invalidInput) {
            assertTrue(dao.getSelecteditem(invalidInput).isEmpty());
        }

        @Test
        void getAllviewlist_shouldReturnMultipleProducts() throws SQLException {
            when(mockResultSet.next()).thenReturn(true, true, false);
            assertEquals(2, dao.getAllviewlist().size());
        }

        @Test
        void getAllviewlist_shouldReturnEmptyListOnSqlException() throws SQLException {
            when(mockResultSet.next()).thenThrow(new SQLException("Erro de Leitura"));
            assertTrue(dao.getAllviewlist().isEmpty());
        }
    }

    @Nested
    @DisplayName("Testes de Autenticação e Gestão de Usuários")
    class UserManagementTests {

        @Test
        void checkcust_shouldReturnTrueForValidCredentials() throws SQLException {
            customer cust = buildMockCustomer();
            when(cust.getPassword()).thenReturn("pass123");
            when(cust.getEmail_Id()).thenReturn("user@test.com");
            when(mockResultSet.next()).thenReturn(true);

            assertTrue(dao.checkcust(cust));

            verify(mockPreparedStatement).setObject(1, "pass123");
            verify(mockPreparedStatement).setObject(2, "user@test.com");
        }

        @Test
        void checkcust_shouldReturnFalseForInvalidCredentials() throws SQLException {
            when(mockResultSet.next()).thenReturn(false);
            assertFalse(dao.checkcust(buildMockCustomer()));
        }

        @Test
        void addcustomer_shouldReturnOneOnSuccess() throws SQLException {
            customer cust = new customer();
            cust.setName("New User");
            cust.setPassword("newpass");
            cust.setEmail_Id("new@user.com");
            cust.setContact_No(12345);

            assertEquals(1, dao.addcustomer(cust));

            verify(mockPreparedStatement).setObject(1, "New User");
            verify(mockPreparedStatement).setObject(2, "newpass");
            verify(mockPreparedStatement).setObject(3, "new@user.com");
            verify(mockPreparedStatement).setObject(4, 12345);
        }

        @Test
        void addcustomer_shouldReturnZeroOnSqlException() throws SQLException {
            when(mockPreparedStatement.executeUpdate()).thenThrow(new SQLException());
            assertEquals(0, dao.addcustomer(new customer()));
        }

        @Test
        void checkadmin_shouldReturnTrueForValidAdmin() throws SQLException {
            usermaster admin = buildMockUsermaster();
            when(admin.getName()).thenReturn("admin");
            when(admin.getPassword()).thenReturn("admin_pass");
            when(mockResultSet.next()).thenReturn(true);

            assertTrue(dao.checkadmin(admin));

            verify(mockPreparedStatement).setObject(1, "admin");
            verify(mockPreparedStatement).setObject(2, "admin_pass");
        }

        @Test
        void checkcust2_shouldReturnTrueWhenUserExists() throws SQLException {
            when(mockResultSet.next()).thenReturn(true);
            assertTrue(dao.checkcust2(buildMockCustomer()));
        }
    }

    @Nested
    @DisplayName("Testes de Delegação para Operações de Carrinho")
    class CartDelegationTests {

        @Test
        void checkaddtocartnull_shouldDelegateAndReturnBoolean() throws SQLException {
            when(mockResultSet.next()).thenReturn(true);
            assertTrue(dao.checkaddtocartnull(buildMockCart()));
        }

        @Test
        void updateaddtocartnull_shouldDelegateAndReturnInt() {
            assertEquals(1, dao.updateaddtocartnull(buildMockCart()));
        }

        @Test
        void addtocartnull_shouldDelegateAndReturnInt() {
            assertEquals(1, dao.addtocartnull(buildMockCart()));
        }

        @Test
        void getSelectedcart_shouldDelegateAndReturnList() {
            assertNotNull(dao.getSelectedcart());
        }

        @Test
        void getcart_shouldDelegateAndReturnList() {
            assertNotNull(dao.getcart("some_customer"));
        }

        @Test
        void removecart_shouldDelegateAndReturnInt() {
            assertEquals(1, dao.removecart(buildMockCart()));
        }
    }

    @Nested
    @DisplayName("Testes de Gestão de Pedidos")
    class OrderManagementTests {

        @Test
        void removeorders_shouldReturnOneOnSuccess() throws SQLException {
            orders order = buildMockOrder();
            when(order.getOrder_Id()).thenReturn(999);

            assertEquals(1, dao.removeorders(order));

            verify(mockPreparedStatement).setObject(1, 999);
        }

        @Test
        void removeorders_shouldReturnZeroOnFailure() throws SQLException {
            when(mockPreparedStatement.executeUpdate()).thenReturn(0);
            assertEquals(0, dao.removeorders(buildMockOrder()));
        }

        @Test
        void removeorders_shouldReturnZeroOnException() throws SQLException {
            when(mockPreparedStatement.executeUpdate()).thenThrow(new SQLException());
            assertEquals(0, dao.removeorders(buildMockOrder()));
        }
    }
}