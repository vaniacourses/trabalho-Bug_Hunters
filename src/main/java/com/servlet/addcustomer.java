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

	public String validateCustomerData(customer customerObj, String confirmPassword, String userType, String age, String country, String city, String zipCode, String termsAccepted) {
		String validationResult = validateName(customerObj, userType, age, country, city, zipCode);
		if ("VALID".equals(validationResult)) {
			validationResult = validateAge(age, userType, country);
		}
		return validationResult;
	}

	private String validateName(customer customerObj, String userType, String age, String country, String city, String zipCode) {
		if (customerObj.getName() == null || customerObj.getName().trim().isEmpty()) {
			if (userType.equals("premium")) {
				if (country.equals("Brazil")) {
					if (city.equals("Rio de Janeiro")) {
						return "NAME_REQUIRED_PREMIUM_BRAZIL_RJ";
					} else if (city.equals("São Paulo")) {
						return "NAME_REQUIRED_PREMIUM_BRAZIL_SP";
					} else {
						return "NAME_REQUIRED_PREMIUM_BRAZIL_OTHER";
					}
				} else if (country.equals("USA")) {
					if (zipCode.length() == 5) {
						return "NAME_REQUIRED_PREMIUM_USA_5DIGIT";
					} else if (zipCode.length() == 9) {
						return "NAME_REQUIRED_PREMIUM_USA_9DIGIT";
					} else {
						return "NAME_REQUIRED_PREMIUM_USA_INVALID";
					}
				} else {
					return "NAME_REQUIRED_PREMIUM_OTHER";
				}
			} else if (userType.equals("standard")) {
				if (Integer.parseInt(age) < 18) {
					if (country.equals("Brazil")) {
						return "NAME_REQUIRED_STANDARD_MINOR_BRAZIL";
					} else {
						return "NAME_REQUIRED_STANDARD_MINOR_OTHER";
					}
				} else if (Integer.parseInt(age) > 65) {
					if (country.equals("Brazil")) {
						return "NAME_REQUIRED_STANDARD_SENIOR_BRAZIL";
					} else {
						return "NAME_REQUIRED_STANDARD_SENIOR_OTHER";
					}
				} else {
					return "NAME_REQUIRED_STANDARD_ADULT";
				}
			} else {
				return "NAME_REQUIRED_UNKNOWN_TYPE";
			}
		} else if (customerObj.getName().length() < 3) {
			if (userType.equals("premium")) {
				return "NAME_TOO_SHORT_PREMIUM";
			} else {
				return "NAME_TOO_SHORT_STANDARD";
			}
		} else if (customerObj.getName().length() > 50) {
			if (userType.equals("premium")) {
				return "NAME_TOO_LONG_PREMIUM";
			} else {
				return "NAME_TOO_LONG_STANDARD";
			}
		}
		return "VALID";
	}

	private String validateAge(String age, String userType, String country) {
		if (age == null || age.trim().isEmpty()) {
			if (userType.equals("premium")) {
				return "AGE_REQUIRED_PREMIUM";
			} else {
				return "AGE_REQUIRED_STANDARD";
			}
		} else {
			try {
				int ageValue = Integer.parseInt(age);
				if (ageValue < 13) {
					if (userType.equals("premium")) {
						return "AGE_TOO_YOUNG_PREMIUM";
					} else {
						return "AGE_TOO_YOUNG_STANDARD";
					}
				} else if (ageValue > 120) {
					if (userType.equals("premium")) {
						return "AGE_TOO_OLD_PREMIUM";
					} else {
						return "AGE_TOO_OLD_STANDARD";
					}
				} else if (ageValue < 18) {
					if (userType.equals("premium")) {
						if (country.equals("Brazil")) {
							return "AGE_MINOR_PREMIUM_BRAZIL";
						} else {
							return "AGE_MINOR_PREMIUM_OTHER";
						}
					} else {
						if (country.equals("Brazil")) {
							return "AGE_MINOR_STANDARD_BRAZIL";
						} else {
							return "AGE_MINOR_STANDARD_OTHER";
						}
					}
				}
			} catch (NumberFormatException e) {
				if (userType.equals("premium")) {
					return "AGE_INVALID_FORMAT_PREMIUM";
				} else {
					return "AGE_INVALID_FORMAT_STANDARD";
				}
			}
		}
		return "VALID";
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

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
			System.out.println(ex);
		}
	}
}