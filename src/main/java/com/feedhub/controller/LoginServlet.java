package com.feedhub.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import com.feedhub.dao.daoInterfaces.UserDAO;
import com.feedhub.dao.daoimpl.UserDAOImpl;
import com.feedhub.model.User;
import com.feedhub.util.DBConnection;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private UserDAO userDAO;

	@Override
	public void init() throws ServletException {
		Connection conn = null;
		try {
			conn = DBConnection.getConnection();
			userDAO = new UserDAOImpl(conn);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String email = request.getParameter("username");
		String password = request.getParameter("password");

		User user = userDAO.authenticate(email, password);

		System.out.println(user);
		if (user != null) {
			// Login successful
			HttpSession session = request.getSession();
			session.setAttribute("user", user);
			String role = user.getRole().getName();
			if (role.equals("admin")) {
				response.sendRedirect("admin-dashboard.jsp");
			} else if (role.equals("teacher")) {
				response.sendRedirect("teacher-dashboard.jsp");
			} else {

				response.sendRedirect("student-dashboard.jsp");
			}

		} else {
			// Login failed
			request.setAttribute("error", "Invalid email or password");
			request.getRequestDispatcher("login.jsp").forward(request, response);
		}
	}
}
