package com.entity;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OrdersTest {
    private orders order;

    @BeforeEach
    public void setUp() {
        order = new orders();
    }

    @Test
    public void testDefaultConstructor() {
        assertNotNull(order);
        assertEquals(0, order.getOrder_Id());
        assertNull(order.getCustomer_Name());
        assertNull(order.getCustomer_City());
        assertNull(order.getDate());
        assertEquals(0, order.getTotal_Price());
        assertNull(order.getStatus());
    }

    @Test
    public void testSetAndGetOrderId() {
        int orderId = 123;
        order.setOrder_Id(orderId);
        assertEquals(orderId, order.getOrder_Id());
    }

    @Test
    public void testSetAndGetCustomerName() {
        String customerName = "John Doe";
        order.setCustomer_Name(customerName);
        assertEquals(customerName, order.getCustomer_Name());
    }

    @Test
    public void testSetAndGetCustomerCity() {
        String city = "New York";
        order.setCustomer_City(city);
        assertEquals(city, order.getCustomer_City());
    }

    @Test
    public void testSetAndGetDate() {
        String date = "2024-03-20";
        order.setDate(date);
        assertEquals(date, order.getDate());
    }

    @Test
    public void testSetAndGetTotalPrice() {
        int totalPrice = 1500;
        order.setTotal_Price(totalPrice);
        assertEquals(totalPrice, order.getTotal_Price());
    }

    @Test
    public void testSetAndGetStatus() {
        String status = "Processing";
        order.setStatus(status);
        assertEquals(status, order.getStatus());
    }

    @Test
    public void testCompleteOrderObject() {
        int orderId = 123;
        String customerName = "John Doe";
        String city = "New York";
        String date = "2024-03-20";
        int totalPrice = 1500;
        String status = "Processing";

        order.setOrder_Id(orderId);
        order.setCustomer_Name(customerName);
        order.setCustomer_City(city);
        order.setDate(date);
        order.setTotal_Price(totalPrice);
        order.setStatus(status);

        assertEquals(orderId, order.getOrder_Id());
        assertEquals(customerName, order.getCustomer_Name());
        assertEquals(city, order.getCustomer_City());
        assertEquals(date, order.getDate());
        assertEquals(totalPrice, order.getTotal_Price());
        assertEquals(status, order.getStatus());
    }
} 