package com.feedhub.controller.methods.admin;

import java.io.IOException;
import java.util.List;

import com.feedhub.dao.daoInterfaces.RoleDAO;
import com.feedhub.dao.daoInterfaces.StudentDAO;
import com.feedhub.dao.daoInterfaces.UserDAO;
import com.feedhub.model.Role;
import com.feedhub.model.Student;
import com.feedhub.model.User;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class StudentMethods {

	private StudentDAO studentDAO;
	private UserDAO userDAO;
	private RoleDAO roleDAO;

	public StudentMethods(StudentDAO studentDAO, UserDAO userDAO, RoleDAO roleDAO) {
		this.studentDAO = studentDAO;
		this.roleDAO = roleDAO;
		this.userDAO = userDAO;
	}

	public void getStudents(HttpServletRequest req, HttpServletResponse res) throws IOException {

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

	public void deleteStudent(HttpServletRequest req, HttpServletResponse res) throws IOException {
		int id = Integer.parseInt(req.getParameter("id"));
		boolean deleted = studentDAO.deleteStudent(id);

		if (deleted) {
			res.getWriter().write("Student deleted successfully.");
		} else {
			res.getWriter().write("Failed to delete student.");
		}
	};

	public void handleStudentForm(HttpServletRequest req, HttpServletResponse res) throws IOException {
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

}
