package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.entity.cart;
import com.entity.contactus;
import com.entity.order_details;
import com.entity.orders;
import com.entity.viewlist;

public class DAO5 extends BaseDAO {
	
	public DAO5(Connection conn) {
		super(conn);
	}
	
	
			// view all cart
		
		public List<cart> getAllcart(){
			String sql = "select * from cart";
			return executeQuery(sql, EntityMappers::mapToCart);
		}
		
		
		// view all orders
		
		public List<orders> getAllorders(){
			String sql = "select * from orders";
			return executeQuery(sql, EntityMappers::mapToOrders);
		}
		
		
		// view all order_details
		
		
		public List<order_details> getAllorder_details(){
			String sql = "select * from order_details";
			return executeQuery(sql, EntityMappers::mapToOrderDetails);
		}
		
		
		//remove order_details
		
		

		public int removeorder_details(order_details c) {
		String sql = "delete from order_details where Date= ? and pimage= ?";
		Object[] parameters = {c.getDate(), c.getPimage()};
		return executeUpdate(sql, parameters);
		}
		
		
		// add to contact us
		
public int addContactus(contactus c) {
	String sql = "insert into Contactus(Name,Email_Id,Contact_No,Message) values(?,?,?,?)" ;
	Object[] parameters = {c.getName(), c.getEmail_Id(), c.getContact_No(), c.getMessage()};
	return executeUpdate(sql, parameters);
		}
	

	// view table contactus
public List<contactus> getcontactus(){
	String sql = "select * from Contactus";
	return executeQuery(sql, EntityMappers::mapToContactus);
	}

		//remove from contactus



	public int removecont(contactus c) {
	String sql = "delete from Contactus where id= ?";
	Object[] parameters = {c.getId()};
	return executeUpdate(sql, parameters);
	}
	
	

}
