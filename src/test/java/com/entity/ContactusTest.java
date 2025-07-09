package com.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ContactusTest {

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

    //baseado em possiveis defeitos
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
        c.setContact_No(-123456);
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

}
