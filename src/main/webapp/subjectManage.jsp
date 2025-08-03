<%@page import="java.util.List"%>
<%@page import="com.feedhub.model.Subject"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="header.jsp"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css"
	href="/feedhub/assets/css/style.css">
<script src="/feedhub/assets/js/script.js"></script>
<title>Manage Subjects</title>
</head>
<body>
	<div style="max-width: 800px; margin: auto;">
		<h2>Subject List</h2>

		<!-- Add Subject Form -->
		<form action="addSubject" method="post" style="margin-bottom: 20px;">
			<input type="text" name="subjectName"
				placeholder="Enter Subject Name" required>
			<button type="submit" class="btn">Add Subject</button>
		</form>

		<!-- Error Message -->
		<%
		String error = request.getParameter("error");
		if ("duplicate".equals(error)) {
		%>
		<p id="error-message" class="error-msg">Subject with this name
			already exists.</p>
		<%
		}
		%>

		<!-- Subject Table -->
		<%
		List<Subject> subjects = (List<Subject>) request.getAttribute("subjects");
		if (subjects != null && !subjects.isEmpty()) {
		%>
		<table border="1" cellspacing="0" cellpadding="10"
			style="width: 100%;">
			<tr>
				<th>ID</th>
				<th>Subject Name</th>
				<th>Action</th>
			</tr>
			<%
			for (Subject s : subjects) {
			%>
			<tr>
				<td><%=s.getId()%></td>
				<td><%=s.getName()%></td>
				<td>
					<button class="btn btn-danger"
						onclick="deleteSubject(<%=s.getId()%>)">Delete</button>
				</td>
			</tr>
			<%
			}
			%>
		</table>
		<%
		} else {
		%>
		<p>No subjects found.</p>
		<%
		}
		%>
	</div>


</body>
</html>
