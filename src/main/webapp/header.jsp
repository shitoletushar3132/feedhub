<%@page import="java.net.http.HttpClient.Redirect"%>
<%@ page session="true"%>
<%@ page import="com.feedhub.model.User"%>
<%@ page import="com.feedhub.model.Role"%>

<%
response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
response.setHeader("Pragma", "no-cache"); // HTTP 1.0
response.setDateHeader("Expires", 0); // Proxies
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>FeedHub</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/assets/css/style.css">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

</head>


<body>
	<%
	User currentUser = (User) session.getAttribute("user");
	String roleName = "";
	if (currentUser != null && currentUser.getRole() != null) {
		roleName = currentUser.getRole().getName();
	} else {
		response.sendRedirect("/feedhub/login.jsp"); // Redirect to login page
		return;
	}
	%>

	<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
		<div class="container-fluid">
			<a class="navbar-brand" href="<%=request.getContextPath()%>/home">FeedHub</a>

			<button class="navbar-toggler" type="button"
				data-bs-toggle="collapse" data-bs-target="#navbarNav"
				aria-controls="navbarNav" aria-expanded="false"
				aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>

			<div class="collapse navbar-collapse" id="navbarNav">
				<ul class="navbar-nav ms-auto">

					<%
					if ("admin".equalsIgnoreCase(roleName)) {
					%>
					<li class="nav-item"><a class="nav-link"
						href="<%=request.getContextPath()%>/admin-dashboard.jsp">Dashboard</a>
					</li>
					<li class="nav-item"><a class="nav-link"
						href="<%=request.getContextPath()%>/admin/getStudents">Manage
							Students</a></li>
					<li class="nav-item"><a class="nav-link"
						href="<%=request.getContextPath()%>/admin/getTeachers">Manage
							Teachers</a></li>
					<li class="nav-item"><a class="nav-link"
						href="<%=request.getContextPath()%>/admin/getSubjects">Manage
							Subjects</a></li>
					<%
					} else if ("student".equalsIgnoreCase(roleName)) {
					%>
					<li class="nav-item"><a class="nav-link"
						href="<%=request.getContextPath()%>/student-dashboard.jsp">Dashboard</a>
					</li>
					<li class="nav-item"><a class="nav-link"
						href="<%=request.getContextPath()%>/student/giveFeedback">Add
							Review</a></li>
					<li class="nav-item"><a class="nav-link"
						href="<%=request.getContextPath()%>/student/myFeedbacks">My
							Reviews</a></li>
					<%
					} else if ("teacher".equalsIgnoreCase(roleName)) {
					%>
					<li class="nav-item"><a class="nav-link"
						href="<%=request.getContextPath()%>/teacher-dashboard.jsp">Dashboard</a>
					</li>
					<li class="nav-item"><a class="nav-link"
						href="<%=request.getContextPath()%>/teacher/mySubjects">My
							Subjects</a></li>
					<li class="nav-item"><a class="nav-link"
						href="<%=request.getContextPath()%>/teacher/myReviews">View
							Feedback</a></li>
					<%
					}
					%>

					<%
					if (currentUser != null) {
					%>
					<li class="nav-item">
						<form method="post" action="/feedhub/logout"
							style="display: inline;">
							<button type="submit" class="nav-link btn btn-link text-danger"
								style="text-decoration: none;">Logout</button>
						</form>
					</li>
					<%
					}
					%>

					%>
				</ul>
			</div>
		</div>
	</nav>
</body>
</html>
