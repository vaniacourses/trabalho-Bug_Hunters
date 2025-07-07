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
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;


import com.entity.category;
import com.entity.customer;
import com.utility.MyUtilities;
import com.entity.Product;
import com.entity.customer;
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
		
		brand b = null;
		
		try {
			String sql = "select * from brand";
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next())
			{
				b = new brand();
				b.setBid(rs.getInt(1));
				b.setBname(rs.getString(2));
				listb.add(b);
				
			}
			
			
				
			}catch (Exception e) {
				e.printStackTrace();
			}
			
			return listb;
		}
	
	
	// list all category
	
	public List<category> getAllcategory(){
		List<category> listc = new ArrayList<category>();
		
		category c = null;
		
		try {
			String sql = "select * from category";
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next())
			{
				c = new category();
				c.setCid(rs.getInt(1));
				c.setCname(rs.getString(2));
				listc.add(c);
				
			}
			
			
				
			}catch (Exception e) {
				e.printStackTrace();
			}
			
			return listc;
		}

public int addproduct(HttpServletRequest request) {
    String path = "C://Users//calis//IdeaProjects//trabalho-Bug_Hunters//src//main//webapp//images";
    int a = 0;
    try {
        String pname = "";
        int pprice = 0;
        int pquantity = 0;
        String pimage = "";
        int bid = 0;
        int cid = 0;

        String sql = "insert into product(pname,pprice,pquantity,pimage,bid,cid) values(?,?,?,?,?,?)";
        PreparedStatement ps = conn.prepareStatement(sql);

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
            ps.setString(1, pname);
            ps.setInt(2, pprice);
            ps.setInt(3, pquantity);
            ps.setString(4, pimage);
            ps.setInt(5, bid);
            ps.setInt(6, cid);
            ps.executeUpdate();
            a = 1;
        }

        logger.info("Product details - pname: " + pname + ", pprice: " + pprice + 
                   ", pquantity: " + pquantity + ", pimage: " + pimage + 
                   ", bid: " + bid + ", cid: " + cid);

        conn.close();

    } catch (Exception e) {
        logger.log(Level.SEVERE, "Error adding product", e);
    }
    return a;
}

//display all customers

public List<customer> getAllCustomer()
{
	List<customer> list = new ArrayList <customer>();
	
	customer c = null;
	
	try {
		String sql = "select * from customer";
		PreparedStatement ps = conn.prepareStatement(sql);
		
	ResultSet rs = ps.executeQuery();
	
	while(rs.next())
	{
		c = new customer();
		c.setName(rs.getString(1));
		c.setPassword(rs.getString(2));
		c.setEmail_Id(rs.getString(3));
		c.setContact_No(rs.getInt(4));
		list.add(c);
		
	}
	
	
		
	}catch (Exception e) {
		e.printStackTrace();
	}
	
	return list;
}


//Delete Customer

	public boolean deleteCustomer(customer c)
	{
		boolean f = false;
		
		try {
			
			String sql = "delete from customer where Name = ? and Email_Id = ?";
			
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ps.setString(1, c.getName());
			ps.setString(2, c.getEmail_Id());
			
			
			int i = ps.executeUpdate();
			
			
			if(i == 1)
			{
				f = true;
			}
			
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return f;
	}

	
	// display selected customer

public List<customer> getCustomer(String eid)
{
	List<customer> list = new ArrayList <customer>();
	
	customer c = null;
	
	try {
		String sql = "select * from customer where Email_Id=?";
		PreparedStatement ps = conn.prepareStatement(sql);
		
		ps.setString(1, eid);
	ResultSet rs = ps.executeQuery();
	
	while(rs.next())
	{
		c = new customer();
		c.setName(rs.getString(1));
		c.setPassword(rs.getString(2));
		c.setEmail_Id(rs.getString(3));
		c.setContact_No(rs.getInt(4));
		list.add(c);
		
	}
	
	
		
	}catch (Exception e) {
		e.printStackTrace();
	}
	
	return list;
}
	
}
