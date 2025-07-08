package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;

import com.entity.cart;

/**
 * Utility class for cart operations to eliminate duplication between DAO2 and DAO3.
 * Contains common cart-related database operations.
 */
public class CartOperations {
    private static final Logger logger = Logger.getLogger(CartOperations.class.getName());
    
    /**
     * Checks if a cart item exists for anonymous user (Name is NULL)
     */
    public static boolean checkAddToCartNull(Connection conn, cart c) {
        String sql = "select * from cart where Name is NULL and bname=? and cname =? and pname = ? and pprice = ? and pimage = ?";
        Object[] parameters = {c.getBname(), c.getCname(), c.getPname(), c.getPprice(), c.getPimage()};
        return executeExistsCheck(conn, sql, parameters);
    }
    
    /**
     * Checks if a cart item exists for a specific user
     */
    public static boolean checkAddToCartWithUser(Connection conn, cart c) {
        String sql = "select * from cart where Name =? and bname=? and cname =? and pname = ? and pprice = ? and pimage = ?";
        Object[] parameters = {c.getName(), c.getBname(), c.getCname(), c.getPname(), c.getPprice(), c.getPimage()};
        return executeExistsCheck(conn, sql, parameters);
    }
    
    /**
     * Updates cart quantity for anonymous user
     */
    public static int updateAddToCartNull(Connection conn, cart c) {
        String sql = "update cart set pquantity = (pquantity + 1) where Name is NULL and bname = ? and cname = ? and pname = ? and pprice = ? and pimage = ?";
        Object[] parameters = {c.getBname(), c.getCname(), c.getPname(), c.getPprice(), c.getPimage()};
        return executeUpdate(conn, sql, parameters);
    }
    
    /**
     * Updates cart quantity for specific user
     */
    public static int updateAddToCartWithUser(Connection conn, cart c) {
        String sql = "update cart set pquantity = (pquantity + 1) where Name =? and bname = ? and cname = ? and pname = ? and pprice = ? and pimage = ?";
        Object[] parameters = {c.getName(), c.getBname(), c.getCname(), c.getPname(), c.getPprice(), c.getPimage()};
        return executeUpdate(conn, sql, parameters);
    }
    
    /**
     * Adds item to cart for anonymous user
     */
    public static int addToCartNull(Connection conn, cart c) {
        String sql = "insert into cart (bname,cname,pname,pprice,pquantity,pimage) values(?,?,?,?,?,?)";
        Object[] parameters = {c.getBname(), c.getCname(), c.getPname(), c.getPprice(), c.getPquantity(), c.getPimage()};
        return executeUpdate(conn, sql, parameters);
    }
    
    /**
     * Adds item to cart for specific user
     */
    public static int addToCartWithUser(Connection conn, cart c) {
        String sql = "insert into cart values(?,?,?,?,?,?,?)";
        Object[] parameters = {c.getName(), c.getBname(), c.getCname(), c.getPname(), c.getPprice(), c.getPquantity(), c.getPimage()};
        return executeUpdate(conn, sql, parameters);
    }
    
    /**
     * Gets all cart items for anonymous user
     */
    public static List<cart> getSelectedCart(Connection conn) {
        String sql = "select * from cart where Name is NULL";
        return executeQuery(conn, sql, EntityMappers::mapToCart);
    }
    
    /**
     * Gets cart items for specific customer
     */
    public static List<cart> getCartByCustomer(Connection conn, String customerName) {
        String sql = "select * from cart where Name = ?";
        Object[] parameters = {customerName};
        return executeQuery(conn, sql, parameters, EntityMappers::mapToCart);
    }
    
    /**
     * Removes cart item for anonymous user
     */
    public static int removeCartNull(Connection conn, cart c) {
        String sql = "delete from cart where Name is NULL and pimage = ?";
        Object[] parameters = {c.getPimage()};
        return executeUpdate(conn, sql, parameters);
    }
    
    /**
     * Removes cart item for specific user
     */
    public static int removeCartWithUser(Connection conn, cart c) {
        String sql = "delete from cart where Name= ? and pimage= ?";
        Object[] parameters = {c.getName(), c.getPimage()};
        return executeUpdate(conn, sql, parameters);
    }
    
    /**
     * Checks if cart has items for anonymous user
     */
    public static boolean checkCartNull(Connection conn) {
        String sql = "select * from cart where Name is NULL";
        return executeExistsCheck(conn, sql);
    }
    
    /**
     * Checks if cart has items for specific user
     */
    public static boolean checkCartByUser(Connection conn, String userName) {
        String sql = "select * from cart where Name= ?";
        Object[] parameters = {userName};
        return executeExistsCheck(conn, sql, parameters);
    }
    
    /**
     * Deletes all cart items for anonymous user
     */
    public static int deleteCartNull(Connection conn) {
        String sql = "delete from cart where Name is NULL";
        return executeUpdate(conn, sql);
    }
    
    /**
     * Deletes all cart items for specific user
     */
    public static int deleteCartByUser(Connection conn, String userName) {
        String sql = "delete from cart where Name = ?";
        Object[] parameters = {userName};
        return executeUpdate(conn, sql, parameters);
    }
    
    // Helper methods
    private static boolean executeExistsCheck(Connection conn, String sql, Object[] parameters) {
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            for (int i = 0; i < parameters.length; i++) {
                ps.setObject(i + 1, parameters[i]);
            }
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error checking cart existence: " + sql, e);
            return false;
        }
    }
    
    private static boolean executeExistsCheck(Connection conn, String sql) {
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            return rs.next();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error checking cart existence: " + sql, e);
            return false;
        }
    }
    
    private static int executeUpdate(Connection conn, String sql, Object[] parameters) {
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            for (int i = 0; i < parameters.length; i++) {
                ps.setObject(i + 1, parameters[i]);
            }
            int result = ps.executeUpdate();
            return result > 0 ? 1 : 0;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error executing cart update: " + sql, e);
            return 0;
        }
    }
    
    private static int executeUpdate(Connection conn, String sql) {
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            int result = ps.executeUpdate();
            return result > 0 ? 1 : 0;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error executing cart update: " + sql, e);
            return 0;
        }
    }
    
    private static List<cart> executeQuery(Connection conn, String sql, ResultSetMapper<cart> mapper) {
        List<cart> results = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                results.add(mapper.map(rs));
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error executing cart query: " + sql, e);
        }
        return results;
    }
    
    private static List<cart> executeQuery(Connection conn, String sql, Object[] parameters, ResultSetMapper<cart> mapper) {
        List<cart> results = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            for (int i = 0; i < parameters.length; i++) {
                ps.setObject(i + 1, parameters[i]);
            }
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    results.add(mapper.map(rs));
                }
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error executing parameterized cart query: " + sql, e);
        }
        return results;
    }
    
    @FunctionalInterface
    private interface ResultSetMapper<T> {
        T map(ResultSet rs) throws Exception;
    }
} 