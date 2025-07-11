import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.dao.DAO2;
import com.entity.viewlist;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

        dao = new DAO2(mockConnection);
    }

    @Test
    @DisplayName("Deve retornar um produto com todos os detalhes quando a imagem existe")
    void getSelecteditem_shouldReturnProduct_whenImageExists() throws Exception {
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
        assertAll("Verifica todos os detalhes do produto retornado",
                () -> assertEquals("Marca Teste", product.getBname()),
                () -> assertEquals("Categoria Teste", product.getCname()),
                () -> assertEquals("Produto de Teste", product.getPname()),
                () -> assertEquals(150, product.getPprice()),
                () -> assertEquals(10, product.getPquantity()),
                () -> assertEquals(productImage, product.getPimage())
        );
        verify(mockPreparedStatement).setObject(1, productImage);
    }

    @Test
    @DisplayName("Deve retornar uma lista com múltiplos produtos se a imagem não for única")
    void getSelecteditem_shouldReturnMultipleProducts_whenImageIsNotUnique() throws Exception {
        String nonUniqueImage = "imagem_repetida.jpg";
        when(mockResultSet.next()).thenReturn(true, true, false);
        when(mockResultSet.getString(3)).thenReturn("Produto A", "Produto B");
        when(mockResultSet.getInt(4)).thenReturn(100, 200);

        List<viewlist> result = dao.getSelecteditem(nonUniqueImage);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Produto A", result.get(0).getPname());
        assertEquals(200, result.get(1).getPprice());
    }

    @Test
    @DisplayName("Deve retornar preço 0 se o valor no banco for nulo ou inválido")
    void getSelecteditem_shouldReturnPriceZero_whenDbPriceIsInvalid() throws SQLException {
        String productImage = "produto_preco_nulo.jpg";
        when(mockResultSet.next()).thenReturn(true).thenReturn(false);
        when(mockResultSet.getInt(4)).thenReturn(0);

        List<viewlist> result = dao.getSelecteditem(productImage);

        assertEquals(1, result.size());
        assertEquals(0, result.get(0).getPprice());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"  ", "\t", "\n"})
    @DisplayName("Deve retornar lista vazia para entradas de imagem inválidas (null, vazia, espaços em branco)")
    void getSelecteditem_shouldReturnEmptyList_forInvalidInput(String invalidImageName) {
        List<viewlist> result = dao.getSelecteditem(invalidImageName);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("Deve retornar uma lista vazia em caso de erro de SQL")
    void getSelecteditem_shouldReturnEmptyList_onSqlException() throws Exception {
        String productImage = "qualquer_imagem.jpg";
        when(mockPreparedStatement.executeQuery()).thenThrow(new SQLException("Erro de conexão com o banco de dados"));

        List<viewlist> result = dao.getSelecteditem(productImage);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}