package com.feedhub.controller.methods.admin;

import java.io.IOException;
import java.util.List;

import com.feedhub.dao.daoInterfaces.RoleDAO;
import com.feedhub.dao.daoInterfaces.TeacherDAO;
import com.feedhub.dao.daoInterfaces.UserDAO;
import com.feedhub.model.Role;
import com.feedhub.model.Teacher;
import com.feedhub.model.User;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class TeacherMethods {
	private TeacherDAO teacherDAO;
	private UserDAO userDAO;
	private RoleDAO roleDAO;

	public TeacherMethods(TeacherDAO teacherDAO, UserDAO userDAO, RoleDAO roleDAO) {
		this.teacherDAO = teacherDAO;
		this.roleDAO = roleDAO;
		this.userDAO = userDAO;
	}

	public void getTeachers(HttpServletRequest req, HttpServletResponse res) throws IOException {

		try {
			List<Teacher> teacherList = teacherDAO.getAllTeachers();

			req.setAttribute("teachers", teacherList);

			req.getRequestDispatcher("/teacherManage.jsp").forward(req, res);
		} catch (Exception e) {
			e.printStackTrace();
			res.getWriter().println("Error retrieving students.");
		}
	}

	public void deleteTeacher(HttpServletRequest req, HttpServletResponse res) throws IOException {
		int id = Integer.parseInt(req.getParameter("id"));
		boolean deleted = teacherDAO.deleteTeacher(id);

		if (deleted) {
			res.getWriter().write("Student deleted successfully.");
		} else {
			res.getWriter().write("Failed to delete student.");
		}
	}

	public void addTeacher(HttpServletRequest req, HttpServletResponse res) throws IOException {
		String username = req.getParameter("username");
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		String teacherName = req.getParameter("name");

		try {

			Role role = roleDAO.getRoleByName("teacher");

			User user = new User();
			user.setUsername(username);
			user.setEmail(email);
			user.setPassword(password);
			user.setRole(role);

			int userId = userDAO.addUserAndReturnId(user);
			if (userId == -1) {
				res.sendRedirect(req.getContextPath() + "/admin/getTeachers?error=duplicate");
				return;
			}

			user.setId(userId);
			Teacher teacher = new Teacher();

			teacher.setUser(user);
			teacher.setName(teacherName);

			teacherDAO.addTeacher(teacher);
			res.sendRedirect(req.getContextPath() + "/admin/getTeachers");
		} catch (Exception e) {
			e.printStackTrace();
		}

//		res.getWriter().println("Teacher added");
	}
}
