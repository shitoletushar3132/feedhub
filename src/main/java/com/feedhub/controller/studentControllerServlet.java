package com.feedhub.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

import com.feedhub.dao.daoInterfaces.FeedbackDAO;
import com.feedhub.dao.daoInterfaces.StudentDAO;
import com.feedhub.dao.daoInterfaces.SubjectDAO;
import com.feedhub.dao.daoInterfaces.TeacherDAO;
import com.feedhub.dao.daoInterfaces.Teacher_SubjectDAO;
import com.feedhub.dao.daoimpl.FeedbackDAOImpl;
import com.feedhub.dao.daoimpl.StudentDAOImpl;
import com.feedhub.dao.daoimpl.SubjectDAOImpl;
import com.feedhub.dao.daoimpl.TeacherDAOImpl;
import com.feedhub.dao.daoimpl.Teacher_SubjectDAOImpl;
import com.feedhub.model.Feedback;
import com.feedhub.model.Student;
import com.feedhub.model.Subject;
import com.feedhub.model.Teacher;
import com.feedhub.model.TeacherSubjectDTO;
import com.feedhub.util.DBConnection;

@WebServlet("/student/*")
public class studentControllerServlet extends HttpServlet {

	Connection conn = null;
	TeacherDAO teacherDAO;
	StudentDAO studentDAO;
	SubjectDAO subjectDAO;
	FeedbackDAO feedDAO;
	private Teacher_SubjectDAO teacherSubjectDAO;

	public void init() throws ServletException {
		try {
			conn = DBConnection.getConnection();

			teacherSubjectDAO = new Teacher_SubjectDAOImpl(conn);
			subjectDAO = new SubjectDAOImpl(conn);
			teacherDAO = new TeacherDAOImpl(conn);
			studentDAO = new StudentDAOImpl(conn);
			feedDAO = new FeedbackDAOImpl(conn);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String path = request.getPathInfo();
		switch (path) {
		case "/myFeedbacks":
			request.getRequestDispatcher("/studentOwnReview.jsp").forward(request, response);
		case "/giveFeedback":
			List<TeacherSubjectDTO> mappings = teacherSubjectDAO.getAllTeacherSubjectMappings();
			request.setAttribute("teacherSubjects", mappings);
			request.getRequestDispatcher("/studentReview.jsp").forward(request, response);
			break;
		default:
			response.sendRedirect(request.getContextPath() + "/student-dashboard.jsp");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String teacherId = request.getParameter("teacherId");
			String subjectId = request.getParameter("subjectId");
			String studentId = request.getParameter("studentId");
			String ratingStr = request.getParameter("rating");
			String comment = request.getParameter("comment");

			int rating = Integer.parseInt(ratingStr);


			Teacher teacher = teacherDAO.getTeacherById(Integer.parseInt(teacherId));
			Subject subject = subjectDAO.getSubjectById(Integer.parseInt(subjectId));
			Student student = studentDAO.getStudentById(Integer.parseInt(studentId));
			Timestamp now = Timestamp.from(Instant.now());

			System.out.print("from" + teacher.getId() + " " + "id: " + teacherId);

			Feedback feedBack = new Feedback(0, student, teacher, subject, comment, rating, now);

			boolean res = feedDAO.addFeedback(feedBack);
			if (res) {
				response.sendRedirect(request.getContextPath() + "/student/giveFeedback?error=false");
			} else {
				response.sendRedirect(request.getContextPath() + "/student/giveFeedback?error=true");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
