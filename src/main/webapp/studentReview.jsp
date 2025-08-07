<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*, com.feedhub.model.TeacherSubjectDTO"%>
<%@ include file="/header.jsp"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Give Feedback</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet">
</head>
<body>




	<div class="container mt-5">
		<h3 class="mb-4">Give Feedback</h3>

		<%
		String error = request.getParameter("error");
		if ("true".equals(error)) {
		%>
		<p id="error-message" class="error-msg">Your Review is not Added</p>
		<%
		} else {
		%>
		<p id="error-message" class="text-success">Your Review is Added</p>
		<%
		}
		%>

		<table class="table table-bordered">
			<thead class="table-light">
				<tr>
					<th>Teacher</th>
					<th>Subject</th>
					<th>Action</th>
				</tr>
			</thead>
			<tbody>
				<%
				List<TeacherSubjectDTO> teacherSubjects = (List<TeacherSubjectDTO>) request.getAttribute("teacherSubjects");

				for (TeacherSubjectDTO ts : teacherSubjects) {
				%>
				<tr>
					<td><%=ts.getTeacherName()%></td>
					<td><%=ts.getSubjectName()%></td>
					<td>
						<button class="btn btn-primary btn-sm" data-bs-toggle="modal"
							data-bs-target="#feedbackModal"
							data-teacher-id="<%=ts.getTeacherId()%>"
							data-teacher-name="<%=ts.getTeacherName()%>"
							data-subject-id="<%=ts.getSubjectId()%>"
							data-subject-name="<%=ts.getSubjectName()%>">Give Review</button>
					</td>
				</tr>
				<%
				}
				%>
			</tbody>
		</table>
	</div>

	<!-- Feedback Modal -->
	<div class="modal fade" id="feedbackModal" tabindex="-1"
		aria-hidden="true">
		<div class="modal-dialog">
			<form action="student" method="post" class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">Submit Feedback</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal"></button>
				</div>
				<div class="modal-body">
					<input type="hidden" name="studentId"
						value=<%=currentUser.getId()%>> <input type="hidden"
						id="teacherId" name="teacherId"> <input type="hidden"
						id="subjectId" name="subjectId">

					<div class="mb-3">
						<label class="form-label">Teacher</label> <input type="text"
							id="teacherName" class="form-control" disabled>
					</div>

					<div class="mb-3">
						<label class="form-label">Subject</label> <input type="text"
							id="subjectName" class="form-control" disabled>
					</div>

					<div class="mb-3">
						<label for="rating" class="form-label">Rating (1–5)</label> <input
							type="number" class="form-control" name="rating" min="1" max="5"
							required>
					</div>

					<div class="mb-3">
						<label for="comment" class="form-label">Comment</label>
						<textarea class="form-control" name="comment" rows="3" required></textarea>
					</div>
				</div>
				<div class="modal-footer">
					<button type="submit" class="btn btn-success">Submit</button>
					<button type="button" class="btn btn-secondary"
						data-bs-dismiss="modal">Cancel</button>
				</div>
			</form>
		</div>
	</div>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
	<script>
		const feedbackModal = document.getElementById('feedbackModal');
		feedbackModal.addEventListener('show.bs.modal', function(event) {
			const button = event.relatedTarget;

			const teacherId = button.getAttribute('data-teacher-id');
			const teacherName = button.getAttribute('data-teacher-name');
			const subjectId = button.getAttribute('data-subject-id');
			const subjectName = button.getAttribute('data-subject-name');

			feedbackModal.querySelector('#teacherId').value = teacherId;
			feedbackModal.querySelector('#subjectId').value = subjectId;
			feedbackModal.querySelector('#teacherName').value = teacherName;
			feedbackModal.querySelector('#subjectName').value = subjectName;
		});
	</script>

</body>
</html>
