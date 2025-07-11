import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.dao.DAO2;
import com.entity.viewlist;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ViewProductDetailsTest {

    private Connection mockConnection;
    private PreparedStatement mockPreparedStatement;
    private ResultSet mockResultSet;
    private DAO2 dao;

    @BeforeEach
    public void setup() throws Exception {
        mockConnection = mock(Connection.class);
        mockPreparedStatement = mock(PreparedStatement.class);
        mockResultSet = mock(ResultSet.class);

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

        dao = new DAO2(mockConnection);
    }

    @Test
    public void testGetSelectedItem_ProductFound_ReturnsProductList() throws Exception {
        String productImage = "produto_teste.jpg";

        when(mockResultSet.next()).thenReturn(true).thenReturn(false);

        when(mockResultSet.getString(1)).thenReturn("Marca Teste");
        when(mockResultSet.getString(2)).thenReturn("Categoria Teste");
        when(mockResultSet.getString(3)).thenReturn("Produto de Teste");
        when(mockResultSet.getInt(4)).thenReturn(150);
        when(mockResultSet.getInt(5)).thenReturn(10);
        when(mockResultSet.getString(6)).thenReturn(productImage);

        List<viewlist> result = dao.getSelecteditem(productImage);

        assertNotNull(result);
        assertEquals(1, result.size());

        viewlist product = result.get(0);
        assertEquals("Marca Teste", product.getBname());
        assertEquals("Categoria Teste", product.getCname());
        assertEquals("Produto de Teste", product.getPname());
        assertEquals(150, product.getPprice());
        assertEquals(10, product.getPquantity());
        assertEquals(productImage, product.getPimage());

        verify(mockPreparedStatement).setObject(1, productImage);
        verify(mockPreparedStatement, times(1)).executeQuery();
    }

    @Test
    public void testGetSelectedItem_ProductNotFound_ReturnsEmptyList() throws Exception {
        String productImage = "imagem_inexistente.jpg";

        when(mockResultSet.next()).thenReturn(false);

        List<viewlist> result = dao.getSelecteditem(productImage);

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(mockPreparedStatement).setObject(1, productImage);
        verify(mockPreparedStatement, times(1)).executeQuery();
    }

    @Test
    public void testGetSelectedItem_SQLExceptionThrown_ReturnsEmptyList() throws Exception {
        String productImage = "qualquer_imagem.jpg";

        when(mockPreparedStatement.executeQuery()).thenThrow(new SQLException("Erro de conexão com o banco de dados"));

        List<viewlist> result = dao.getSelecteditem(productImage);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}