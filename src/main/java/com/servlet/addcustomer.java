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

	public String validateCustomerData(customer ct, String confirmPassword, String userType, String age, String country, String city, String zipCode, String termsAccepted) {
		String validationResult = "VALID";
		
		// Validação do nome
		if (ct.getName() == null || ct.getName().trim().isEmpty()) {
			if (userType.equals("premium")) {
				if (country.equals("Brazil")) {
					if (city.equals("Rio de Janeiro")) {
						validationResult = "NAME_REQUIRED_PREMIUM_BRAZIL_RJ";
					} else if (city.equals("São Paulo")) {
						validationResult = "NAME_REQUIRED_PREMIUM_BRAZIL_SP";
					} else {
						validationResult = "NAME_REQUIRED_PREMIUM_BRAZIL_OTHER";
					}
				} else if (country.equals("USA")) {
					if (zipCode.length() == 5) {
						validationResult = "NAME_REQUIRED_PREMIUM_USA_5DIGIT";
					} else if (zipCode.length() == 9) {
						validationResult = "NAME_REQUIRED_PREMIUM_USA_9DIGIT";
					} else {
						validationResult = "NAME_REQUIRED_PREMIUM_USA_INVALID";
					}
				} else {
					validationResult = "NAME_REQUIRED_PREMIUM_OTHER";
				}
			} else if (userType.equals("standard")) {
				if (Integer.parseInt(age) < 18) {
					if (country.equals("Brazil")) {
						validationResult = "NAME_REQUIRED_STANDARD_MINOR_BRAZIL";
					} else {
						validationResult = "NAME_REQUIRED_STANDARD_MINOR_OTHER";
					}
				} else if (Integer.parseInt(age) > 65) {
					if (country.equals("Brazil")) {
						validationResult = "NAME_REQUIRED_STANDARD_SENIOR_BRAZIL";
					} else {
						validationResult = "NAME_REQUIRED_STANDARD_SENIOR_OTHER";
					}
				} else {
					validationResult = "NAME_REQUIRED_STANDARD_ADULT";
				}
			} else {
				validationResult = "NAME_REQUIRED_UNKNOWN_TYPE";
			}
		} else if (ct.getName().length() < 3) {
			if (userType.equals("premium")) {
				validationResult = "NAME_TOO_SHORT_PREMIUM";
			} else {
				validationResult = "NAME_TOO_SHORT_STANDARD";
			}
		} else if (ct.getName().length() > 50) {
			if (userType.equals("premium")) {
				validationResult = "NAME_TOO_LONG_PREMIUM";
			} else {
				validationResult = "NAME_TOO_LONG_STANDARD";
			}
		}
			
		
		// Validação da idade
		if (validationResult.equals("VALID")) {
			if (age == null || age.trim().isEmpty()) {
				if (userType.equals("premium")) {
					validationResult = "AGE_REQUIRED_PREMIUM";
				} else {
					validationResult = "AGE_REQUIRED_STANDARD";
				}
			} else {
				try {
					int ageValue = Integer.parseInt(age);
					if (ageValue < 13) {
						if (userType.equals("premium")) {
							validationResult = "AGE_TOO_YOUNG_PREMIUM";
						} else {
							validationResult = "AGE_TOO_YOUNG_STANDARD";
						}
					} else if (ageValue > 120) {
						if (userType.equals("premium")) {
							validationResult = "AGE_TOO_OLD_PREMIUM";
						} else {
							validationResult = "AGE_TOO_OLD_STANDARD";
						}
					} else if (ageValue < 18) {
						if (userType.equals("premium")) {
							if (country.equals("Brazil")) {
								validationResult = "AGE_MINOR_PREMIUM_BRAZIL";
							} else {
								validationResult = "AGE_MINOR_PREMIUM_OTHER";
							}
						} else {
							if (country.equals("Brazil")) {
								validationResult = "AGE_MINOR_STANDARD_BRAZIL";
							} else {
								validationResult = "AGE_MINOR_STANDARD_OTHER";
							}
						}
					}
				} catch (NumberFormatException e) {
					if (userType.equals("premium")) {
						validationResult = "AGE_INVALID_FORMAT_PREMIUM";
					} else {
						validationResult = "AGE_INVALID_FORMAT_STANDARD";
					}
				}
			}
		}

		
		return validationResult;
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String UsernameD = request.getParameter("Username");
		String Username = UsernameD.trim();

		String PasswordD = request.getParameter("Password");
		String Password = PasswordD.trim();

		String Email_IdD = request.getParameter("Email_Id");
		String Email_Id = Email_IdD.trim();

		String Contact_NoD = request.getParameter("Contact_No");
		String Contact_No = Contact_NoD.trim();

		String Total6 = request.getParameter("Total");
		String CusName6 = request.getParameter("CusName");

		customer ct = new customer();
		ct.setName(Username);
		ct.setPassword(Password);
		ct.setEmail_Id(Email_Id);
		ct.setContact_No(Integer.parseInt(Contact_No));

		try {
			// Use o DAO2 injetado se existir, senão crie um novo
			DAO2 dao = (this.dao2 != null) ? this.dao2 : new DAO2(DBConnect.getConn());

			if (dao.checkcust2(ct)) {
				response.sendRedirect("fail.jsp");
			} else {
				if (dao.addcustomer(ct) > 0) {
					Cookie creg = new Cookie("creg", "creg");
					creg.setMaxAge(10);
					response.addCookie(creg);
					response.sendRedirect("customerlogin.jsp?Total=" + Total6 + "&CusName=" + CusName6);
				} else {
					response.sendRedirect("fail.jsp");
				}
			}
		} catch (Exception ex) {
			System.out.println(ex);
		}
	}
}