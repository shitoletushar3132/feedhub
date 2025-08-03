<%@page import="com.feedhub.model.User"%>
<%@ include file="header.jsp" %>

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
<html>
<body>
<h2>welcome <%=user.getUsername() %> <%= "Hello World!" %></h2>
</body>
</html>
