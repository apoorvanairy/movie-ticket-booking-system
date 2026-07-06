<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.moviebooking.model.User" %>
<%@ page import="com.moviebooking.model.Movie" %>
<%@ page import="java.util.List" %>
<%
    User loggedInUser = (User) session.getAttribute("loggedInUser");
    if (loggedInUser == null || !loggedInUser.getRole().equalsIgnoreCase("ADMIN")) {
        response.sendRedirect(request.getContextPath() + "/views/login.jsp");
        return;
    }
    List<Movie> movieList = (List<Movie>) request.getAttribute("movieList");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>CineBook Admin - Manage Movies</title>
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
<h3>MANAGE MOVIES</h3>
</div>

<%
if (request.getAttribute("errorMessage") != null) {
%>
<div class="error-message"><%= request.getAttribute("errorMessage") %></div>
<%
}
%>

<div class="auth-card" style="max-width: 600px; margin-bottom: 40px;">
<h2 style="font-size: 1.4rem; margin-bottom: 16px;">Add New Movie</h2>

<form action="${pageContext.request.contextPath}/AdminMovieServlet" method="post">

<div class="form-group">
<label for="movieName">Movie Name</label>
<input type="text" id="movieName" name="movieName" required>
</div>

<div class="form-group">
<label for="genre">Genre</label>
<input type="text" id="genre" name="genre" required>
</div>

<div class="form-group">
<label for="language">Language</label>
<input type="text" id="language" name="language" required>
</div>

<div class="form-group">
<label for="durationMinutes">Duration (minutes)</label>
<input type="number" id="durationMinutes" name="durationMinutes" required>
</div>

<div class="form-group">
<label for="description">Description</label>
<input type="text" id="description" name="description">
</div>

<div class="form-group">
<label for="imageUrl">Image URL (poster path)</label>
<input type="text" id="imageUrl" name="imageUrl" placeholder="https://example.com/poster.jpg">
</div>

<button type="submit" class="auth-submit">Add Movie</button>
</form>
</div>

<div class="section-title">
<h3>ALL MOVIES</h3>
</div>

<div class="movie-grid">
<%
if (movieList != null && !movieList.isEmpty()) {
    for (Movie movie : movieList) {
        String imgUrl = movie.getImageUrl();
        boolean hasRealImage = (imgUrl != null && imgUrl.trim().startsWith("http"));
%>
<div class="movie-card">
<%
if (hasRealImage) {
%>
<img src="<%= imgUrl %>" alt="<%= movie.getMovieName() %>" style="width:100%; aspect-ratio: 2/3; object-fit: cover;">
<%
} else {
%>
<div class="movie-poster"><%= movie.getMovieName() %></div>
<%
}
%>
<div class="ticket-divider"></div>
<div class="movie-info">
<div class="movie-name"><%= movie.getMovieName() %></div>
<div class="movie-meta"><%= movie.getLanguage() %> &middot; <%= movie.getDurationMinutes() %> mins</div>
<span class="badge"><%= movie.getGenre() %></span>
<a href="${pageContext.request.contextPath}/AdminMovieServlet?action=delete&movieId=<%= movie.getMovieId() %>" onclick="return confirm('Are you sure you want to delete this movie?');">
<button class="book-btn" style="background-color: var(--accent-red); color: white; margin-top: 10px;">Delete</button>
</a>
</div>
</div>
<%
    }
} else {
%>
<p>No movies added yet.</p>
<%
}
%>
</div>

</div>

</body>
</html>