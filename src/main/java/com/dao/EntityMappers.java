package com.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.entity.brand;
import com.entity.cart;
import com.entity.category;
import com.entity.contactus;
import com.entity.customer;
import com.entity.laptop;
import com.entity.mobile;
import com.entity.order_details;
import com.entity.orders;
import com.entity.tv;
import com.entity.usermaster;
import com.entity.viewlist;
import com.entity.watch;

/**
 * Utility class containing entity mapping functions to eliminate repetitive ResultSet mapping code.
 * All mappers are static and can be used across all DAO classes.
 */
public class EntityMappers {
    
    /**
     * Maps ResultSet to brand entity
     */
    public static brand mapToBrand(ResultSet rs) throws SQLException {
        brand b = new brand();
        b.setBid(rs.getInt(1));
        b.setBname(rs.getString(2));
        return b;
    }
    
    /**
     * Maps ResultSet to category entity
     */
    public static category mapToCategory(ResultSet rs) throws SQLException {
        category c = new category();
        c.setCid(rs.getInt(1));
        c.setCname(rs.getString(2));
        return c;
    }
    
    /**
     * Maps ResultSet to customer entity
     */
    public static customer mapToCustomer(ResultSet rs) throws SQLException {
        customer c = new customer();
        c.setName(rs.getString(1));
        c.setPassword(rs.getString(2));
        c.setEmail_Id(rs.getString(3));
        c.setContact_No(rs.getInt(4));
        return c;
    }
    
    /**
     * Maps ResultSet to viewlist entity
     */
    public static viewlist mapToViewlist(ResultSet rs) throws SQLException {
        viewlist v = new viewlist();
        v.setBname(rs.getString(1));
        v.setCname(rs.getString(2));
        v.setPname(rs.getString(3));
        v.setPprice(rs.getInt(4));
        v.setPquantity(rs.getInt(5));
        v.setPimage(rs.getString(6));
        return v;
    }
    
    /**
     * Maps ResultSet to cart entity
     */
    public static cart mapToCart(ResultSet rs) throws SQLException {
        cart c = new cart();
        c.setName(rs.getString(1));
        c.setBname(rs.getString(2));
        c.setCname(rs.getString(3));
        c.setPname(rs.getString(4));
        c.setPprice(rs.getInt(5));
        c.setPquantity(rs.getInt(6));
        c.setPimage(rs.getString(7));
        return c;
    }
    
    /**
     * Maps ResultSet to orders entity
     */
    public static orders mapToOrders(ResultSet rs) throws SQLException {
        orders o = new orders();
        o.setOrder_Id(rs.getInt(1));
        o.setCustomer_Name(rs.getString(2));
        o.setCustomer_City(rs.getString(3));
        o.setDate(rs.getString(4));
        o.setTotal_Price(rs.getInt(5));
        o.setStatus(rs.getString(6));
        return o;
    }
    
    /**
     * Maps ResultSet to order_details entity
     */
    public static order_details mapToOrderDetails(ResultSet rs) throws SQLException {
        order_details od = new order_details();
        od.setDate(rs.getString(1));
        od.setName(rs.getString(2));
        od.setBname(rs.getString(3));
        od.setCname(rs.getString(4));
        od.setPname(rs.getString(5));
        od.setPprice(rs.getInt(6));
        od.setPquantity(rs.getInt(7));
        od.setPimage(rs.getString(8));
        return od;
    }
    
    /**
     * Maps ResultSet to contactus entity
     */
    public static contactus mapToContactus(ResultSet rs) throws SQLException {
        contactus c = new contactus();
        c.setId(rs.getInt(1));
        c.setName(rs.getString(2));
        c.setEmail_Id(rs.getString(3));
        c.setContact_No(rs.getInt(4));
        c.setMessage(rs.getString(5));
        return c;
    }
    
    /**
     * Maps ResultSet to tv entity
     */
    public static tv mapToTv(ResultSet rs) throws SQLException {
        tv t = new tv();
        t.setBname(rs.getString(1));
        t.setCname(rs.getString(2));
        t.setPname(rs.getString(3));
        t.setPprice(rs.getInt(4));
        t.setPquantity(rs.getInt(5));
        t.setPimage(rs.getString(6));
        return t;
    }
    
    /**
     * Maps ResultSet to laptop entity
     */
    public static laptop mapToLaptop(ResultSet rs) throws SQLException {
        laptop l = new laptop();
        l.setBname(rs.getString(1));
        l.setCname(rs.getString(2));
        l.setPname(rs.getString(3));
        l.setPprice(rs.getInt(4));
        l.setPquantity(rs.getInt(5));
        l.setPimage(rs.getString(6));
        return l;
    }
    
    /**
     * Maps ResultSet to mobile entity
     */
    public static mobile mapToMobile(ResultSet rs) throws SQLException {
        mobile m = new mobile();
        m.setBname(rs.getString(1));
        m.setCname(rs.getString(2));
        m.setPname(rs.getString(3));
        m.setPprice(rs.getInt(4));
        m.setPquantity(rs.getInt(5));
        m.setPimage(rs.getString(6));
        return m;
    }
    
    /**
     * Maps ResultSet to watch entity
     */
    public static watch mapToWatch(ResultSet rs) throws SQLException {
        watch w = new watch();
        w.setBname(rs.getString(1));
        w.setCname(rs.getString(2));
        w.setPname(rs.getString(3));
        w.setPprice(rs.getInt(4));
        w.setPquantity(rs.getInt(5));
        w.setPimage(rs.getString(6));
        return w;
    }
} 