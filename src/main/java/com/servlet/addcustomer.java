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