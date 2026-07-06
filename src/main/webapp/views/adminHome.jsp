<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.moviebooking.model.User" %>
<%
    User loggedInUser = (User) session.getAttribute("loggedInUser");
    if (loggedInUser == null) {
        response.sendRedirect(request.getContextPath() + "/views/login.jsp");
        return;
    }
    if (!loggedInUser.getRole().equalsIgnoreCase("ADMIN")) {
        response.sendRedirect(request.getContextPath() + "/views/home.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>CineBook Admin - Dashboard</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<div class="navbar">
<div class="brand">CINEBOOK ADMIN</div>
<div class="nav-right">
<span>Hello, <%= loggedInUser.getFullName() %></span>
<a href="${pageContext.request.contextPath}/LogoutServlet" class="logout-btn">Logout</a>
</div>
</div>

<div class="page-container">

<div class="section-title">
<h3>ADMIN DASHBOARD</h3>
</div>

<div class="movie-grid">

<div class="movie-card">
<div class="movie-info">
<div class="movie-name">Manage Movies</div>
<div class="movie-meta">Add, view, and delete movies</div>
<a href="${pageContext.request.contextPath}/AdminMovieServlet">
<button class="book-btn">Go</button>
</a>
</div>
</div>

<div class="movie-card">
<div class="movie-info">
<div class="movie-name">Manage Theatres</div>
<div class="movie-meta">Add, view, and delete theatres</div>
<a href="${pageContext.request.contextPath}/AdminTheatreServlet">
<button class="book-btn">Go</button>
</a>
</div>
</div>

<div class="movie-card">
<div class="movie-info">
<div class="movie-name">Manage Shows</div>
<div class="movie-meta">Add, view, and delete shows</div>
<a href="${pageContext.request.contextPath}/AdminShowServlet">
<button class="book-btn">Go</button>
</a>
</div>
</div>

</div>

</div>

</body>
</html>