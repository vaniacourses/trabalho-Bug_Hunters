package com.conn;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnect 
{

	private static Connection conn = null;
	
	public static Connection getConn()
	{
		try {
			
			Class.forName("com.mysql.cj.jdbc.Driver");
		//	conn = DriverManager.getConnection("jdbc:sqlite:mydatabase.db");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ecomerce","root","admin");
		}catch (Exception e) {
			e.printStackTrace();
		}

		
		
		return conn;
	}
	
}
