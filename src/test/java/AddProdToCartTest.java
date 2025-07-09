import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.dao.DAO2;
import com.entity.cart;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AddProdToCartTest {

    private Connection mockConnection;
    private PreparedStatement mockPreparedStatement;
    private DAO2 dao;

    @BeforeEach
    public void setup() throws Exception {
        mockConnection = mock(Connection.class);
        mockPreparedStatement = mock(PreparedStatement.class);

        when(mockConnection.prepareStatement(any(String.class))).thenReturn(mockPreparedStatement);
        dao = new DAO2(mockConnection);
    }

    private cart buildCart(
            String bname, String cname, String pname,
            Integer pprice, Integer pquantity, String pimage) {

        cart mockCart = mock(cart.class);
        when(mockCart.getBname()).thenReturn(bname);
        when(mockCart.getCname()).thenReturn(cname);
        when(mockCart.getPname()).thenReturn(pname);
        when(mockCart.getPprice()).thenReturn(pprice);
        when(mockCart.getPquantity()).thenReturn(pquantity);
        when(mockCart.getPimage()).thenReturn(pimage);
        return mockCart;
    }

    @Test
    public void testAddToCart_SuccessfulInsert_Returns1() throws Exception {
        cart mockCart = buildCart("Book", "Client", "Pen", 10, 2, "img.png");
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        int result = dao.addtocartnull(mockCart);

        assertEquals(1, result);
        verify(mockPreparedStatement, times(1)).executeUpdate();
    }

    @Test
    public void testAddToCart_InsertFails_Returns0() throws Exception {
        cart mockCart = buildCart("Book", "Client", "Pen", 10, 2, "img.png");
        when(mockPreparedStatement.executeUpdate()).thenReturn(0);

        int result = dao.addtocartnull(mockCart);

        assertEquals(0, result);
    }

    @Test
    public void testAddToCart_PrepareStatementException_Returns0() throws Exception {
        cart mockCart = buildCart("Book", "Client", "Pen", 10, 2, "img.png");

        when(mockConnection.prepareStatement(any())).thenThrow(new SQLException("Erro SQL"));

        int result = dao.addtocartnull(mockCart);

        assertEquals(0, result);
    }

    @Test
    public void testAddToCart_SetParameterException_Returns0() throws Exception {
        cart mockCart = buildCart("Book", "Client", "Pen", 10, 2, "img.png");

        doThrow(new SQLException("Erro em setString")).when(mockPreparedStatement).setObject(eq(1), eq("Book"));

        int result = dao.addtocartnull(mockCart);

        assertEquals(0, result);
    }

    @Test
    public void testAddToCart_NullValues() throws Exception {
        cart mockCart = buildCart(null, null, null, null, null, null);

        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        int result = dao.addtocartnull(mockCart);

        assertEquals(1, result);
    }

    @Test
    public void testAddToCart_VerifySetParameters() throws Exception {
        cart mockCart = buildCart("Book", "Client", "Pen", 10, 2, "img.png");

        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        dao.addtocartnull(mockCart);

        verify(mockPreparedStatement).setObject(1, "Book");
        verify(mockPreparedStatement).setObject(2, "Client");
        verify(mockPreparedStatement).setObject(3, "Pen");
        verify(mockPreparedStatement).setObject(4, 10);
        verify(mockPreparedStatement).setObject(5, 2);
        verify(mockPreparedStatement).setObject(6, "img.png");
    }

    @Test
    public void testAddToCart_WithRealCartObject() throws Exception {
        cart realCart = new cart();
        realCart.setBname("Book");
        realCart.setCname("Client");
        realCart.setPname("Pen");
        realCart.setPprice(50);
        realCart.setPquantity(1);
        realCart.setPimage("img.png");

        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        int result = dao.addtocartnull(realCart);

        assertEquals(1, result);
    }
}
