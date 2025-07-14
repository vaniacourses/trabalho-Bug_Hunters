
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;

import com.conn.DBConnect;
import com.entity.brand;
import com.entity.category;
import com.entity.customer;
import org.apache.commons.fileupload.FileUploadException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import com.utility.MyUtilities;
import com.dao.DAO;


@ExtendWith(MockitoExtension.class)
public class AddNewProductTest {
    @Mock
    private HttpServletRequest request;

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    private FileItem fileItemFormField1;

    @Mock
    private FileItem fileItemFormField2;

    @Mock
    private FileItem fileItemFormField3;

    @Mock
    private FileItem fileItemFormField4;

    @Mock
    private FileItem fileItemFormField5;

    @Mock
    private FileItem fileItemFile;

    @Mock
    private MyUtilities myUtilitiesMock;

    @Mock
    private ServletFileUpload servletFileUploadMock;

    @Mock
    private DAO dao;

     private MyUtilities myUtilities = new MyUtilities();

    @BeforeEach
    void setUp() throws Exception {
        dao = new DAO(connection, myUtilitiesMock, servletFileUploadMock);

        try (MockedStatic<DBConnect> dbConnect = mockStatic(DBConnect.class)) {
            dbConnect.when(DBConnect::getConn).thenReturn(connection);
        }
    }


    @Test
    void testShouldAddProductSuccessfully() throws Exception {
        // Arrange
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);

        // Simula campos do formulário
        when(fileItemFormField1.isFormField()).thenReturn(true);
        when(fileItemFormField1.getFieldName()).thenReturn("pname");
        when(fileItemFormField1.getString()).thenReturn("Galaxy Watch Ultra");

        when(fileItemFormField2.isFormField()).thenReturn(true);
        when(fileItemFormField2.getFieldName()).thenReturn("pprice");
        when(fileItemFormField2.getString()).thenReturn("2799");

        when(fileItemFormField3.isFormField()).thenReturn(true);
        when(fileItemFormField3.getFieldName()).thenReturn("pquantity");
        when(fileItemFormField3.getString()).thenReturn("1");

        when(fileItemFormField4.isFormField()).thenReturn(true);
        when(fileItemFormField4.getFieldName()).thenReturn("bname");
        when(fileItemFormField4.getString()).thenReturn("samsung");

        when(fileItemFormField5.isFormField()).thenReturn(true);
        when(fileItemFormField5.getFieldName()).thenReturn("cname");
        when(fileItemFormField5.getString()).thenReturn("watch");

        // Simula upload de arquivo
        when(fileItemFile.isFormField()).thenReturn(false);

        List<FileItem> fileItems = new ArrayList<>();
        fileItems.add(fileItemFormField1);
        fileItems.add(fileItemFormField2);
        fileItems.add(fileItemFormField3);
        fileItems.add(fileItemFormField4);
        fileItems.add(fileItemFormField5);
        fileItems.add(fileItemFile);

        // Mock do parseRequest para retornar os campos simulados!
        when(servletFileUploadMock.parseRequest(any(HttpServletRequest.class))).thenReturn(fileItems);

        // Mock do upload de arquivo
        when(myUtilitiesMock.UploadFile(eq(fileItemFile), anyString(), any()))
                .thenReturn("galaxy_watch_ultra.jpg");

