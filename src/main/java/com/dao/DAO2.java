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
import com.entity.order_details;
import com.entity.orders;
import com.entity.usermaster;
import com.entity.viewlist;
import com.utility.MyUtilities;

public class DAO2 extends BaseDAO {
	
	public DAO2(Connection conn) {
		super(conn);
	}
	
	



	// viewproduct
	
	public List<viewlist> getAllviewlist(){
		String sql = "select * from viewlist";
		return executeQuery(sql, EntityMappers::mapToViewlist);
	}
		
	
	// check customer login
	
	public boolean checkcust(customer cust)
	{
		String sql = "select * from customer  where Password=? and Email_Id=?";
		Object[] parameters = {cust.getPassword(), cust.getEmail_Id()};
		return checkExists(sql, parameters);
	}
	
	// check admin login
	
		public boolean checkadmin(usermaster admin)
		{
			String sql = "select * from usermaster  where Name=? and Password=?";
			Object[] parameters = {admin.getName(), admin.getPassword()};
			return checkExists(sql, parameters);
		}
		
		// customer registration
		
		public int  addcustomer(customer ct) {
			String sql = "insert into customer(Name,Password,Email_Id,Contact_No) values(?,?,?,?)";
			Object[] parameters = {ct.getName(), ct.getPassword(), ct.getEmail_Id(), ct.getContact_No()};
			return executeUpdate(sql, parameters);
		}
		
//===================================================================================================================
		//view selected item
		
		// viewproduct
		
		public List<viewlist> getSelecteditem(String st){
			String sql = "select * from viewlist where Pimage = ?";
			Object[] parameters = {st};
			return executeQuery(sql, parameters, EntityMappers::mapToViewlist);
		}
			
		
		
// addtocartnull
		
		public boolean checkaddtocartnull(cart c)
		{
			return CartOperations.checkAddToCartNull(conn, c);
		}
		
	// update cart	
		public int updateaddtocartnull(cart c) {
			return CartOperations.updateAddToCartNull(conn, c);
		}
		
		//
public int addtocartnull(cart c) {
			return CartOperations.addToCartNull(conn, c);
		}
	
//===================================================================

// view cart

public List<cart> getSelectedcart(){
	return CartOperations.getSelectedCart(conn);
}
	
//
// view cart of specific customer

public List<cart> getcart(String ct){
	return CartOperations.getCartByCustomer(conn, ct);
}

// removecartnull

public int removecartnull(cart c) {
	return CartOperations.removeCartNull(conn, c);
}


// removecart

	public int removecart(cart c) {
		return CartOperations.removeCartWithUser(conn, c);
	}
	
	
	
	// check existing customer login name for new registration
	
	// check customer login
	
		public boolean checkcust2(customer cus)
		{
			String sql = "select * from customer  where Name=? or Email_Id=?";
			Object[] parameters = {cus.getName(), cus.getEmail_Id()};
			return checkExists(sql, parameters);
		}
		
		
// remove orders
		
public int removeorders(orders o) {
	String sql = "delete from orders where Order_Id= ?";
	Object[] parameters = {o.getOrder_Id()};
	return executeUpdate(sql, parameters);
}
		
}
