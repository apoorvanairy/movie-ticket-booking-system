<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.moviebooking.model.User" %>
<%@ page import="com.moviebooking.model.Theatre" %>
<%@ page import="java.util.List" %>
<%
    User loggedInUser = (User) session.getAttribute("loggedInUser");
    if (loggedInUser == null || !loggedInUser.getRole().equalsIgnoreCase("ADMIN")) {
        response.sendRedirect(request.getContextPath() + "/views/login.jsp");
        return;
    }
    List<Theatre> theatreList = (List<Theatre>) request.getAttribute("theatreList");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>CineBook Admin - Manage Theatres</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<div class="navbar">
<div class="brand">CINEBOOK ADMIN</div>
<div class="nav-right">
<a href="${pageContext.request.contextPath}/views/adminHome.jsp">Dashboard</a>
<span>Hello, <%= loggedInUser.getFullName() %></span>
<a href="${pageContext.request.contextPath}/LogoutServlet" class="logout-btn">Logout</a>
</div>
</div>

<div class="page-container">

<div class="section-title">
<h3>MANAGE THEATRES</h3>
</div>

<%
if (request.getAttribute("errorMessage") != null) {
%>
<div class="error-message"><%= request.getAttribute("errorMessage") %></div>
<%
}
%>

<div class="auth-card" style="max-width: 600px; margin-bottom: 40px;">
<h2 style="font-size: 1.4rem; margin-bottom: 16px;">Add New Theatre</h2>

<form action="${pageContext.request.contextPath}/AdminTheatreServlet" method="post">

<div class="form-group">
<label for="theatreName">Theatre Name</label>
<input type="text" id="theatreName" name="theatreName" required>
</div>

<div class="form-group">
<label for="location">Location</label>
<input type="text" id="location" name="location" required>
</div>

<button type="submit" class="auth-submit">Add Theatre</button>
</form>
</div>

<div class="section-title">
<h3>ALL THEATRES</h3>
</div>

<div class="movie-grid">
<%
if (theatreList != null && !theatreList.isEmpty()) {
    for (Theatre theatre : theatreList) {
%>
<div class="movie-card">
<div class="movie-info">
<div class="movie-name"><%= theatre.getTheatreName() %></div>
<div class="movie-meta"><%= theatre.getLocation() %></div>

<a href="${pageContext.request.contextPath}/AdminTheatreServlet?action=delete&theatreId=<%= theatre.getTheatreId() %>"
   onclick="return confirm('Are you sure you want to delete this theatre?');">
<button class="book-btn" style="background-color: var(--accent-red); color: white; margin-top: 10px;">Delete</button>
</a>
</div>
</div>
<%
    }
} else {
%>
<p>No theatres added yet.</p>
<%
}
%>
</div>

</div>

</body>
</html>