        // Mock estático de isMultipartContent (opcional, só se o DAO usa)
        try (MockedStatic<ServletFileUpload> uploadMock = mockStatic(ServletFileUpload.class)) {
            uploadMock.when(() -> ServletFileUpload.isMultipartContent(any(HttpServletRequest.class))).thenReturn(true);

            // Act
            int result = dao.addproduct(request);

            // Assert
            verify(preparedStatement).setString(1, "Galaxy Watch Ultra");
            verify(preparedStatement).setInt(2, 2799);
            verify(preparedStatement).setInt(3, 1);
            verify(preparedStatement).setString(4, "galaxy_watch_ultra.jpg");
            verify(preparedStatement).setInt(5, 1); // samsung = 1
            verify(preparedStatement).setInt(6, 4); // watch = 4
            verify(preparedStatement).executeUpdate();
            assertEquals(1, result);
        }
    }

    @Test
    void testShouldAddProductTvSuccessfully() throws Exception {
        System.out.println("Rodando teste shouldAddProductSuccessfully");
        // Arrange
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);

        // Simula campos do formulário
        when(fileItemFormField1.isFormField()).thenReturn(true);
        when(fileItemFormField1.getFieldName()).thenReturn("pname");
        when(fileItemFormField1.getString()).thenReturn("Crystal 4K IA 55");

        when(fileItemFormField2.isFormField()).thenReturn(true);
        when(fileItemFormField2.getFieldName()).thenReturn("pprice");
        when(fileItemFormField2.getString()).thenReturn("3799");

        when(fileItemFormField3.isFormField()).thenReturn(true);
        when(fileItemFormField3.getFieldName()).thenReturn("pquantity");
        when(fileItemFormField3.getString()).thenReturn("1");

        when(fileItemFormField4.isFormField()).thenReturn(true);
        when(fileItemFormField4.getFieldName()).thenReturn("bname");
        when(fileItemFormField4.getString()).thenReturn("sony");

        when(fileItemFormField5.isFormField()).thenReturn(true);
        when(fileItemFormField5.getFieldName()).thenReturn("cname");
        when(fileItemFormField5.getString()).thenReturn("tv");

        // Simula upload de arquivo
        when(fileItemFile.isFormField()).thenReturn(false);

        List<FileItem> fileItems = new ArrayList<>();
        fileItems.add(fileItemFormField1);
        fileItems.add(fileItemFormField2);
        fileItems.add(fileItemFormField3);
        fileItems.add(fileItemFormField4);
        fileItems.add(fileItemFormField5);
        fileItems.add(fileItemFile);

        // Mock do parseRequest para retornar os campos simulados!
        when(servletFileUploadMock.parseRequest(any(HttpServletRequest.class))).thenReturn(fileItems);

        // Mock do upload de arquivo
        when(myUtilitiesMock.UploadFile(eq(fileItemFile), anyString(), any()))
                .thenReturn("crystalAI55.jpg");

        // Mock estático de isMultipartContent (opcional, só se o DAO usa)
        try (MockedStatic<ServletFileUpload> uploadMock = mockStatic(ServletFileUpload.class)) {
            uploadMock.when(() -> ServletFileUpload.isMultipartContent(any(HttpServletRequest.class))).thenReturn(true);

            // Act
            int result = dao.addproduct(request);

            // Assert
            verify(preparedStatement).setString(1, "Crystal 4K IA 55");
            verify(preparedStatement).setInt(2, 3799);
            verify(preparedStatement).setInt(3, 1);
            verify(preparedStatement).setString(4, "crystalAI55.jpg");
            verify(preparedStatement).setInt(5, 2);
            verify(preparedStatement).setInt(6, 2);
            verify(preparedStatement).executeUpdate();
            assertEquals(1, result);
        }
    }

    @Test
    void testShouldNotAddProductWhenUploadFails() throws Exception {
    // Simula campos do formulário
    when(fileItemFormField1.isFormField()).thenReturn(true);
    when(fileItemFormField1.getFieldName()).thenReturn("pname");
    when(fileItemFormField1.getString()).thenReturn("Galaxy Watch Ultra");

    when(fileItemFormField2.isFormField()).thenReturn(true);
    when(fileItemFormField2.getFieldName()).thenReturn("pprice");
    when(fileItemFormField2.getString()).thenReturn("2799");

    when(fileItemFormField3.isFormField()).thenReturn(true);
    when(fileItemFormField3.getFieldName()).thenReturn("pquantity");
    when(fileItemFormField3.getString()).thenReturn("1");

    when(fileItemFormField4.isFormField()).thenReturn(true);
    when(fileItemFormField4.getFieldName()).thenReturn("bname");
    when(fileItemFormField4.getString()).thenReturn("samsung");

    when(fileItemFormField5.isFormField()).thenReturn(true);
    when(fileItemFormField5.getFieldName()).thenReturn("cname");
    when(fileItemFormField5.getString()).thenReturn("watch");

    // Simula upload de arquivo
    when(fileItemFile.isFormField()).thenReturn(false);

    List<FileItem> fileItems = new ArrayList<>();
    fileItems.add(fileItemFormField1);
    fileItems.add(fileItemFormField2);
    fileItems.add(fileItemFormField3);
    fileItems.add(fileItemFormField4);
    fileItems.add(fileItemFormField5);
    fileItems.add(fileItemFile);

    when(servletFileUploadMock.parseRequest(any(HttpServletRequest.class))).thenReturn(fileItems);

    // Simula falha no upload
    when(myUtilitiesMock.UploadFile(eq(fileItemFile), anyString(), any()))
            .thenReturn("Problem with upload");

    try (MockedStatic<ServletFileUpload> uploadMock = mockStatic(ServletFileUpload.class)) {
        uploadMock.when(() -> ServletFileUpload.isMultipartContent(any(HttpServletRequest.class))).thenReturn(true);

        // Act
        int result = dao.addproduct(request);

        // Assert
        verify(preparedStatement, never()).setString(anyInt(), anyString());
        verify(preparedStatement, never()).setInt(anyInt(), anyInt());
        verify(preparedStatement, never()).executeUpdate();
        assertEquals(0, result);
    }
}

@Test
void testShouldReturnZeroWhenDatabaseFails() throws Exception {
    // Simula os campos manualmente (sem método auxiliar)
    when(fileItemFormField1.isFormField()).thenReturn(true);
    when(fileItemFormField1.getFieldName()).thenReturn("pname");
    when(fileItemFormField1.getString()).thenReturn("Galaxy Watch Ultra");

    when(fileItemFormField2.isFormField()).thenReturn(true);
    when(fileItemFormField2.getFieldName()).thenReturn("pprice");
    when(fileItemFormField2.getString()).thenReturn("2799");

    when(fileItemFormField3.isFormField()).thenReturn(true);
    when(fileItemFormField3.getFieldName()).thenReturn("pquantity");
    when(fileItemFormField3.getString()).thenReturn("2");

    when(fileItemFormField4.isFormField()).thenReturn(true);
    when(fileItemFormField4.getFieldName()).thenReturn("bname");
    when(fileItemFormField4.getString()).thenReturn("samsung");

    when(fileItemFormField5.isFormField()).thenReturn(true);
    when(fileItemFormField5.getFieldName()).thenReturn("cname");
    when(fileItemFormField5.getString()).thenReturn("watch");

    when(fileItemFile.isFormField()).thenReturn(false);

    List<FileItem> fileItems = new ArrayList<>();
    fileItems.add(fileItemFormField1);
    fileItems.add(fileItemFormField2);
    fileItems.add(fileItemFormField3);
    fileItems.add(fileItemFormField4);
    fileItems.add(fileItemFormField5);
    fileItems.add(fileItemFile);

    when(servletFileUploadMock.parseRequest(any(HttpServletRequest.class))).thenReturn(fileItems);

    // Simula upload bem-sucedido
    when(myUtilitiesMock.UploadFile(eq(fileItemFile), anyString(), any())).thenReturn("galaxy_watch_ultra.jpg");

    // Simula falha ao preparar o SQL (banco)
    when(connection.prepareStatement(anyString())).thenThrow(new RuntimeException("DB error"));

    try (MockedStatic<ServletFileUpload> uploadMock = mockStatic(ServletFileUpload.class)) {
        uploadMock.when(() -> ServletFileUpload.isMultipartContent(any(HttpServletRequest.class))).thenReturn(true);

        int result = dao.addproduct(request);

        assertEquals(0, result);
        verify(preparedStatement, never()).executeUpdate();
    }
}

