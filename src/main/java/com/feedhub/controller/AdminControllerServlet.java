package com.feedhub.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.feedhub.dao.daoInterfaces.RoleDAO;
import com.feedhub.dao.daoInterfaces.StudentDAO;
import com.feedhub.dao.daoInterfaces.UserDAO;
import com.feedhub.dao.daoimpl.RoleDAOImpl;
import com.feedhub.dao.daoimpl.StudentDAOImpl;
import com.feedhub.dao.daoimpl.UserDAOImpl;
import com.feedhub.model.Role;
import com.feedhub.model.Student;
import com.feedhub.model.User;
import com.feedhub.util.DBConnection;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/admin/*")
public class AdminControllerServlet extends HttpServlet {

	private UserDAO userDAO;
	private StudentDAO studentDAO;
	private RoleDAO roleDAO;

	@Override
	public void init() throws ServletException {
		Connection conn = null;
		try {
			conn = DBConnection.getConnection();
			userDAO = new UserDAOImpl(conn);
			studentDAO = new StudentDAOImpl(conn);
			roleDAO = new RoleDAOImpl(conn);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String path = request.getPathInfo();

		switch (path) {
		case "/getStudents":
			getStudents(request, response);
			break;
		case "/deleteStudent":
			deleteStudent(request, response);
			break;
		case "/getTeachers":
			getTeachers(request, response);
			break;
		case "/getSubjects":
			getSubjects(request, response);
			break;
		default:
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "Unknown GET path: " + path);
		}
	}

	private void getStudents(HttpServletRequest req, HttpServletResponse res) throws IOException {

		try {
			List<Student> studentList = studentDAO.getAllStudents();
			req.setAttribute("students", studentList); // Set data in request scope

			// Forward to JSP (inside WEB-INF to restrict direct access)
			req.getRequestDispatcher("/studentsManage.jsp").forward(req, res);
		} catch (Exception e) {
			e.printStackTrace();
			res.getWriter().println("Error retrieving students.");
		}
	}

	private void deleteStudent(HttpServletRequest req, HttpServletResponse res) throws IOException {
		int id = Integer.parseInt(req.getParameter("id"));
		boolean deleted = studentDAO.deleteStudent(id);

		if (deleted) {
			res.getWriter().write("Student deleted successfully.");
		} else {
			res.getWriter().write("Failed to delete student.");
		}
	};

	private void getTeachers(HttpServletRequest req, HttpServletResponse res) throws IOException {
		res.getWriter().println("Get Student called");
	}

	private void getSubjects(HttpServletRequest req, HttpServletResponse res) throws IOException {
		res.getWriter().println("Get Student called");
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String path = request.getPathInfo(); // Gets the part after /admin/

		if (path != null && path.startsWith("/studentHandler/")) {
			handleStudentForm(request, response);
		} else if ("/addTeacher".equals(path)) {
			addTeacher(request, response);
		} else if ("/addSubject".equals(path)) {
			addSubject(request, response);
		} else {
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "Unknown POST path: " + path);
		}
	}

	private void addStudent(HttpServletRequest req, HttpServletResponse res) throws IOException {
		String username = req.getParameter("username");
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		String studentName = req.getParameter("name");
		String rollNo = req.getParameter("rollNo");

		try {
			// Step 1: Add user
			User user = new User();
			Role role = roleDAO.getRoleByName("student");
			user.setUsername(username);
			user.setEmail(email);
			user.setPassword(password);
			user.setRole(role);

			int addedUserId = userDAO.addUserAndReturnId(user);

			if (addedUserId == -1) {
				res.sendRedirect(req.getContextPath() + "/admin/getStudents?error=duplicate");
				return;
			}

			user.setId(addedUserId);

			// Step 3: Add student
			Student student = new Student();
			student.setName(studentName);
			student.setRollNo(rollNo);
			student.setUser(user);

			boolean studentAdded = studentDAO.addStudent(student);

			if (studentAdded) {
				res.sendRedirect(req.getContextPath() + "/admin/getStudents");
			} else {
				res.getWriter().println("Failed to add student.");
			}

		} catch (Exception e) {
			e.printStackTrace();
			res.getWriter().println("Server error: " + e.getMessage());
		}
	}

	private void handleStudentForm(HttpServletRequest req, HttpServletResponse res) throws IOException {
		String action = req.getPathInfo(); // "add" or "edit"
		String idStr = req.getParameter("id");
		String username = req.getParameter("username");
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		String studentName = req.getParameter("name");
		String rollNo = req.getParameter("rollNo");

		try {
			if ("/studentHandler/add".equalsIgnoreCase(action)) {
				// Add student (same as your current addStudent() logic)
				Role role = roleDAO.getRoleByName("student");

				User user = new User();
				user.setUsername(username);
				user.setEmail(email);
				user.setPassword(password);
				user.setRole(role);

				int userId = userDAO.addUserAndReturnId(user);
				if (userId == -1) {
					res.sendRedirect(req.getContextPath() + "/admin/getStudents?error=duplicate");
					return;
				}

				user.setId(userId);
				Student student = new Student();
				student.setName(studentName);
				student.setRollNo(rollNo);
				student.setUser(user);

				studentDAO.addStudent(student);
				res.sendRedirect(req.getContextPath() + "/admin/getStudents");

			} else if ("/studentHandler/edit".equalsIgnoreCase(action)) {
				// Edit student
				int studentId = Integer.parseInt(idStr);

				Student student = studentDAO.getStudentById(studentId);
				if (student == null) {
					res.sendRedirect(req.getContextPath() + "/admin/getStudents?error=notfound");
					return;
				}

				// Update user
				User user = student.getUser();
				user.setUsername(username);
				user.setEmail(email);
				if (password != null && !password.isBlank()) {
					user.setPassword(password); // Optional: check if password should be changed
				}
				userDAO.updateUser(user);

				// Update student
				student.setName(studentName);
				student.setRollNo(rollNo);
				studentDAO.updateStudent(student);

				res.sendRedirect(req.getContextPath() + "/admin/getStudents");

			} else {
				res.getWriter().println("Invalid form action.");
			}

		} catch (Exception e) {
			e.printStackTrace();
			res.getWriter().println("Server error: " + e.getMessage());
		}
	}

	private void addTeacher(HttpServletRequest req, HttpServletResponse res) throws IOException {
		res.getWriter().println("Teacher added");
	}

	private void addSubject(HttpServletRequest req, HttpServletResponse res) throws IOException {
		res.getWriter().println("Subject added");
	}
}
