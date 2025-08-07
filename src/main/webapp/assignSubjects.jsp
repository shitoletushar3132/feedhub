<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="header.jsp"%>
<%@ page import="java.util.*, com.feedhub.model.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Assign Subject</title>
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css"
	href="/feedhub/assets/css/style.css">
<script src="/feedhub/assets/js/script.js"></script>
</head>
<body class="">
	<div class="container">
		<h2 class="mb-4">Assign a Subject to Teachers</h2>

		<!-- Assignment Form -->
		<div class="card mb-5">
			<div class="card-header">Assign Subject</div>
			<div class="card-body">

				<%
				String message = (String) request.getParameter("message");
				if ("duplicateError".equals(message)) {
				%>
				<p class="text-danger error-msg" id="error-message">You tried to assign the same subject to
					the same teacher again.</p>
				<%
				} else if ("assigned".equals(message)) {
				%>
				<p class="text-success" id="error-message">Subject successfully assigned to the
					teacher.</p>
				<%
				}
				%>


				<form action="assignSubject" method="post" class="row g-3">
					<div class="col-md-6">
						<label for="teacher" class="form-label">Select Teacher:</label> <select
							name="teacherId" id="teacher" class="form-select" required>
							<option value="">-- Select Teacher --</option>
							<%
							List<Teacher> teachers = (List<Teacher>) request.getAttribute("teachers");
							if (teachers != null) {
								for (Teacher teacher : teachers) {
							%>
							<option value="<%=teacher.getId()%>"><%=teacher.getName()%></option>
							<%
							}
							}
							%>
						</select>
					</div>

					<div class="col-md-6">
						<label for="subject" class="form-label">Select Subject:</label> <select
							name="subjectId" id="subject" class="form-select" required>
							<option value="">-- Select Subject --</option>
							<%
							List<Subject> subjects = (List<Subject>) request.getAttribute("subjects");
							if (subjects != null) {
								for (Subject subject : subjects) {
							%>
							<option value="<%=subject.getId()%>"><%=subject.getName()%></option>
							<%
							}
							}
							%>
						</select>
					</div>

					<div class="col-12">
						<button type="submit" class="btn btn-primary">Assign</button>
					</div>
				</form>
			</div>
		</div>

		<!-- Display Teacher-Subject Mappings -->
		<h3 class="mb-3">Assigned Teacher-Subject Mappings</h3>
		<table class="table table-bordered table-striped">
			<thead class="table-dark">
				<tr>
					<th>Teacher Name</th>
					<th>Subject Name</th>
					<th>Action</th>
				</tr>
			</thead>
			<tbody>
				<%
				List<TeacherSubjectDTO> teacherSubjects = (List<TeacherSubjectDTO>) request.getAttribute("teacherSubject");
				if (teacherSubjects != null) {
					for (TeacherSubjectDTO ts : teacherSubjects) {
						if (ts != null && ts.getTeacherName() != null && ts.getSubjectName() != null) {
				%>
				<tr>
					<td><%=ts.getTeacherName()%></td>
					<td><%=ts.getSubjectName()%></td>
					<td>
						<form action="deleteMapping" method="post"
							style="display: inline;">
							<input type="hidden" name="teacherId"
								value="<%=ts.getTeacherId()%>"> <input type="hidden"
								name="subjectId" value="<%=ts.getSubjectId()%>">
							<button type="submit" class="btn btn-danger btn-sm"
								onclick="return confirm('Are you sure to delete this mapping?')">Delete</button>
						</form>
					</td>
				</tr>
				<%
				}
				}
				}
				%>
			</tbody>
		</table>
	</div>

</body>
</html>