@Test
void testShouldReturnZeroWhenValuesAreNull() throws Exception {
    when(fileItemFormField1.isFormField()).thenReturn(true);
    when(fileItemFormField1.getFieldName()).thenReturn("pname");
    when(fileItemFormField1.getString()).thenReturn(null); // nome nulo

    when(fileItemFormField2.isFormField()).thenReturn(true);
    when(fileItemFormField2.getFieldName()).thenReturn("pprice");
    when(fileItemFormField2.getString()).thenReturn(null); // preço nulo

    when(fileItemFormField3.isFormField()).thenReturn(true);
    when(fileItemFormField3.getFieldName()).thenReturn("pquantity");
    when(fileItemFormField3.getString()).thenReturn(null); // quantidade nula

    when(fileItemFormField4.isFormField()).thenReturn(true);
    when(fileItemFormField4.getFieldName()).thenReturn("bname");
    when(fileItemFormField4.getString()).thenReturn("samsung");

    when(fileItemFormField5.isFormField()).thenReturn(true);
    when(fileItemFormField5.getFieldName()).thenReturn("cname");
    when(fileItemFormField5.getString()).thenReturn("watch");

    when(fileItemFile.isFormField()).thenReturn(false);

    List<FileItem> fileItems = new ArrayList<>();
    fileItems.add(fileItemFormField1);
    fileItems.add(fileItemFormField2);
    fileItems.add(fileItemFormField3);
    fileItems.add(fileItemFormField4);
    fileItems.add(fileItemFormField5);
    fileItems.add(fileItemFile);

    when(servletFileUploadMock.parseRequest(any(HttpServletRequest.class))).thenReturn(fileItems);
    when(myUtilitiesMock.UploadFile(eq(fileItemFile), anyString(), any())).thenReturn("produto_null.jpg");

    try (MockedStatic<ServletFileUpload> uploadMock = mockStatic(ServletFileUpload.class)) {
        uploadMock.when(() -> ServletFileUpload.isMultipartContent(any(HttpServletRequest.class))).thenReturn(true);

        int result = dao.addproduct(request);

        assertEquals(0, result);
        verify(connection, never()).prepareStatement(anyString());
        verify(preparedStatement, never()).executeUpdate();
    }
}



// Valor Limite Superior
@Test
void testShouldAddProductWithUpperBoundaryValues() throws Exception {
    int max = Integer.MAX_VALUE;

    when(fileItemFormField1.isFormField()).thenReturn(true);
    when(fileItemFormField1.getFieldName()).thenReturn("pname");
    when(fileItemFormField1.getString()).thenReturn("Produto Máximo");

    when(fileItemFormField2.isFormField()).thenReturn(true);
    when(fileItemFormField2.getFieldName()).thenReturn("pprice");
    when(fileItemFormField2.getString()).thenReturn(String.valueOf(max));

    when(fileItemFormField3.isFormField()).thenReturn(true);
    when(fileItemFormField3.getFieldName()).thenReturn("pquantity");
    when(fileItemFormField3.getString()).thenReturn(String.valueOf(max));

    when(fileItemFormField4.isFormField()).thenReturn(true);
    when(fileItemFormField4.getFieldName()).thenReturn("bname");
    when(fileItemFormField4.getString()).thenReturn("lenovo");

    when(fileItemFormField5.isFormField()).thenReturn(true);
    when(fileItemFormField5.getFieldName()).thenReturn("cname");
    when(fileItemFormField5.getString()).thenReturn("watch");

    when(fileItemFile.isFormField()).thenReturn(false);

    List<FileItem> fileItems = new ArrayList<>();
    fileItems.add(fileItemFormField1);
    fileItems.add(fileItemFormField2);
    fileItems.add(fileItemFormField3);
    fileItems.add(fileItemFormField4);
    fileItems.add(fileItemFormField5);
    fileItems.add(fileItemFile);

    when(servletFileUploadMock.parseRequest(any(HttpServletRequest.class))).thenReturn(fileItems);
    when(myUtilitiesMock.UploadFile(eq(fileItemFile), anyString(), any())).thenReturn("produto_maximo.jpg");
    when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
    when(preparedStatement.executeUpdate()).thenReturn(1);

    try (MockedStatic<ServletFileUpload> uploadMock = mockStatic(ServletFileUpload.class)) {
        uploadMock.when(() -> ServletFileUpload.isMultipartContent(any(HttpServletRequest.class))).thenReturn(true);

        int result = dao.addproduct(request);

        assertEquals(1, result);
        verify(preparedStatement).setInt(2, max);
        verify(preparedStatement).setInt(3, max);
    }
}

