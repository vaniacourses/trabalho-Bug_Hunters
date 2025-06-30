import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.PreparedStatement;

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

    @Test
    public void testAddToCart_SuccessfulInsert_Returns1() throws Exception {
        cart mockCart = mock(cart.class);

        when(mockCart.getBname()).thenReturn("BookName");
        when(mockCart.getCname()).thenReturn("CustomerName");
        when(mockCart.getPname()).thenReturn("ProductName");
        when(mockCart.getPprice()).thenReturn(100);
        when(mockCart.getPquantity()).thenReturn(2);
        when(mockCart.getPimage()).thenReturn("image.png");


        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        int result = dao.addtocartnull(mockCart);

        assertEquals(1, result);
        verify(mockPreparedStatement, times(1)).executeUpdate();
    }

    @Test
    public void testAddToCart_InsertFails_Returns0() throws Exception {
        cart mockCart = mock(cart.class);

        when(mockCart.getBname()).thenReturn("BookName");
        when(mockCart.getCname()).thenReturn("CustomerName");
        when(mockCart.getPname()).thenReturn("ProductName");
        when(mockCart.getPprice()).thenReturn(100);
        when(mockCart.getPquantity()).thenReturn(2);
        when(mockCart.getPimage()).thenReturn("image.png");

        when(mockPreparedStatement.executeUpdate()).thenReturn(0);

        int result = dao.addtocartnull(mockCart);

        assertEquals(0, result);
    }

    @Test
    public void testAddToCart_ExceptionThrown_Returns0() throws Exception {
        cart mockCart = mock(cart.class);

        when(mockConnection.prepareStatement(any())).thenThrow(new RuntimeException("DB error"));

        int result = dao.addtocartnull(mockCart);

        assertEquals(0, result);
    }
}
