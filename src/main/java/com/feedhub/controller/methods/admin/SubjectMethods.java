package com.feedhub.controller.methods.admin;

import java.io.IOException;
import java.util.List;

import com.feedhub.dao.daoInterfaces.RoleDAO;
import com.feedhub.dao.daoInterfaces.SubjectDAO;
import com.feedhub.dao.daoInterfaces.UserDAO;
import com.feedhub.model.Subject;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class SubjectMethods {
	private SubjectDAO subjectDAO;
	private UserDAO userDAO;
	private RoleDAO roleDAO;

	public SubjectMethods(SubjectDAO subjectDAO, UserDAO userDAO, RoleDAO roleDAO) {
		this.subjectDAO = subjectDAO;
		this.roleDAO = roleDAO;
		this.userDAO = userDAO;
	}

	public void getSubjects(HttpServletRequest req, HttpServletResponse res) throws IOException {

		try {

			List<Subject> subjects = subjectDAO.getAllSubjects();

			req.setAttribute("subjects", subjects);

			req.getRequestDispatcher("/subjectManage.jsp").forward(req, res);
			return;

		} catch (Exception e) {
			e.printStackTrace();
		}

//		res.getWriter().println("Get Student called");
	}

	public void addSubject(HttpServletRequest req, HttpServletResponse res) throws IOException {
		String subjectName = req.getParameter("subjectName");

		try {

			Subject newSubject = new Subject();
			newSubject.setName(subjectName);

			boolean check = subjectDAO.addSubject(newSubject);

			if (check) {
				res.sendRedirect(req.getContextPath() + "/admin/getSubjects");
				return;

			}

		} catch (Exception e) {
		}
		res.getWriter().println("Subject added");
	}

	public void deleteSubject(HttpServletRequest req, HttpServletResponse res) throws IOException {

		int id = Integer.parseInt(req.getParameter("id"));

		try {
			subjectDAO.deleteSubject(id);
			res.getWriter().println("Subject Deleted Successfully");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
