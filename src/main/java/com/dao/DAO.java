package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.servlet.ServletFileUpload;


import com.entity.category;
import com.entity.customer;
import com.utility.MyUtilities;
import com.entity.brand;

public class DAO {
	private static final Logger logger = Logger.getLogger(DAO.class.getName());
	
	private Connection conn;
	private MyUtilities myUtilities;
	private ServletFileUpload servletFileUpload;
	
	// Construtor padrão (produção)
    public DAO(Connection conn) {
        this.conn = conn;
        this.myUtilities = new MyUtilities();
    }

	public DAO(Connection conn, MyUtilities myUtilities, ServletFileUpload servletFileUpload) {
    this.conn = conn;
    this.myUtilities = myUtilities;
    this.servletFileUpload = servletFileUpload;
}
	
	
	// list all brand
	public List<brand> getAllbrand(){
		List<brand> listb = new ArrayList<brand>();
		
		try {
			String sql = "select * from brand";
			try (PreparedStatement ps = conn.prepareStatement(sql);
				 ResultSet rs = ps.executeQuery()) {
				
				while(rs.next())
				{
					brand b = new brand();
					b.setBid(rs.getInt(1));
					b.setBname(rs.getString(2));
					listb.add(b);
				}
			}
				
		}catch (Exception e) {
			logger.log(Level.SEVERE, "Error retrieving all brands", e);
		}
		
		return listb;
	}
	
	
	// list all category
	
	public List<category> getAllcategory(){
		List<category> listc = new ArrayList<category>();
		
		try {
			String sql = "select * from category";
			try (PreparedStatement ps = conn.prepareStatement(sql);
				 ResultSet rs = ps.executeQuery()) {
				
				while(rs.next())
				{
					category c = new category();
					c.setCid(rs.getInt(1));
					c.setCname(rs.getString(2));
					listc.add(c);
				}
			}
				
		}catch (Exception e) {
			logger.log(Level.SEVERE, "Error retrieving all categories", e);
		}
		
		return listc;
	}

public int addproduct(HttpServletRequest request) {
    String path = "C://Users//calis//IdeaProjects//trabalho-Bug_Hunters//src//main//webapp//images";
    int a = 0;
    
    String pname = "";
    int pprice = 0;
    int pquantity = 0;
    String pimage = "";
    int bid = 0;
    int cid = 0;

    try {
        // Use o mock/injetado!
        List<FileItem> multiparts = servletFileUpload.parseRequest(request);

        for (FileItem item1 : multiparts) {
            if (item1.isFormField()) {
                if (item1.getFieldName().equals("pname"))
                    pname = item1.getString();

                if (item1.getFieldName().equals("pprice"))
                    pprice = Integer.parseInt(item1.getString());

                if (item1.getFieldName().equals("pquantity"))
                    pquantity = Integer.parseInt(item1.getString());

                if (item1.getFieldName().equals("bname")) {
                    if (item1.getString().equals("samsung"))
                        bid = 1;
                    if (item1.getString().equals("sony"))
                        bid = 2;
                    if (item1.getString().equals("lenovo"))
                        bid = 3;
                    if (item1.getString().equals("acer"))
                        bid = 4;
                    if (item1.getString().equals("onida"))
                        bid = 5;
                }
                if (item1.getFieldName().equals("cname")) {
                    if (item1.getString().equals("laptop"))
                        cid = 1;
                    if (item1.getString().equals("tv"))
                        cid = 2;
                    if (item1.getString().equals("mobile"))
                        cid = 3;
                    if (item1.getString().equals("watch"))
                        cid = 4;
                }
            } else {
                ArrayList<String> ext = new ArrayList<>();
                ext.add(".jpg"); ext.add(".bmp"); ext.add(".jpeg"); ext.add(".png"); ext.add(".webp");
                pimage = myUtilities.UploadFile(item1, path, ext);
            }
        }

        if (!pimage.equals("Problem with upload")) {
            String sql = "insert into product(pname,pprice,pquantity,pimage,bid,cid) values(?,?,?,?,?,?)";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, pname);
                ps.setInt(2, pprice);
                ps.setInt(3, pquantity);
                ps.setString(4, pimage);
                ps.setInt(5, bid);
                ps.setInt(6, cid);
                ps.executeUpdate();
                a = 1;
            }
        }

        if (logger.isLoggable(Level.INFO)) {
            logger.info(String.format("Product details - pname: %s, pprice: %d, pquantity: %d, pimage: %s, bid: %d, cid: %d", 
                       pname, pprice, pquantity, pimage, bid, cid));
        }

    } catch (Exception e) {
        logger.log(Level.SEVERE, "Error adding product", e);
    } finally {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error closing connection", e);
        }
    }
    return a;
}

//display all customers

public List<customer> getAllCustomer()
{
	List<customer> list = new ArrayList <customer>();
	
	try {
		String sql = "select * from customer";
		try (PreparedStatement ps = conn.prepareStatement(sql);
			 ResultSet rs = ps.executeQuery()) {
			
			while(rs.next())
			{
				customer c = new customer();
				c.setName(rs.getString(1));
				c.setPassword(rs.getString(2));
				c.setEmail_Id(rs.getString(3));
				c.setContact_No(rs.getInt(4));
				list.add(c);
			}
		}
		
	}catch (Exception e) {
		logger.log(Level.SEVERE, "Error retrieving all customers", e);
	}
	
	return list;
}


//Delete Customer

	public boolean deleteCustomer(customer c)
	{
		boolean f = false;
		
		try {
			String sql = "delete from customer where Name = ? and Email_Id = ?";
			
			try (PreparedStatement ps = conn.prepareStatement(sql)) {
				ps.setString(1, c.getName());
				ps.setString(2, c.getEmail_Id());
				
				int i = ps.executeUpdate();
				
				if(i == 1)
				{
					f = true;
				}
			}
			
		}catch (Exception e) {
			logger.log(Level.SEVERE, "Error deleting customer", e);
		}
		
		return f;
	}

	
	// display selected customer

public List<customer> getCustomer(String eid)
{
	List<customer> list = new ArrayList <customer>();
	
	try {
		String sql = "select * from customer where Email_Id=?";
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, eid);
			try (ResultSet rs = ps.executeQuery()) {
				
				while(rs.next())
				{
					customer c = new customer();
					c.setName(rs.getString(1));
					c.setPassword(rs.getString(2));
					c.setEmail_Id(rs.getString(3));
					c.setContact_No(rs.getInt(4));
					list.add(c);
				}
			}
		}
		
	}catch (Exception e) {
		logger.log(Level.SEVERE, "Error retrieving customer by email", e);
	}
	
	return list;
}
	
}
