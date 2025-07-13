package com.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.conn.DBConnect;
import com.dao.DAO2;
import com.entity.customer;
import java.util.logging.Logger;

@MultipartConfig
@WebServlet("/addcustomer")
public class addcustomer extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// Novo: DAO2 injetável
	private DAO2 dao2;

	// Construtor padrão: uso normal
	public addcustomer() {
		super();
		this.dao2 = null;
	}

	// Novo: construtor para injeção (usado em testes)
	public addcustomer(DAO2 dao2) {
		super();
		this.dao2 = dao2;
	}

	// Novo: setter para injeção (opcional, útil em frameworks ou testes)
	public void setDao2(DAO2 dao2) {
		this.dao2 = dao2;
	}

	// Add a new static inner class to group validation context
	public static class CustomerValidationContext {
		public String userType;
		public String age;
		public String country;
		public String city;
		public String zipCode;
		public CustomerValidationContext(String userType, String age, String country, String city, String zipCode) {
			this.userType = userType;
			this.age = age;
			this.country = country;
			this.city = city;
			this.zipCode = zipCode;
		}
	}

	private static final String BRAZIL = "Brazil";
	private static final String PREMIUM = "premium";

	public String validateCustomerData(customer customerObj, CustomerValidationContext ctx) {
		String validationResult = validateName(customerObj, ctx);
		if ("VALID".equals(validationResult)) {
			validationResult = validateAge(ctx.age, ctx.userType, ctx.country);
		}
		return validationResult;
	}

	private String validateName(customer customerObj, CustomerValidationContext ctx) {
		String name = customerObj.getName();
		if (name == null || name.trim().isEmpty()) {
			if (PREMIUM.equals(ctx.userType)) {
				if (BRAZIL.equals(ctx.country)) {
					if ("Rio de Janeiro".equals(ctx.city)) return "NAME_REQUIRED_PREMIUM_BRAZIL_RJ";
					if ("São Paulo".equals(ctx.city)) return "NAME_REQUIRED_PREMIUM_BRAZIL_SP";
					return "NAME_REQUIRED_PREMIUM_BRAZIL_OTHER";
				}
				if ("USA".equals(ctx.country)) {
					if (ctx.zipCode.length() == 5) return "NAME_REQUIRED_PREMIUM_USA_5DIGIT";
					if (ctx.zipCode.length() == 9) return "NAME_REQUIRED_PREMIUM_USA_9DIGIT";
					return "NAME_REQUIRED_PREMIUM_USA_INVALID";
				}
				return "NAME_REQUIRED_PREMIUM_OTHER";
			}
			if ("standard".equals(ctx.userType)) {
				int ageInt = Integer.parseInt(ctx.age);
				if (ageInt < 18) return "NAME_REQUIRED_STANDARD_MINOR_BRAZIL".equals(ctx.country) ? "NAME_REQUIRED_STANDARD_MINOR_BRAZIL" : "NAME_REQUIRED_STANDARD_MINOR_OTHER";
				if (ageInt > 65) return BRAZIL.equals(ctx.country) ? "NAME_REQUIRED_STANDARD_SENIOR_BRAZIL" : "NAME_REQUIRED_STANDARD_SENIOR_OTHER";
				return "NAME_REQUIRED_STANDARD_ADULT";
			}
			return "NAME_REQUIRED_UNKNOWN_TYPE";
		}
		if (name.length() < 3) return PREMIUM.equals(ctx.userType) ? "NAME_TOO_SHORT_PREMIUM" : "NAME_TOO_SHORT_STANDARD";
		if (name.length() > 50) return PREMIUM.equals(ctx.userType) ? "NAME_TOO_LONG_PREMIUM" : "NAME_TOO_LONG_STANDARD";
		return "VALID";
	}

	private String validateAge(String age, String userType, String country) {
		if (age == null || age.trim().isEmpty()) return PREMIUM.equals(userType) ? "AGE_REQUIRED_PREMIUM" : "AGE_REQUIRED_STANDARD";
		try {
			int ageValue = Integer.parseInt(age);
			if (ageValue < 13) return PREMIUM.equals(userType) ? "AGE_TOO_YOUNG_PREMIUM" : "AGE_TOO_YOUNG_STANDARD";
			if (ageValue > 120) return PREMIUM.equals(userType) ? "AGE_TOO_OLD_PREMIUM" : "AGE_TOO_OLD_STANDARD";
			if (ageValue < 18) {
				if (PREMIUM.equals(userType)) return BRAZIL.equals(country) ? "AGE_MINOR_PREMIUM_BRAZIL" : "AGE_MINOR_PREMIUM_OTHER";
				return BRAZIL.equals(country) ? "AGE_MINOR_STANDARD_BRAZIL" : "AGE_MINOR_STANDARD_OTHER";
			}
		} catch (NumberFormatException e) {
			return PREMIUM.equals(userType) ? "AGE_INVALID_FORMAT_PREMIUM" : "AGE_INVALID_FORMAT_STANDARD";
		}
		return "VALID";
	}

	private static final Logger LOGGER = Logger.getLogger(addcustomer.class.getName());

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// This servlet does not handle GET requests. Method intentionally left empty.
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String usernameRaw = request.getParameter("Username");
		String username = usernameRaw.trim();

		String passwordRaw = request.getParameter("Password");
		String password = passwordRaw.trim();

		String emailIdRaw = request.getParameter("Email_Id");
		String emailId = emailIdRaw.trim();

		String contactNoRaw = request.getParameter("Contact_No");
		String contactNo = contactNoRaw.trim();

		String total = request.getParameter("Total");
		String customerName = request.getParameter("CusName");

		customer customerObj = new customer();
		customerObj.setName(username);
		customerObj.setPassword(password);
		customerObj.setEmail_Id(emailId);
		customerObj.setContact_No(Integer.parseInt(contactNo));

		try {
			// Use o DAO2 injetado se existir, senão crie um novo
			DAO2 dao = (this.dao2 != null) ? this.dao2 : new DAO2(DBConnect.getConn());

			if (dao.checkcust2(customerObj)) {
				response.sendRedirect("fail.jsp");
			} else {
				if (dao.addcustomer(customerObj) > 0) {
					Cookie creg = new Cookie("creg", "creg");
					creg.setMaxAge(10);
					response.addCookie(creg);
					response.sendRedirect("customerlogin.jsp?Total=" + total + "&CusName=" + customerName);
				} else {
					response.sendRedirect("fail.jsp");
				}
			}
		} catch (Exception ex) {
			LOGGER.severe("Exception in addcustomer#doPost: " + ex.getMessage());
		}
	}
}