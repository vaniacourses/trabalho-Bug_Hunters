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

	// Add a new static inner class to group validation context
	public static class CustomerValidationContext {
		private final String userType;
		private final String age;
		private final String country;
		private final String city;
		private final String zipCode;
		public String getUserType() { return userType; }
		public String getAge() { return age; }
		public String getCountry() { return country; }
		public String getCity() { return city; }
		public String getZipCode() { return zipCode; }
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
	private static final String FAIL_JSP = "fail.jsp";
	private static final String VALID = "VALID";

	public String validateCustomerData(customer customerObj, CustomerValidationContext ctx) {
		String validationResult = validateName(customerObj, ctx);
		if (VALID.equals(validationResult)) {
			validationResult = validateAge(ctx.getAge(), ctx.getUserType(), ctx.getCountry());
		}
		return validationResult;
	}

	private String validateName(customer customerObj, CustomerValidationContext ctx) {
		String name = customerObj.getName();
		if (name == null || name.trim().isEmpty()) {
			return validateNameEmpty(ctx);
		}
		if (name.length() < 3) {
			return PREMIUM.equals(ctx.getUserType()) ? "NAME_TOO_SHORT_PREMIUM" : "NAME_TOO_SHORT_STANDARD";
		}
		if (name.length() > 50) {
			return PREMIUM.equals(ctx.getUserType()) ? "NAME_TOO_LONG_PREMIUM" : "NAME_TOO_LONG_STANDARD";
		}
		return VALID;
	}

	private String validateNameEmpty(CustomerValidationContext ctx) {
		if (PREMIUM.equals(ctx.getUserType())) {
			return validateNameEmptyPremium(ctx);
		}
		if ("standard".equals(ctx.getUserType())) {
			return validateNameEmptyStandard(ctx);
		}
		return "NAME_REQUIRED_UNKNOWN_TYPE";
	}

	private String validateNameEmptyPremium(CustomerValidationContext ctx) {
		if (BRAZIL.equals(ctx.getCountry())) {
			if ("Rio de Janeiro".equals(ctx.getCity())) return "NAME_REQUIRED_PREMIUM_BRAZIL_RJ";
			if ("São Paulo".equals(ctx.getCity())) return "NAME_REQUIRED_PREMIUM_BRAZIL_SP";
			return "NAME_REQUIRED_PREMIUM_BRAZIL_OTHER";
		}
		if ("USA".equals(ctx.getCountry())) {
			if (ctx.getZipCode().length() == 5) return "NAME_REQUIRED_PREMIUM_USA_5DIGIT";
			if (ctx.getZipCode().length() == 9) return "NAME_REQUIRED_PREMIUM_USA_9DIGIT";
			return "NAME_REQUIRED_PREMIUM_USA_INVALID";
		}
		return "NAME_REQUIRED_PREMIUM_OTHER";
	}

	private String validateNameEmptyStandard(CustomerValidationContext ctx) {
		int ageInt = Integer.parseInt(ctx.getAge());
		if (ageInt < 18) {
			return "NAME_REQUIRED_STANDARD_MINOR_BRAZIL".equals(ctx.getCountry()) ? "NAME_REQUIRED_STANDARD_MINOR_BRAZIL" : "NAME_REQUIRED_STANDARD_MINOR_OTHER";
		}
		if (ageInt > 65) {
			return BRAZIL.equals(ctx.getCountry()) ? "NAME_REQUIRED_STANDARD_SENIOR_BRAZIL" : "NAME_REQUIRED_STANDARD_SENIOR_OTHER";
		}
		return "NAME_REQUIRED_STANDARD_ADULT";
	}

	private String validateAge(String age, String userType, String country) {
		if (age == null || age.trim().isEmpty()) {
			return PREMIUM.equals(userType) ? "AGE_REQUIRED_PREMIUM" : "AGE_REQUIRED_STANDARD";
		}
		int ageValue;
		try {
			ageValue = Integer.parseInt(age);
		} catch (NumberFormatException e) {
			return PREMIUM.equals(userType) ? "AGE_INVALID_FORMAT_PREMIUM" : "AGE_INVALID_FORMAT_STANDARD";
		}
		if (ageValue < 13) {
			return getAgeTooYoungMessage(userType);
		}
		if (ageValue > 120) {
			return getAgeTooOldMessage(userType);
		}
		if (ageValue < 18) {
			return getAgeMinorMessage(userType, country);
		}
		return VALID;
	}

	private String getAgeTooYoungMessage(String userType) {
		return PREMIUM.equals(userType) ? "AGE_TOO_YOUNG_PREMIUM" : "AGE_TOO_YOUNG_STANDARD";
	}

	private String getAgeTooOldMessage(String userType) {
		return PREMIUM.equals(userType) ? "AGE_TOO_OLD_PREMIUM" : "AGE_TOO_OLD_STANDARD";
	}

	private String getAgeMinorMessage(String userType, String country) {
		if (PREMIUM.equals(userType)) {
			return BRAZIL.equals(country) ? "AGE_MINOR_PREMIUM_BRAZIL" : "AGE_MINOR_PREMIUM_OTHER";
		}
		return BRAZIL.equals(country) ? "AGE_MINOR_STANDARD_BRAZIL" : "AGE_MINOR_STANDARD_OTHER";
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
		try {
			customerObj.setContact_No(Integer.parseInt(contactNo));
		} catch (NumberFormatException nfe) {
			LOGGER.severe("Invalid contact number format: " + contactNo);
			response.sendRedirect(FAIL_JSP);
			return;
		}

		try {
			DAO2 dao = new DAO2(DBConnect.getConn());
			if (dao.checkcust2(customerObj)) {
				response.sendRedirect(FAIL_JSP);
			} else {
				if (dao.addcustomer(customerObj) > 0) {
					Cookie creg = new Cookie("creg", "creg");
					creg.setMaxAge(10);
					response.addCookie(creg);
					response.sendRedirect("customerlogin.jsp?Total=" + total + "&CusName=" + customerName);
				} else {
					response.sendRedirect(FAIL_JSP);
				}
			}
		} catch (Exception ex) {
			LOGGER.severe("Exception in addcustomer#doPost: " + ex.getMessage());
		}
	}
}