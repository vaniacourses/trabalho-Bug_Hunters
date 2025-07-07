package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.Attributes.Name;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.entity.Product;
import com.entity.cart;
import com.entity.customer;
import com.entity.laptop;
import com.entity.mobile;
import com.entity.orders;
import com.entity.order_details;
import com.entity.tv;
import com.entity.usermaster;
import com.entity.viewlist;
import com.entity.watch;
import com.utility.MyUtilities;

public class DAO3 extends BaseDAO {
	
	public DAO3(Connection conn) {
		super(conn);
	}
	
	
	// view tv
	
	public List<tv> getAlltv(){
		String sql = "select * from tv";
		return executeQuery(sql, EntityMappers::mapToTv);
	}
		
	// view laptop
	
		public List<laptop> getAlllaptop(){
			String sql = "select * from laptop";
			return executeQuery(sql, EntityMappers::mapToLaptop);
		}

		// view mobile
		
			public List<mobile> getAllmobile(){
				String sql = "select * from mobile";
				return executeQuery(sql, EntityMappers::mapToMobile);
			}
			
			// view watch
			
			public List<watch> getAllwatch(){
				String sql = "select * from watch";
				return executeQuery(sql, EntityMappers::mapToWatch);
			}


	
	
		

	//==================================
			// addtocartnull
			
			public boolean checkaddtocartnull(cart c)
			{
				return CartOperations.checkAddToCartWithUser(conn, c);
			}
			
		// update cart	
			public int updateaddtocartnull(cart c) {
				return CartOperations.updateAddToCartWithUser(conn, c);
			}
			
			//
	public int addtocartnull(cart c) {
				return CartOperations.addToCartWithUser(conn, c);
			}
		
		
	
		
		// view orders
	
	
	public List<orders> getOrders(String o){
		String sql = "select * from orders where Customer_Name = ?";
		Object[] parameters = {o};
		return executeQuery(sql, parameters, EntityMappers::mapToOrders);
	}

	
	//view orders by Date
		

	public List<orders> getOrdersbydate(String d){
		String sql = "select * from orders where Date = ?";
		Object[] parameters = {d};
		return executeQuery(sql, parameters, EntityMappers::mapToOrders);
	}

			//view order_details by date
	
	public List<order_details> getOrderdetails(String d){
		String sql = "select * from Order_details where Date = ?";
		Object[] parameters = {d};
		return executeQuery(sql, parameters, EntityMappers::mapToOrderDetails);
	}

	
}
