package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.entity.cart;
import com.entity.customer;
import com.entity.order_details;
import com.entity.orders;

public class DAO4 extends BaseDAO {
	
	public DAO4(Connection conn) {
		super(conn);
	}
	
	
	//check cart by null
	
	public boolean checkcart()
	{
		return CartOperations.checkCartNull(conn);
	}
	
	//check cart by name
	
		public boolean checkcart2(String nm)
		{
			return CartOperations.checkCartByUser(conn, nm);
		}
	
	// add to orders
	
	public int addOrders(orders o) {
		String sql = "insert into orders(Customer_Name,Customer_City,Date,Total_Price,Status) values(?,?,?,?,?)" ;
		Object[] parameters = {o.getCustomer_Name(), o.getCustomer_City(), o.getDate(), o.getTotal_Price(), o.getStatus()};
		return executeUpdate(sql, parameters);
	}
	
	// add to orders
	
		public int addOrder_details() {
			String sql = "insert into order_details(Name,bname,cname,pname,pprice,pquantity,pimage) select * from cart where Name is NULL";
			return executeUpdate(sql);
		}
		
				//add to orders by name
	
		
			public int addOrder_details2(String st) {
				String sql = "insert into order_details(Name,bname,cname,pname,pprice,pquantity,pimage) select * from cart where Name = ?";
				Object[] parameters = {st};
				return executeUpdate(sql, parameters);
			}
			
		
				//delete cart null
		

		public int deletecart() {
			return CartOperations.deleteCartNull(conn);
		}
	
		
				//delete cart by name
		

		public int deletecart2(String st) {
			return CartOperations.deleteCartByUser(conn, st);
		}
	
		
		//update order_details
		
public int updateOrder_details(order_details od) {
	String sql = "update order_details set Date = ? , Name=? where Date is NULL";
	Object[] parameters = {od.getDate(), od.getName()};
	return executeUpdate(sql, parameters);
}



//update order_details 2

public int updateOrder_details2(order_details od) {
	String sql = "update order_details set Date = ? where Date is NULL";
	Object[] parameters = {od.getDate()};
	return executeUpdate(sql, parameters);
}
	
	
}
