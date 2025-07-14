import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.*;
import org.mockito.*;

import com.entity.cart;
import com.dao.DAO2;
import com.servlet.addtocartnull;

class AddProdToCartTest {

    @Mock HttpServletRequest request;
    @Mock HttpServletResponse response;
    @Mock DAO2 dao;

    @Captor ArgumentCaptor<cart> cartCaptor;
    @Captor ArgumentCaptor<Cookie> cookieCaptor;

    addtocartnull servlet;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        servlet = new addtocartnull(dao);
    }

    // Método helper para mockar os parâmetros de request
    void mockRequestParameters() {
        when(request.getParameter("id")).thenReturn("Book");
        when(request.getParameter("ie")).thenReturn("Client");
        when(request.getParameter("ig")).thenReturn("Pen");
        when(request.getParameter("ih")).thenReturn("10");
        when(request.getParameter("ii")).thenReturn("2");
        when(request.getParameter("ij")).thenReturn("img.png");
    }

    @Test
    void testDoGet_ItemExists_UpdateSuccess() throws Exception {
        mockRequestParameters();

        when(dao.checkaddtocartnull(any(cart.class))).thenReturn(true);
        when(dao.updateaddtocartnull(any(cart.class))).thenReturn(1);

        servlet.doGet(request, response);

        // Verifica o objeto cart passado ao DAO
        verify(dao).checkaddtocartnull(cartCaptor.capture());
        cart c = cartCaptor.getValue();
        assertEquals("Book", c.getBname());
        assertEquals("Client", c.getCname());
        assertEquals("Pen", c.getPname());
        assertEquals(10, c.getPprice());
        assertEquals(2, c.getPquantity());
        assertEquals("img.png", c.getPimage());

        // Verifica cookie criado corretamente
        verify(response).addCookie(cookieCaptor.capture());
        Cookie cookie = cookieCaptor.getValue();
        assertEquals("cart", cookie.getName());
        assertEquals("cartt", cookie.getValue());
        assertEquals(10, cookie.getMaxAge());

        verify(response).sendRedirect("cartnull.jsp");
    }

    @Test
    void testDoGet_ItemExists_UpdateFail() throws Exception {
        mockRequestParameters();

        when(dao.checkaddtocartnull(any(cart.class))).thenReturn(true);
        when(dao.updateaddtocartnull(any(cart.class))).thenReturn(0); // update falhou

        servlet.doGet(request, response);

        verify(dao).checkaddtocartnull(cartCaptor.capture());
        cart c = cartCaptor.getValue();
        assertEquals("Book", c.getBname());

        verify(response, never()).addCookie(any());
        verify(response).sendRedirect("selecteditem.jsp");
    }

    @Test
    void testDoGet_ItemDoesNotExist_AddSuccess() throws Exception {
        mockRequestParameters();

        when(dao.checkaddtocartnull(any(cart.class))).thenReturn(false);
        when(dao.addtocartnull(any(cart.class))).thenReturn(1);

        servlet.doGet(request, response);

        verify(dao).checkaddtocartnull(cartCaptor.capture());
        cart c = cartCaptor.getValue();
        assertEquals("Book", c.getBname());

        verify(response).addCookie(cookieCaptor.capture());
        Cookie cookie = cookieCaptor.getValue();
        assertEquals(10, cookie.getMaxAge());

        verify(response).sendRedirect("cartnull.jsp");
    }

    @Test
    void testDoGet_ItemDoesNotExist_AddFail() throws Exception {
        mockRequestParameters();

        when(dao.checkaddtocartnull(any(cart.class))).thenReturn(false);
        when(dao.addtocartnull(any(cart.class))).thenReturn(0); // add falhou

        servlet.doGet(request, response);

        verify(dao).checkaddtocartnull(cartCaptor.capture());
        cart c = cartCaptor.getValue();
        assertEquals("Book", c.getBname());

        verify(response, never()).addCookie(any());
        verify(response).sendRedirect("selecteditem.jsp");
    }

    @Test
    void testDoGet_InvalidParameters_ThrowsException() {
        // Simular parâmetros inválidos que causariam NumberFormatException
        when(request.getParameter("ih")).thenReturn("abc");
        when(request.getParameter("ii")).thenReturn("def");

        // O servlet deve lançar exception ou tratá-la; aqui testamos exceção
        assertThrows(NumberFormatException.class, () -> {
            servlet.doGet(request, response);
        });

        // Nenhuma interação com DAO ou response esperada
        verifyNoInteractions(dao);
        verifyNoInteractions(response);
    }
}
