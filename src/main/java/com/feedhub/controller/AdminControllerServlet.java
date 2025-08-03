package com.feedhub.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import com.feedhub.controller.methods.admin.StudentMethods;
import com.feedhub.controller.methods.admin.SubjectMethods;
import com.feedhub.controller.methods.admin.TeacherMethods;
import com.feedhub.dao.daoInterfaces.RoleDAO;
import com.feedhub.dao.daoInterfaces.StudentDAO;
import com.feedhub.dao.daoInterfaces.SubjectDAO;
import com.feedhub.dao.daoInterfaces.TeacherDAO;
import com.feedhub.dao.daoInterfaces.Teacher_SubjectDAO;
import com.feedhub.dao.daoInterfaces.UserDAO;
import com.feedhub.dao.daoimpl.RoleDAOImpl;
import com.feedhub.dao.daoimpl.StudentDAOImpl;
import com.feedhub.dao.daoimpl.SubjectDAOImpl;
import com.feedhub.dao.daoimpl.TeacherDAOImpl;
import com.feedhub.dao.daoimpl.Teacher_SubjectDAOImpl;
import com.feedhub.dao.daoimpl.UserDAOImpl;
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
	private TeacherDAO teacherDAO;
	private SubjectDAO subjectDAO;
	private Teacher_SubjectDAO teacherSubjectDAO;
	private StudentMethods studentHandler;
	private TeacherMethods teacherHandler;
	private SubjectMethods subjectHandler;

	@Override
	public void init() throws ServletException {
		Connection conn = null;
		try {
			conn = DBConnection.getConnection();
			studentDAO = new StudentDAOImpl(conn);
			studentHandler = new StudentMethods(studentDAO, userDAO, roleDAO);

			userDAO = new UserDAOImpl(conn);

			roleDAO = new RoleDAOImpl(conn);

			teacherSubjectDAO = new Teacher_SubjectDAOImpl(conn);

			teacherDAO = new TeacherDAOImpl(conn);
			teacherHandler = new TeacherMethods(teacherDAO, userDAO, roleDAO);

			subjectDAO = new SubjectDAOImpl(conn);
			subjectHandler = new SubjectMethods(subjectDAO, userDAO, roleDAO);

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
			studentHandler.getStudents(request, response);
			break;
		case "/deleteStudent":
			studentHandler.deleteStudent(request, response);
			break;
		case "/deleteTeacher":
			teacherHandler.deleteTeacher(request, response);
			break;
		case "/getTeachers":
			teacherHandler.getTeachers(request, response);
			break;
		case "/getSubjects":
			subjectHandler.getSubjects(request, response);
			break;
		case "/deleteSubject":
			subjectHandler.deleteSubject(request, response);
			break;
		default:
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "Unknown GET path: " + path);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String path = request.getPathInfo(); // Gets the part after /admin/

		if (path != null && path.startsWith("/studentHandler/")) {
			studentHandler.handleStudentForm(request, response);
		} else if ("/addTeacher".equals(path)) {
			teacherHandler.addTeacher(request, response);
		} else if ("/addSubject".equals(path)) {
			subjectHandler.addSubject(request, response);
		} else if ("/assignSubject".equals(path)) {

		} else {
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "Unknown POST path: " + path);
		}
	}

	private void assignSubject(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		int teacherId = Integer.parseInt(req.getParameter("teacherId"));
		int subjectId = Integer.parseInt(req.getParameter("subjectId"));

		boolean success = teacherSubjectDAO.assignTeacherToSubject(teacherId, subjectId);

		if (success) {
			resp.sendRedirect("admin-dashboard.jsp?message=assigned");
		} else {
			resp.sendRedirect("admin-dashboard.jsp?error=assign_failed");
		}
	}

}