// Valor Limite Inferior
@Test
void testShouldAddProductWithLowerBoundaryValues() throws Exception {
    when(fileItemFormField1.isFormField()).thenReturn(true);
    when(fileItemFormField1.getFieldName()).thenReturn("pname");
    when(fileItemFormField1.getString()).thenReturn("Produto Zero");

    when(fileItemFormField2.isFormField()).thenReturn(true);
    when(fileItemFormField2.getFieldName()).thenReturn("pprice");
    when(fileItemFormField2.getString()).thenReturn("1");

    when(fileItemFormField3.isFormField()).thenReturn(true);
    when(fileItemFormField3.getFieldName()).thenReturn("pquantity");
    when(fileItemFormField3.getString()).thenReturn("1");

    when(fileItemFormField4.isFormField()).thenReturn(true);
    when(fileItemFormField4.getFieldName()).thenReturn("bname");
    when(fileItemFormField4.getString()).thenReturn("samsung");

    when(fileItemFormField5.isFormField()).thenReturn(true);
    when(fileItemFormField5.getFieldName()).thenReturn("cname");
    when(fileItemFormField5.getString()).thenReturn("watch");

    when(fileItemFile.isFormField()).thenReturn(false);

    List<FileItem> fileItems = new ArrayList<>();
    fileItems.add(fileItemFormField1);
    fileItems.add(fileItemFormField2);
    fileItems.add(fileItemFormField3);
    fileItems.add(fileItemFormField4);
    fileItems.add(fileItemFormField5);
    fileItems.add(fileItemFile);

    when(servletFileUploadMock.parseRequest(any(HttpServletRequest.class))).thenReturn(fileItems);
    when(myUtilitiesMock.UploadFile(eq(fileItemFile), anyString(), any())).thenReturn("produto_zero.jpg");
    when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
    when(preparedStatement.executeUpdate()).thenReturn(1);

    try (MockedStatic<ServletFileUpload> uploadMock = mockStatic(ServletFileUpload.class)) {
        uploadMock.when(() -> ServletFileUpload.isMultipartContent(any(HttpServletRequest.class))).thenReturn(true);

        int result = dao.addproduct(request);

        assertEquals(1, result);
        verify(preparedStatement).setInt(2, 1);
        verify(preparedStatement).setInt(3, 1);
    }
}

// Valor Acima  do Limite Superior
@Test
void testShouldReturnZeroWhenValuesAreAboveUpperLimit() throws Exception {
    String AboveMax = "999999999999";
    when(fileItemFormField1.isFormField()).thenReturn(true);
    when(fileItemFormField1.getFieldName()).thenReturn("pname");
    when(fileItemFormField1.getString()).thenReturn("Produto Inválido Superior");

    when(fileItemFormField2.isFormField()).thenReturn(true);
    when(fileItemFormField2.getFieldName()).thenReturn("pprice");
    when(fileItemFormField2.getString()).thenReturn(AboveMax);

    when(fileItemFormField3.isFormField()).thenReturn(true);
    when(fileItemFormField3.getFieldName()).thenReturn("pquantity");
    when(fileItemFormField3.getString()).thenReturn(AboveMax);

    when(fileItemFormField4.isFormField()).thenReturn(true);
    when(fileItemFormField4.getFieldName()).thenReturn("bname");
    when(fileItemFormField4.getString()).thenReturn("samsung");

    when(fileItemFormField5.isFormField()).thenReturn(true);
    when(fileItemFormField5.getFieldName()).thenReturn("cname");
    when(fileItemFormField5.getString()).thenReturn("watch");

    when(fileItemFile.isFormField()).thenReturn(false);

    List<FileItem> fileItems = new ArrayList<>();
    fileItems.add(fileItemFormField1);
    fileItems.add(fileItemFormField2);
    fileItems.add(fileItemFormField3);
    fileItems.add(fileItemFormField4);
    fileItems.add(fileItemFormField5);
    fileItems.add(fileItemFile);

    when(servletFileUploadMock.parseRequest(any(HttpServletRequest.class))).thenReturn(fileItems);
    when(myUtilitiesMock.UploadFile(eq(fileItemFile), anyString(), any())).thenReturn("produto_invalido_superior.jpg");

    try (MockedStatic<ServletFileUpload> uploadMock = mockStatic(ServletFileUpload.class)) {
        uploadMock.when(() -> ServletFileUpload.isMultipartContent(any(HttpServletRequest.class))).thenReturn(true);

        int result = dao.addproduct(request);

        assertEquals(0, result);
        verify(preparedStatement, never()).executeUpdate();

    }
}

