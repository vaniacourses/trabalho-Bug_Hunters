import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import com.utility.MyUtilities;
import com.dao.DAO;

@ExtendWith(MockitoExtension.class)
public class AddNewProductTest {
    @Mock private HttpServletRequest request;
    @Mock private Connection connection;
    @Mock private PreparedStatement preparedStatement;
    @Mock private FileItem fileItemFormField1;
    @Mock private FileItem fileItemFormField2;
    @Mock private FileItem fileItemFormField3;
    @Mock private FileItem fileItemFormField4;
    @Mock private FileItem fileItemFormField5;
    @Mock private FileItem fileItemFile;
    @Mock private MyUtilities myUtilitiesMock;
    @Mock private ServletFileUpload servletFileUploadMock;

    private DAO dao;

    @BeforeEach
    void setUp() throws Exception {
        dao = new DAO(connection, myUtilitiesMock, servletFileUploadMock);
    }

    @Test
    void shouldAddProductSuccessfully() throws Exception {
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
    void shouldNotAddProductWhenUploadFails() throws Exception {
    // Arrange

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
}