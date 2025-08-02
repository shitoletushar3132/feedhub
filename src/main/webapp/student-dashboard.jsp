<%@page import="com.feedhub.model.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
response.setHeader("Pragma", "no-cache"); // HTTP 1.0
response.setDateHeader("Expires", 0); // Proxies
%>

<%
// Check if session exists and user is logged in
User user = (User) session.getAttribute("user"); // or "user", depending on what you stored
if (user.getUsername() == null) {
	response.sendRedirect("login.jsp"); // Redirect to login page
	return;
}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h2> welcome <%=user.getUsername() %> student user here</h2>
</body>
</html>