// Valor Abaixo do Limite Inferior
@Test
void testShouldReturnZeroWhenValuesAreBelowLowerLimit() throws Exception {
    int invalidLow= -1;
    when(fileItemFormField1.isFormField()).thenReturn(true);
    when(fileItemFormField1.getFieldName()).thenReturn("pname");
    when(fileItemFormField1.getString()).thenReturn("Produto Inválido Inferior");

    when(fileItemFormField2.isFormField()).thenReturn(true);
    when(fileItemFormField2.getFieldName()).thenReturn("pprice");
    when(fileItemFormField2.getString()).thenReturn(String.valueOf(invalidLow));

    when(fileItemFormField3.isFormField()).thenReturn(true);
    when(fileItemFormField3.getFieldName()).thenReturn("pquantity");
    when(fileItemFormField3.getString()).thenReturn(String.valueOf(invalidLow));

    when(fileItemFormField4.isFormField()).thenReturn(true);
    when(fileItemFormField4.getFieldName()).thenReturn("bname");
    when(fileItemFormField4.getString()).thenReturn("samsung");

    when(fileItemFormField5.isFormField()).thenReturn(true);
    when(fileItemFormField5.getFieldName()).thenReturn("cname");
    when(fileItemFormField5.getString()).thenReturn("watch");

    when(fileItemFile.isFormField()).thenReturn(false);

    List<FileItem> fileItems = new ArrayList<>();
    fileItems.add(fileItemFormField1);
    fileItems.add(fileItemFormField2);
    fileItems.add(fileItemFormField3);
    fileItems.add(fileItemFormField4);
    fileItems.add(fileItemFormField5);
    fileItems.add(fileItemFile);

    when(servletFileUploadMock.parseRequest(any(HttpServletRequest.class))).thenReturn(fileItems);
    when(myUtilitiesMock.UploadFile(eq(fileItemFile), anyString(), any())).thenReturn("produto_invalido_inferior.jpg");

    try (MockedStatic<ServletFileUpload> uploadMock = mockStatic(ServletFileUpload.class)) {
        uploadMock.when(() -> ServletFileUpload.isMultipartContent(any(HttpServletRequest.class))).thenReturn(true);

        int result = dao.addproduct(request);

        assertEquals(0, result);
        verify(connection, never()).prepareStatement(anyString());
        verify(preparedStatement, never()).executeUpdate();

    }
}


@Test
void testShouldAddProductWithNoName() throws Exception {
    when(fileItemFormField1.isFormField()).thenReturn(true);
    when(fileItemFormField1.getFieldName()).thenReturn("pname");
    when(fileItemFormField1.getString()).thenReturn("");

    when(fileItemFormField2.isFormField()).thenReturn(true);
    when(fileItemFormField2.getFieldName()).thenReturn("pprice");
    when(fileItemFormField2.getString()).thenReturn("1");

    when(fileItemFormField3.isFormField()).thenReturn(true);
    when(fileItemFormField3.getFieldName()).thenReturn("pquantity");
    when(fileItemFormField3.getString()).thenReturn("1");

    when(fileItemFormField4.isFormField()).thenReturn(true);
    when(fileItemFormField4.getFieldName()).thenReturn("bname");
    when(fileItemFormField4.getString()).thenReturn("samsung");

    when(fileItemFormField5.isFormField()).thenReturn(true);
    when(fileItemFormField5.getFieldName()).thenReturn("cname");
    when(fileItemFormField5.getString()).thenReturn("watch");

    when(fileItemFile.isFormField()).thenReturn(false);

    List<FileItem> fileItems = new ArrayList<>();
    fileItems.add(fileItemFormField1);
    fileItems.add(fileItemFormField2);
    fileItems.add(fileItemFormField3);
    fileItems.add(fileItemFormField4);
    fileItems.add(fileItemFormField5);
    fileItems.add(fileItemFile);

    when(servletFileUploadMock.parseRequest(any(HttpServletRequest.class))).thenReturn(fileItems);
    when(myUtilitiesMock.UploadFile(eq(fileItemFile), anyString(), any())).thenReturn("produto_vazio.jpg");

    try (MockedStatic<ServletFileUpload> uploadMock = mockStatic(ServletFileUpload.class)) {
        uploadMock.when(() -> ServletFileUpload.isMultipartContent(any(HttpServletRequest.class))).thenReturn(true);

        int result = dao.addproduct(request);

        assertEquals(0, result);
        verify(connection, never()).prepareStatement(anyString());
        verify(preparedStatement, never()).executeUpdate();

    }
}



@Test
void testShouldNotInsertWhenPriceIsInvalidString() throws Exception {
    when(fileItemFormField1.getFieldName()).thenReturn("pname");
    when(fileItemFormField1.getString()).thenReturn("Produto Teste");

    when(fileItemFormField2.getFieldName()).thenReturn("pprice");
    when(fileItemFormField2.getString()).thenReturn("preço_invalido"); // força NumberFormatException

    when(fileItemFormField3.getFieldName()).thenReturn("pquantity");
    when(fileItemFormField3.getString()).thenReturn("1");

    when(fileItemFormField4.getFieldName()).thenReturn("bname");
    when(fileItemFormField4.getString()).thenReturn("sony");

    when(fileItemFormField5.getFieldName()).thenReturn("cname");
    when(fileItemFormField5.getString()).thenReturn("tv");

    when(fileItemFile.isFormField()).thenReturn(false);

    List<FileItem> fileItems = new ArrayList<>();
    fileItems.add(fileItemFormField1);
    fileItems.add(fileItemFormField2);
    fileItems.add(fileItemFormField3);
    fileItems.add(fileItemFormField4);
    fileItems.add(fileItemFormField5);
    fileItems.add(fileItemFile);

    when(servletFileUploadMock.parseRequest(any(HttpServletRequest.class))).thenReturn(fileItems);
    when(myUtilitiesMock.UploadFile(eq(fileItemFile), anyString(), any())).thenReturn("produto_invalido.jpg");

    try (MockedStatic<ServletFileUpload> uploadMock = mockStatic(ServletFileUpload.class)) {
        uploadMock.when(() -> ServletFileUpload.isMultipartContent(any(HttpServletRequest.class))).thenReturn(true);
        int result = dao.addproduct(request);
        assertEquals(0, result);
    }
}

