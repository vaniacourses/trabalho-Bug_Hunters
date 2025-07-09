package com.conn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class teste_conn {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3308/ecomerce";
        String user = "root";
        String password = "adminroot@";

        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println("Conexão feita com sucesso!");
            conn.close();
        } catch (SQLException e) {
            System.out.println("Erro ao conectar: " + e.getMessage());
        }
    }
}
