package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * Base DAO class containing common functionality shared across all DAO classes.
 * This eliminates code duplication while maintaining all existing functionality.
 */
public abstract class BaseDAO {
    protected static final Logger logger = Logger.getLogger(BaseDAO.class.getName());
    protected Connection conn;
    
    public BaseDAO(Connection conn) {
        this.conn = conn;
    }
    
    /**
     * Executes a query and maps results to a list of entities using a mapper function.
     * This eliminates the repetitive ResultSet mapping pattern.
     */
    protected <T> List<T> executeQuery(String sql, ResultSetMapper<T> mapper) {
        List<T> results = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                results.add(mapper.map(rs));
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error executing query: " + sql, e);
        }
        return results;
    }
    
    /**
     * Executes a query with parameters and maps results to a list of entities.
     */
    protected <T> List<T> executeQuery(String sql, Object[] parameters, ResultSetMapper<T> mapper) {
        List<T> results = new ArrayList<>();
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
            logger.log(Level.SEVERE, "Error executing parameterized query: " + sql, e);
        }
        return results;
    }
    
    /**
     * Executes an update statement (INSERT, UPDATE, DELETE) and returns the number of affected rows.
     * Standardizes the return value to 1 for success, 0 for failure.
     */
    protected int executeUpdate(String sql, Object[] parameters) {
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            for (int i = 0; i < parameters.length; i++) {
                ps.setObject(i + 1, parameters[i]);
            }
            int result = ps.executeUpdate();
            return result > 0 ? 1 : 0;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error executing update: " + sql, e);
            return 0;
        }
    }
    
    /**
     * Executes a simple update statement without parameters.
     */
    protected int executeUpdate(String sql) {
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            int result = ps.executeUpdate();
            return result > 0 ? 1 : 0;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error executing update: " + sql, e);
            return 0;
        }
    }
    
    /**
     * Checks if a record exists based on a query and parameters.
     * Standardizes the boolean check pattern used across multiple DAOs.
     */
    protected boolean checkExists(String sql, Object[] parameters) {
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            for (int i = 0; i < parameters.length; i++) {
                ps.setObject(i + 1, parameters[i]);
            }
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error checking existence: " + sql, e);
            return false;
        }
    }
    
    /**
     * Checks if a record exists based on a simple query without parameters.
     */
    protected boolean checkExists(String sql) {
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            return rs.next();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error checking existence: " + sql, e);
            return false;
        }
    }
    
    /**
     * Functional interface for mapping ResultSet to entity objects.
     * This eliminates the repetitive mapping code across all DAOs.
     */
    @FunctionalInterface
    protected interface ResultSetMapper<T> {
        T map(ResultSet rs) throws SQLException;
    }
    
    /**
     * Safely closes the database connection.
     */
    protected void closeConnection() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error closing connection", e);
        }
    }
} 