@Test
void testShouldIgnoreUnknownFormFieldGracefully() throws Exception {
    when(fileItemFormField1.isFormField()).thenReturn(true);
    when(fileItemFormField1.getFieldName()).thenReturn("unknownField");
    when(fileItemFormField1.getString()).thenReturn("algum valor");

    List<FileItem> fileItems = new ArrayList<>();
    fileItems.add(fileItemFormField1);

    when(servletFileUploadMock.parseRequest(any(HttpServletRequest.class))).thenReturn(fileItems);

    try (MockedStatic<ServletFileUpload> uploadMock = mockStatic(ServletFileUpload.class)) {
        uploadMock.when(() -> ServletFileUpload.isMultipartContent(any(HttpServletRequest.class))).thenReturn(true);
        int result = dao.addproduct(request);
        assertEquals(0, result); // Não insere
    }
}

@Test
void testShouldReturnZeroWhenParseRequestFails() throws Exception {
    when(servletFileUploadMock.parseRequest(any(HttpServletRequest.class))).thenThrow(new FileUploadException("Erro no upload"));

    try (MockedStatic<ServletFileUpload> uploadMock = mockStatic(ServletFileUpload.class)) {
        uploadMock.when(() -> ServletFileUpload.isMultipartContent(any(HttpServletRequest.class))).thenReturn(true);

        int result = dao.addproduct(request);

        assertEquals(0, result);
    }
}

@Test
void testShouldReturnZeroWhenRequestIsNotMultipart() throws Exception {
    try (MockedStatic<ServletFileUpload> uploadMock = mockStatic(ServletFileUpload.class)) {
        uploadMock.when(() -> ServletFileUpload.isMultipartContent(any(HttpServletRequest.class))).thenReturn(false);

        int result = dao.addproduct(request);

        assertEquals(0, result);
    }
}

@Test
void testShouldReturnZeroWhenFileUploadThrowsException() throws Exception {
    // Arrange
    when(fileItemFormField1.isFormField()).thenReturn(true);
    when(fileItemFormField1.getFieldName()).thenReturn("pname");
    when(fileItemFormField1.getString()).thenReturn("Produto com Falha Upload Excecao");

    when(fileItemFormField2.isFormField()).thenReturn(true);
    when(fileItemFormField2.getFieldName()).thenReturn("pprice");
    when(fileItemFormField2.getString()).thenReturn("100");

    when(fileItemFormField3.isFormField()).thenReturn(true);
    when(fileItemFormField3.getFieldName()).thenReturn("pquantity");
    when(fileItemFormField3.getString()).thenReturn("1");

    when(fileItemFormField4.isFormField()).thenReturn(true);
    when(fileItemFormField4.getFieldName()).thenReturn("bname");
    when(fileItemFormField4.getString()).thenReturn("samsung");

    when(fileItemFormField5.isFormField()).thenReturn(true);
    when(fileItemFormField5.getFieldName()).thenReturn("cname");
    when(fileItemFormField5.getString()).thenReturn("watch");

    when(fileItemFile.isFormField()).thenReturn(false);

    List<FileItem> fileItems = new ArrayList<>();
    fileItems.add(fileItemFormField1);
    fileItems.add(fileItemFormField2);
    fileItems.add(fileItemFormField3);
    fileItems.add(fileItemFormField4);
    fileItems.add(fileItemFormField5);
    fileItems.add(fileItemFile);

    when(servletFileUploadMock.parseRequest(any(HttpServletRequest.class))).thenReturn(fileItems);

    // Simula que UploadFile lança uma exceção
    when(myUtilitiesMock.UploadFile(eq(fileItemFile), anyString(), any()))
            .thenThrow(new RuntimeException("Simulated upload exception"));

    try (MockedStatic<ServletFileUpload> uploadMock = mockStatic(ServletFileUpload.class)) {
        uploadMock.when(() -> ServletFileUpload.isMultipartContent(any(HttpServletRequest.class))).thenReturn(true);

        // Act
        int result = dao.addproduct(request);

        // Assert
        assertEquals(0, result);
        // Verifica que não houve tentativa de inserir no banco
        verify(connection, never()).prepareStatement(anyString());
        verify(preparedStatement, never()).executeUpdate();

    }
}



