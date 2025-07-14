package com.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ContactusTest {

    // Testes simples

    @Test
    public void testId() {
        contactus c = new contactus();
        c.setId(10);
        assertEquals(10, c.getId());
    }

    @Test
    public void testName() {
        contactus c = new contactus();
        c.setName("Maria");
        assertEquals("Maria", c.getName());
    }

    @Test
    public void testEmail() {
        contactus c = new contactus();
        c.setEmail_Id("maria@email.com");
        assertEquals("maria@email.com", c.getEmail_Id());
    }

    @Test
    public void testContactNo() {
        contactus c = new contactus();
        c.setContact_No(987654321);
        assertEquals(987654321, c.getContact_No());
    }

    @Test
    public void testMessage() {
        contactus c = new contactus();
        c.setMessage("Olá, isso é um teste!");
        assertEquals("Olá, isso é um teste!", c.getMessage());
    }

    // Testes baseados em possíveis defeitos

    @Test
    public void testSetAndGetEmailId_DefectCheck() {
        contactus c = new contactus();
        String email = "teste@email.com";
        c.setEmail_Id(email);
        assertNotNull(c.getEmail_Id());
        assertEquals(email, c.getEmail_Id());
    }

    @Test
    public void testSetContactNoWithNegativeValue() {
        contactus c = new contactus();
        c.setContact_No(-123456); // Número de contato negativo (caso de aresta)
        assertEquals(-123456, c.getContact_No());
    }

    @Test
    public void testMultipleSetters() {
        contactus c = new contactus();
        c.setId(100);
        c.setName("João");
        c.setEmail_Id("joao@email.com");
        c.setContact_No(12345);
        c.setMessage("Mensagem de teste");

        assertAll(
                () -> assertEquals(100, c.getId()),
                () -> assertEquals("João", c.getName()),
                () -> assertEquals("joao@email.com", c.getEmail_Id()),
                () -> assertEquals(12345, c.getContact_No()),
                () -> assertEquals("Mensagem de teste", c.getMessage())
        );
    }

    // Testes de arestas e valores limite

    // Nome nulo
    @Test
    public void testSetNameWithNull() {
        contactus c = new contactus();
        c.setName(null);
        assertNull(c.getName());
    }

    // Nome vazio
    @Test
    public void testSetNameWithEmptyString() {
        contactus c = new contactus();
        c.setName("");
        assertEquals("", c.getName());
    }

    // Email com formato inválido (sem @)
    @Test
    public void testSetEmailWithInvalidFormat() {
        contactus c = new contactus();
        String invalidEmail = "email_invalido";
        c.setEmail_Id(invalidEmail);
        assertEquals(invalidEmail, c.getEmail_Id()); // Não há validação implementada
    }

    // Email nulo
    @Test
    public void testSetEmailWithNull() {
        contactus c = new contactus();
        c.setEmail_Id(null);
        assertNull(c.getEmail_Id());
    }

    // Contato com valor zero (caso limite inferior)
    @Test
    public void testSetContactNoZero() {
        contactus c = new contactus();
        c.setContact_No(0);
        assertEquals(0, c.getContact_No());
    }

    // Contato com valor máximo do int
    @Test
    public void testSetContactNoMaxInt() {
        contactus c = new contactus();
        c.setContact_No(Integer.MAX_VALUE);
        assertEquals(Integer.MAX_VALUE, c.getContact_No());
    }

    // Contato com valor mínimo do int
    @Test
    public void testSetContactNoMinInt() {
        contactus c = new contactus();
        c.setContact_No(Integer.MIN_VALUE);
        assertEquals(Integer.MIN_VALUE, c.getContact_No());
    }

    // Mensagem nula
    @Test
    public void testSetMessageWithNull() {
        contactus c = new contactus();
        c.setMessage(null);
        assertNull(c.getMessage());
    }

    // Mensagem vazia
    @Test
    public void testSetMessageWithEmptyString() {
        contactus c = new contactus();
        c.setMessage("");
        assertEquals("", c.getMessage());
    }

    // Mensagem muito longa (caso de estresse)
    @Test
    public void testSetMessageWithLongText() {
        contactus c = new contactus();
        String longMessage = "A".repeat(10000); // Mensagem com 10.000 caracteres
        c.setMessage(longMessage);
        assertEquals(longMessage, c.getMessage());
    }
}
