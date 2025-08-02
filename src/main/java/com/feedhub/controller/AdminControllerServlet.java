package com.feedhub.controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/admin/*")
public class AdminControllerServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String path = request.getPathInfo();

		switch (path) {
		case "/getStudents":
			getStudents(request, response);
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
		
		
		
		res.getWriter().println("Get Student called");
	}

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

		switch (path) {
		case "/addStudent":
			addStudent(request, response);
			break;
		case "/addTeacher":
			addTeacher(request, response);
			break;
		case "/addSubject":
			addSubject(request, response);
			break;
		default:
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "Unknown POST path: " + path);
		}
	}

	private void addStudent(HttpServletRequest req, HttpServletResponse res) throws IOException {
		res.getWriter().println("Student added");
	}

	private void addTeacher(HttpServletRequest req, HttpServletResponse res) throws IOException {
		res.getWriter().println("Teacher added");
	}

	private void addSubject(HttpServletRequest req, HttpServletResponse res) throws IOException {
		res.getWriter().println("Subject added");
	}
}