@Test
    void testShouldReturnZeroWhenFileItemIsFormField() throws Exception {
        // Simula campos do formulário (os 5 primeiros, como antes)
        when(fileItemFormField1.isFormField()).thenReturn(true);
        when(fileItemFormField1.getFieldName()).thenReturn("pname");
        when(fileItemFormField1.getString()).thenReturn("Produto Sem Imagem Real");

        when(fileItemFormField2.isFormField()).thenReturn(true);
        when(fileItemFormField2.getFieldName()).thenReturn("pprice");
        when(fileItemFormField2.getString()).thenReturn("10");

        when(fileItemFormField3.isFormField()).thenReturn(true);
        when(fileItemFormField3.getFieldName()).thenReturn("pquantity");
        when(fileItemFormField3.getString()).thenReturn("1");

        when(fileItemFormField4.isFormField()).thenReturn(true);
        when(fileItemFormField4.getFieldName()).thenReturn("bname");
        when(fileItemFormField4.getString()).thenReturn("samsung");

        when(fileItemFormField5.isFormField()).thenReturn(true);
        when(fileItemFormField5.getFieldName()).thenReturn("cname");
        when(fileItemFormField5.getString()).thenReturn("watch");

        // Simula que o 'fileItemFile' é um campo de formulário, não um arquivo
        when(fileItemFile.isFormField()).thenReturn(true);
        // *** ADICIONE ESTES DOIS MOCKS PARA EVITAR O NullPointerException ***
        when(fileItemFile.getFieldName()).thenReturn("fileItemAsFormField"); // Nome de campo para o que era para ser um arquivo
        when(fileItemFile.getString()).thenReturn("algum_valor_como_texto"); // Valor de texto para este campo


        List<FileItem> fileItems = new ArrayList<>();
        fileItems.add(fileItemFormField1);
        fileItems.add(fileItemFormField2);
        fileItems.add(fileItemFormField3);
        fileItems.add(fileItemFormField4);
        fileItems.add(fileItemFormField5);
        fileItems.add(fileItemFile); // fileItemFile agora se comporta como um FileItem de formulário

        when(servletFileUploadMock.parseRequest(any(HttpServletRequest.class))).thenReturn(fileItems);

        try (MockedStatic<ServletFileUpload> uploadMock = mockStatic(ServletFileUpload.class)) {
            uploadMock.when(() -> ServletFileUpload.isMultipartContent(any(HttpServletRequest.class))).thenReturn(true);

            // Act
            int result = dao.addproduct(request);

            // Assert
            // O isValid() deve falhar porque o pimage não será setado para um nome de arquivo válido,
            // e com a mudança no DAO, pimage vazio/problemático invalida o produto.
            assertEquals(0, result);
            // Verifica que UploadFile NÃO foi chamado, pois não era um arquivo real
            verify(myUtilitiesMock, never()).UploadFile(any(), anyString(), any());
            verify(connection, never()).prepareStatement(anyString());
            verify(preparedStatement, never()).executeUpdate();
        }
    }
    @Test
    void testShouldReturnZeroWhenPnameFieldIsMissing() throws Exception {
    // Simula apenas 4 campos válidos, omitindo o pname
    when(fileItemFormField2.isFormField()).thenReturn(true);
    when(fileItemFormField2.getFieldName()).thenReturn("pprice");
    when(fileItemFormField2.getString()).thenReturn("100");

    when(fileItemFormField3.isFormField()).thenReturn(true);
    when(fileItemFormField3.getFieldName()).thenReturn("pquantity");
    when(fileItemFormField3.getString()).thenReturn("2");

    when(fileItemFormField4.isFormField()).thenReturn(true);
    when(fileItemFormField4.getFieldName()).thenReturn("bname");
    when(fileItemFormField4.getString()).thenReturn("sony");

    when(fileItemFormField5.isFormField()).thenReturn(true);
    when(fileItemFormField5.getFieldName()).thenReturn("cname");
    when(fileItemFormField5.getString()).thenReturn("tv");

    when(fileItemFile.isFormField()).thenReturn(false);

    List<FileItem> fileItems = new ArrayList<>();
    // OMITIDO: fileItemFormField1 (pname)
    fileItems.add(fileItemFormField2);
    fileItems.add(fileItemFormField3);
    fileItems.add(fileItemFormField4);
    fileItems.add(fileItemFormField5);
    fileItems.add(fileItemFile);

    when(servletFileUploadMock.parseRequest(any(HttpServletRequest.class))).thenReturn(fileItems);
    when(myUtilitiesMock.UploadFile(eq(fileItemFile), anyString(), any())).thenReturn("produto_sem_nome.jpg");

    try (MockedStatic<ServletFileUpload> uploadMock = mockStatic(ServletFileUpload.class)) {
        uploadMock.when(() -> ServletFileUpload.isMultipartContent(any(HttpServletRequest.class))).thenReturn(true);
        int result = dao.addproduct(request);
        assertEquals(0, result);
        verify(connection, never()).prepareStatement(anyString());
    }
}

@Test
void testShouldReturnZeroWhenQuantityIsInvalidString() throws Exception {
    when(fileItemFormField1.getFieldName()).thenReturn("pname");
    when(fileItemFormField1.getString()).thenReturn("Produto com qtde inválida");

    when(fileItemFormField2.getFieldName()).thenReturn("pprice");
    when(fileItemFormField2.getString()).thenReturn("100");

    when(fileItemFormField3.getFieldName()).thenReturn("pquantity");
    when(fileItemFormField3.getString()).thenReturn("um"); // deve causar NumberFormatException

    when(fileItemFormField4.getFieldName()).thenReturn("bname");
    when(fileItemFormField4.getString()).thenReturn("lenovo");

    when(fileItemFormField5.getFieldName()).thenReturn("cname");
    when(fileItemFormField5.getString()).thenReturn("tv");

    when(fileItemFile.isFormField()).thenReturn(false);

    List<FileItem> fileItems = new ArrayList<>();
    fileItems.add(fileItemFormField1);
    fileItems.add(fileItemFormField2);
    fileItems.add(fileItemFormField3);
    fileItems.add(fileItemFormField4);
    fileItems.add(fileItemFormField5);
    fileItems.add(fileItemFile);

    when(servletFileUploadMock.parseRequest(any(HttpServletRequest.class))).thenReturn(fileItems);
    when(myUtilitiesMock.UploadFile(eq(fileItemFile), anyString(), any())).thenReturn("produto_qtd_invalida.jpg");

    try (MockedStatic<ServletFileUpload> uploadMock = mockStatic(ServletFileUpload.class)) {
        uploadMock.when(() -> ServletFileUpload.isMultipartContent(any(HttpServletRequest.class))).thenReturn(true);
        int result = dao.addproduct(request);
        assertEquals(0, result);
    }
}

