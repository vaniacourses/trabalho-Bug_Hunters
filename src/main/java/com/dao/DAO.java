package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.entity.category;
import com.entity.customer;
import com.utility.MyUtilities;
import com.entity.brand;

public class DAO extends BaseDAO {
	private MyUtilities myUtilities;
	private ServletFileUpload servletFileUpload;
	
	// Construtor padrão (produção)
    public DAO(Connection conn) {
        super(conn);
        this.myUtilities = new MyUtilities();
    }

	public DAO(Connection conn, MyUtilities myUtilities, ServletFileUpload servletFileUpload) {
        super(conn);
        this.myUtilities = myUtilities;
        this.servletFileUpload = servletFileUpload;
    }
	
	
	// list all brand
	public List<brand> getAllbrand(){
		String sql = "select bid, bname from brand";
		return executeQuery(sql, EntityMappers::mapToBrand);
	}
	
	
	// list all category
	
	public List<category> getAllcategory(){
		String sql = "select cid, cname from category";
		return executeQuery(sql, EntityMappers::mapToCategory);
	}

public int addproduct(HttpServletRequest request) {
    String path = "C://Users//calis//IdeaProjects//trabalho-Bug_Hunters//src//main//webapp//images";
    int result = 0;

    try {
        List<FileItem> multiparts = servletFileUpload.parseRequest(request);
        ProductData productData = extractProductData(multiparts, path);

        // ✅ Validação completa (upload, preço, quantidade e nome)
        if (productData.isValid()) {
            result = insertProduct(productData);
            logProductDetails(productData);
        }

    } catch (Exception e) {
        logger.log(Level.SEVERE, "Error adding product", e);
    } finally {
        closeConnection();
    }
    return result;
}

private ProductData extractProductData(List<FileItem> multiparts, String path) {
    ProductData data = new ProductData();
    
    for (FileItem item : multiparts) {
        if (item.isFormField()) {
            processFormField(item, data);
        } else {
            processFileUpload(item, path, data);
        }
    }
    
    return data;
}private void processFormField(FileItem item, ProductData data) {
    String fieldName = item.getFieldName();
    String fieldValue = item.getString();

    try {
        switch (fieldName) {
            case "pname":
                data.setPname(fieldValue);
                break;

            case "pprice":
                int price = Integer.parseInt(fieldValue);
                data.setPprice(price); // não seta erro aqui
                break;

            case "pquantity":
                int quantity = Integer.parseInt(fieldValue);
                data.setPquantity(quantity); // não seta erro aqui
                break;

            case "bname":
                data.setBid(getBrandId(fieldValue));
                break;

            case "cname":
                data.setCid(getCategoryId(fieldValue));
                break;

            default:
                if (logger.isLoggable(Level.FINE)) {
                    logger.fine("Unknown form field: " + fieldName + " with value: " + fieldValue);
                }
                break;
        }
    } catch (NumberFormatException e) {
        // Se der erro na conversão, seta zero para invalidar o dado
        switch (fieldName) {
            case "pprice":
                data.setPprice(0);
                break;
            case "pquantity":
                data.setPquantity(0);
                break;
            default:
                // Ignore other fields that don't need number conversion
                break;
        }
    }
}



private void processFileUpload(FileItem item, String path, ProductData data) {
    ArrayList<String> ext = new ArrayList<>();
    ext.add(".jpg"); 
    ext.add(".bmp"); 
    ext.add(".jpeg"); 
    ext.add(".png"); 
    ext.add(".webp");
    
    try {
        String uploadResult = myUtilities.UploadFile(item, path, ext);
        data.setPimage(uploadResult);
    } catch (Exception e) {
        logger.log(Level.SEVERE, "Error uploading file", e);
        data.setPimage("Problem with upload");
    }
}

private int getBrandId(String brandName) {
    switch (brandName.toLowerCase()) {
        case "samsung": return 1;
        case "sony": return 2;
        case "lenovo": return 3;
        case "acer": return 4;
        case "onida": return 5;
        default: return 0;
    }
}

private int getCategoryId(String categoryName) {
    switch (categoryName.toLowerCase()) {
        case "laptop": return 1;
        case "tv": return 2;
        case "mobile": return 3;
        case "watch": return 4;
        default: return 0;
    }
}

private int insertProduct(ProductData data) {
    String sql = "insert into product(pname,pprice,pquantity,pimage,bid,cid) values(?,?,?,?,?,?)";
    try (PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, data.getPname());
        ps.setInt(2, data.getPprice());
        ps.setInt(3, data.getPquantity());
        ps.setString(4, data.getPimage());
        ps.setInt(5, data.getBid());
        ps.setInt(6, data.getCid());
        ps.executeUpdate();
        return 1;
    } catch (Exception e) {
        logger.log(Level.SEVERE, "Error inserting product", e);
        return 0;
    }
}

private void logProductDetails(ProductData data) {
    if (logger.isLoggable(Level.INFO)) {
        logger.info(String.format("Product details - pname: %s, pprice: %d, pquantity: %d, pimage: %s, bid: %d, cid: %d", 
                   data.getPname(), data.getPprice(), data.getPquantity(), data.getPimage(), data.getBid(), data.getCid()));
    }
}

// Classe interna para encapsular dados do produto
private static class ProductData {
    private String pname = "";
    private int pprice = 0;
    private int pquantity = 0;
    private String pimage = "";
    private int bid = 0;
    private int cid = 0;

    // Erro conhecido de upload
    public boolean hasUploadError() {
        return "Problem with upload".equals(pimage);
    }

    // Validações adicionais de limites
    public boolean hasInvalidPrice() {
        return pprice < 1;
    }

    public boolean hasInvalidQuantity() {
        return pquantity < 1;
    }

    public boolean hasInvalidName() {
        return pname == null || pname.trim().isEmpty();
    }

    // Método que indica se os dados são válidos
    public boolean isValid() {
        return !hasUploadError() && !hasInvalidPrice() && !hasInvalidQuantity() && !hasInvalidName();
    }

    // Getters e Setters
    public String getPname() { return pname; }
    public void setPname(String pname) { this.pname = pname; }

    public int getPprice() { return pprice; }
    public void setPprice(int pprice) { this.pprice = pprice; }

    public int getPquantity() { return pquantity; }
    public void setPquantity(int pquantity) { this.pquantity = pquantity; }

    public String getPimage() { return pimage; }
    public void setPimage(String pimage) { this.pimage = pimage; }

    public int getBid() { return bid; }
    public void setBid(int bid) { this.bid = bid; }

    public int getCid() { return cid; }
    public void setCid(int cid) { this.cid = cid; }
}
//display all customers

public List<customer> getAllCustomer()
{
	String sql = "select Name, Password, Email_Id, Contact_No from customer";
	return executeQuery(sql, EntityMappers::mapToCustomer);
}


//Delete Customer

	public boolean deleteCustomer(customer c)
	{
		String sql = "delete from customer where Name = ? and Email_Id = ?";
		Object[] parameters = {c.getName(), c.getEmail_Id()};
		int result = executeUpdate(sql, parameters);
		return result == 1;
	}


	// display selected customer

public List<customer> getCustomer(String eid)
{
	String sql = "select Name, Password, Email_Id, Contact_No from customer where Email_Id=?";
	Object[] parameters = {eid};
	return executeQuery(sql, parameters, EntityMappers::mapToCustomer);
}

}
