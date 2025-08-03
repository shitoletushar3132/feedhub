<%@page import="com.feedhub.model.Subject"%>
<%@page import="com.feedhub.model.Teacher"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="java.util.*, com.feedhub.model.Student"%>
<%@ include file="header.jsp"%>

<html>
<head>
<title>Student List</title>
<link rel="stylesheet" type="text/css"
	href="/feedhub/assets/css/style.css">
<script src="/feedhub/assets/js/script.js"></script>

</head>
<body>

	<div style="max-width: 800px; margin: auto;">
		<h2>Teacher List</h2>

		<!-- Add Button -->
		<button onclick="openTeacherModal()" class="btn">Add Teacher</button>

		<%
		String error = request.getParameter("error");
		%>
		<%
		if ("duplicate".equals(error)) {
		%>
		<p id="error-message" class="error-msg">User with this email or
			username already exists.</p>
		<%
		}
		%>

		<!-- Teacher Modal -->
		<div id="formTeacherModal" class="modal" style="display: none;">
			<div class="modal-content">
				<button onclick="toggle('formTeacherModal')" class="btn btn-danger"
					style="float: right;">X</button>
				<h3 id="formTitle">Add Teacher</h3>
				<form id="teacherForm" action="teacherHandler/add" method="post">
					<input type="text" name="name" placeholder="Enter name"
						class="input-box" required> <input type="email"
						name="email" placeholder="Enter email" class="input-box" required>
					<input type="text" name="username" placeholder="Enter username"
						class="input-box" required> <input type="password"
						name="password" placeholder="Enter password" class="input-box"
						required> <input type="submit" value="Submit" class="btn"
						style="width: 100%;">
				</form>
			</div>
		</div>



		<br>

		<%
		List<Teacher> teachers = (List<Teacher>) request.getAttribute("teachers");
		if (teachers != null && !teachers.isEmpty()) {
		%>
		<table border="1">
			<tr>
				<th>Id</th>
				<th>Name</th>
				<th>Email</th>

				<th>Delete</th>

			</tr>
			<%
			for (Teacher t : teachers) {
			%>
			<tr>
				<td><%=t.getId()%></td>
				<td><%=t.getName()%></td>
				<td><%=t.getUser().getEmail()%></td>

				<td>
					<button class="btn btn-danger"
						onclick="deleteTeacher(<%=t.getId()%>)">Delete</button>
				</td>

			</tr>
			<%
			}
			%>
		</table>
		<%
		} else {
		%>
		<p>No Teacher found.</p>
		<%
		}
		%>
	</div>

	<div>
		<h3>Assign Subject to Teacher</h3>
		<form action="assignSubject" method="post">
			<label for="teacherId">Select Teacher:</label> <select
				name="teacherId" required>
				<%
				List<Teacher> teachers2 = (List<Teacher>) request.getAttribute("teachers");
				for (Teacher t : teachers2) {
				%>
				<option value="<%=t.getId()%>"><%=t.getName()%></option>
				<%
				}
				%>
			</select> <label for="subjectId">Select Subject:</label> <select
				name="subjectId" required>
				<%
				List<Subject> subjects = (List<Subject>) request.getAttribute("subjects");
				for (Subject s : subjects) {
				%>
				<option value="<%=s.getId()%>"><%=s.getName()%></option>
				<%
				}
				%>
			</select>

			<button type="submit">Assign</button>
		</form>
	</div>
</body>
</html>
