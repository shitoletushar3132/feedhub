<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="java.util.*, com.feedhub.model.Student"%>
<%@ include file="header.jsp" %>

<html>
<head>
<title>Student List</title>
<link rel="stylesheet" type="text/css"
	href="/feedhub/assets/css/style.css">
<script src="/feedhub/assets/js/script.js"></script>

</head>
<body>

	<div style="max-width: 800px; margin: auto;">
		<h2>Student List</h2>

		<!-- Add Button -->
		<button onclick="openModal()" class="btn">Add Student</button>

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



		<div id="formModal" class="modal">
			<div class="modal-content">
				<button onclick="closeModal()" class="btn btn-danger"
					style="float: right;">X</button>
				<h3 id="formTitle">Add Student</h3>
				<form id="studentForm" action="" method="post">
					<input type="text" name="name" placeholder="Enter name"
						class="input-box" required> <input type="text"
						name="rollNo" placeholder="Enter roll number" class="input-box"
						required> <input type="email" name="email"
						placeholder="Enter email" class="input-box" required> <input
						type="text" name="username" placeholder="Enter username"
						class="input-box" required> <input type="password"
						name="password" placeholder="Enter password" class="input-box"
						required> <input type="submit" value="Submit" class="btn"
						style="width: 100%;">
				</form>
			</div>
		</div>


		<br>

		<%
		List<Student> students = (List<Student>) request.getAttribute("students");
		if (students != null && !students.isEmpty()) {
		%>
		<table border="1">
			<tr>
				<th>Roll No</th>
				<th>Name</th>
				<th>Email</th>
				<th>Edit</th>
				<th>Delete</th>

			</tr>
			<%
			for (Student s : students) {
			%>
			<tr>
				<td><%=s.getRollNo()%></td>
				<td><%=s.getName()%></td>
				<td><%=s.getUser().getEmail()%></td>
				<td>
					<button class="btn"
						onclick="openModal('Edit Student', '<%=s.getId()%>', '<%=s.getName()%>', '<%=s.getRollNo()%>', '<%=s.getUser().getEmail()%>', '<%=s.getUser().getUsername()%>')">Edit</button>

				</td>
				<td>
					<button class="btn btn-danger"
						onclick="deleteStudent(<%=s.getId()%>)">Delete</button>
				</td>

			</tr>
			<%
			}
			%>
		</table>
		<%
		} else {
		%>
		<p>No students found.</p>
		<%
		}
		%>
	</div>
</body>
</html>