@Test
void testShouldReturnAllBrands() throws Exception {
    // Mocks
    ResultSet resultSet = mock(ResultSet.class);
    when(resultSet.next()).thenReturn(true, false); // 1 registro
    when(resultSet.getInt(1)).thenReturn(1); // bid
    when(resultSet.getString(2)).thenReturn("Marca A"); // bname

    PreparedStatement mockStmt = mock(PreparedStatement.class);
    when(mockStmt.executeQuery()).thenReturn(resultSet);

    Connection mockConn = mock(Connection.class);
    when(mockConn.prepareStatement(anyString())).thenReturn(mockStmt);

    // Cria o DAO com a conexão mockada
    DAO dao = new DAO(mockConn);

    // Executa
    List<brand> brands = dao.getAllbrand();

    // Verificações
    assertNotNull(brands);
    assertEquals(1, brands.size());
    assertEquals(1, brands.get(0).getBid());
    assertEquals("Marca A", brands.get(0).getBname());
}

@Test
void testShouldReturnAllCategories() throws Exception {
    // Mocks
    PreparedStatement stmt = mock(PreparedStatement.class);
    ResultSet resultSet = mock(ResultSet.class);

    when(connection.prepareStatement(anyString())).thenReturn(stmt);
    when(stmt.executeQuery()).thenReturn(resultSet);

    // Simula 2 registros no ResultSet
    when(resultSet.next()).thenReturn(true, true, false);
    when(resultSet.getInt(1)).thenReturn(10, 20);
    when(resultSet.getString(2)).thenReturn("Eletrônicos", "Roupas");

    // Instancia DAO real com a conexão mockada
    DAO dao = new DAO(connection);

    // Executa o método testado
    List<category> result = dao.getAllcategory();

    // Verificações
    assertNotNull(result);
    assertEquals(2, result.size());

    assertEquals(10, result.get(0).getCid());
    assertEquals("Eletrônicos", result.get(0).getCname());

    assertEquals(20, result.get(1).getCid());
    assertEquals("Roupas", result.get(1).getCname());
}

@Test
void testShouldReturnZeroWhenBrandAndCategoryAreUnknown() throws Exception {
    // Simula os campos do formulário com brand e category inexistentes
    when(fileItemFormField1.isFormField()).thenReturn(true);
    when(fileItemFormField1.getFieldName()).thenReturn("pname");
    when(fileItemFormField1.getString()).thenReturn("Produto Teste");

    when(fileItemFormField2.isFormField()).thenReturn(true);
    when(fileItemFormField2.getFieldName()).thenReturn("pprice");
    when(fileItemFormField2.getString()).thenReturn("100");

    when(fileItemFormField3.isFormField()).thenReturn(true);
    when(fileItemFormField3.getFieldName()).thenReturn("pquantity");
    when(fileItemFormField3.getString()).thenReturn("10");

    when(fileItemFormField4.isFormField()).thenReturn(true);
    when(fileItemFormField4.getFieldName()).thenReturn("bname");
    when(fileItemFormField4.getString()).thenReturn("Apple"); // força retorno 0

    when(fileItemFormField5.isFormField()).thenReturn(true);
    when(fileItemFormField5.getFieldName()).thenReturn("cname");
    when(fileItemFormField5.getString()).thenReturn("Headset"); // força retorno 0

    when(fileItemFile.isFormField()).thenReturn(false);

    List<FileItem> fileItems = List.of(fileItemFormField1, fileItemFormField2, fileItemFormField3,
                                      fileItemFormField4, fileItemFormField5, fileItemFile);

    when(servletFileUploadMock.parseRequest(any(HttpServletRequest.class))).thenReturn(fileItems);
    when(myUtilitiesMock.UploadFile(eq(fileItemFile), anyString(), any())).thenReturn("imagem.jpg");

    int result = dao.addproduct(request);

    assertEquals(0, result);
}

@Test
void testShouldInsertProductWhenBrandIsLenovo() throws Exception {
    // Preparar mocks dos FileItems simulando campos do formulário
    when(fileItemFormField1.isFormField()).thenReturn(true);
    when(fileItemFormField1.getFieldName()).thenReturn("pname");
    when(fileItemFormField1.getString()).thenReturn("Lenovo LOQ");

    when(fileItemFormField2.isFormField()).thenReturn(true);
    when(fileItemFormField2.getFieldName()).thenReturn("pprice");
    when(fileItemFormField2.getString()).thenReturn("100");

    when(fileItemFormField3.isFormField()).thenReturn(true);
    when(fileItemFormField3.getFieldName()).thenReturn("pquantity");
    when(fileItemFormField3.getString()).thenReturn("10");

    when(fileItemFormField4.isFormField()).thenReturn(true);
    when(fileItemFormField4.getFieldName()).thenReturn("bname");
    when(fileItemFormField4.getString()).thenReturn("lenovo");  // <-- case testado aqui

    when(fileItemFormField5.isFormField()).thenReturn(true);
    when(fileItemFormField5.getFieldName()).thenReturn("cname");
    when(fileItemFormField5.getString()).thenReturn("laptop");

    when(fileItemFile.isFormField()).thenReturn(false);

    List<FileItem> fileItems = List.of(fileItemFormField1, fileItemFormField2, fileItemFormField3,
                                      fileItemFormField4, fileItemFormField5, fileItemFile);

    when(servletFileUploadMock.parseRequest(any(HttpServletRequest.class))).thenReturn(fileItems);
    when(myUtilitiesMock.UploadFile(eq(fileItemFile), anyString(), any())).thenReturn("imagem.jpg");

    when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
    when(preparedStatement.executeUpdate()).thenReturn(1);

    int result = dao.addproduct(request);

    assertEquals(1, result); // produto deve ser inserido, marca reconhecida
}

}