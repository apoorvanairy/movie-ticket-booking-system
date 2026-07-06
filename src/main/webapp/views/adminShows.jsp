<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.moviebooking.model.User" %>
<%@ page import="com.moviebooking.model.Movie" %>
<%@ page import="com.moviebooking.model.Theatre" %>
<%@ page import="com.moviebooking.model.Shows" %>
<%@ page import="java.util.List" %>
<%
    User loggedInUser = (User) session.getAttribute("loggedInUser");
    if (loggedInUser == null || !loggedInUser.getRole().equalsIgnoreCase("ADMIN")) {
        response.sendRedirect(request.getContextPath() + "/views/login.jsp");
        return;
    }

    List<Shows> showList = (List<Shows>) request.getAttribute("showList");
    List<Movie> movieForEachShow = (List<Movie>) request.getAttribute("movieForEachShow");
    List<Theatre> theatreForEachShow = (List<Theatre>) request.getAttribute("theatreForEachShow");
    List<Movie> allMovies = (List<Movie>) request.getAttribute("allMovies");
    List<Theatre> allTheatres = (List<Theatre>) request.getAttribute("allTheatres");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>CineBook Admin - Manage Shows</title>
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
<h3>MANAGE SHOWS</h3>
</div>

<%
if (request.getAttribute("errorMessage") != null) {
%>
<div class="error-message"><%= request.getAttribute("errorMessage") %></div>
<%
}
%>

<div class="auth-card" style="max-width: 600px; margin-bottom: 40px;">
<h2 style="font-size: 1.4rem; margin-bottom: 16px;">Add New Show</h2>

<form action="${pageContext.request.contextPath}/AdminShowServlet" method="post">

<div class="form-group">
<label for="movieId">Movie</label>
<select id="movieId" name="movieId" required
        style="width:100%; padding:10px; background-color:var(--bg); color:var(--text-primary); border:1px solid var(--border); border-radius:4px;">
<%
if (allMovies != null) {
    for (Movie m : allMovies) {
%>
<option value="<%= m.getMovieId() %>"><%= m.getMovieName() %></option>
<%
    }
}
%>
</select>
</div>

<div class="form-group">
<label for="theatreId">Theatre</label>
<select id="theatreId" name="theatreId" required
        style="width:100%; padding:10px; background-color:var(--bg); color:var(--text-primary); border:1px solid var(--border); border-radius:4px;">
<%
if (allTheatres != null) {
    for (Theatre t : allTheatres) {
%>
<option value="<%= t.getTheatreId() %>"><%= t.getTheatreName() %></option>
<%
    }
}
%>
</select>
</div>

<div class="form-group">
<label for="showDate">Show Date</label>
<input type="date" id="showDate" name="showDate" required
       style="width:100%; padding:10px; background-color:var(--bg); color:var(--text-primary); border:1px solid var(--border); border-radius:4px;">
</div>

<div class="form-group">
<label for="showTime">Show Time</label>
<input type="time" id="showTime" name="showTime" required
       style="width:100%; padding:10px; background-color:var(--bg); color:var(--text-primary); border:1px solid var(--border); border-radius:4px;">
</div>

<div class="form-group">
<label for="ticketPrice">Ticket Price (Rs.)</label>
<input type="number" step="0.01" id="ticketPrice" name="ticketPrice" required>
</div>

<div class="form-group">
<label for="availableSeats">Available Seats</label>
<input type="number" id="availableSeats" name="availableSeats" required>
</div>

<button type="submit" class="auth-submit">Add Show</button>
</form>
</div>

<div class="section-title">
<h3>ALL SHOWS</h3>
</div>

<div class="movie-grid">
<%
if (showList != null && !showList.isEmpty()) {
    for (int i = 0; i < showList.size(); i++) {
        Shows show = showList.get(i);
        Movie movie = movieForEachShow.get(i);
        Theatre theatre = theatreForEachShow.get(i);
%>
<div class="movie-card">
<div class="movie-info">
<div class="movie-name"><%= (movie != null) ? movie.getMovieName() : "Movie Deleted" %></div>
<div class="movie-meta"><%= (theatre != null) ? theatre.getTheatreName() : "Theatre Deleted" %></div>

<div class="ticket-divider" style="margin: 14px 0;"></div>

<div class="movie-meta">Date: <%= show.getShowDate() %></div>
<div class="movie-meta">Time: <%= show.getShowTime() %></div>
<div class="movie-meta">Price: &#8377;<%= show.getTicketPrice() %></div>
<span class="badge"><%= show.getAvailableSeats() %> seats</span>

<a href="${pageContext.request.contextPath}/AdminShowServlet?action=delete&showId=<%= show.getShowId() %>"
   onclick="return confirm('Are you sure you want to delete this show?');">
<button class="book-btn" style="background-color: var(--accent-red); color: white; margin-top: 10px;">Delete</button>
</a>
</div>
</div>
<%
    }
} else {
%>
<p>No shows added yet.</p>
<%
}
%>
</div>

</div>

</body>
</html>