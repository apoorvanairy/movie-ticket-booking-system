<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.moviebooking.model.User" %>
<%@ page import="com.moviebooking.model.Movie" %>
<%@ page import="java.util.List" %>
<%
    User loggedInUser = (User) session.getAttribute("loggedInUser");
    if (loggedInUser == null) {
        response.sendRedirect(request.getContextPath() + "/views/login.jsp");
        return;
    }
    List<Movie> movieList = (List<Movie>) request.getAttribute("movieList");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>CineBook - Now Showing</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

    <div class="navbar">
        <div class="brand">CINEBOOK</div>
        <div class="nav-right">
            <a href="${pageContext.request.contextPath}/BookingHistoryServlet">My Bookings</a>
            <span>Hello, <%= loggedInUser.getFullName() %></span>
            <a href="${pageContext.request.contextPath}/LogoutServlet" class="logout-btn">Logout</a>
        </div>
    </div>

    <div class="page-container">

        <div class="section-title">
            <h3>NOW SHOWING</h3>
        </div>

        <div class="movie-grid">
            <% if (movieList != null && !movieList.isEmpty()) {
                for (Movie movie : movieList) {
                    String imgUrl = movie.getImageUrl();
                    boolean hasRealImage = (imgUrl != null && imgUrl.trim().startsWith("http"));
            %>

                    <div class="movie-card">
                        <% if (hasRealImage) { %>
                            <img src="<%= imgUrl %>" alt="<%= movie.getMovieName() %>"
                                 style="width:100%; aspect-ratio: 2/3; object-fit: cover;">
                        <% } else { %>
                            <div class="movie-poster"><%= movie.getMovieName() %></div>
                        <% } %>
                        <div class="ticket-divider"></div>
                        <div class="movie-info">
                            <div class="movie-name"><%= movie.getMovieName() %></div>
                            <div class="movie-meta"><%= movie.getLanguage() %> &middot; <%= movie.getDurationMinutes() %> mins</div>
                            <span class="badge"><%= movie.getGenre() %></span>
                            <a href="${pageContext.request.contextPath}/ShowListServlet?movieId=<%= movie.getMovieId() %>">
                                <button class="book-btn">Book Now</button>
                            </a>
                        </div>
                    </div>

            <%  }
            } else { %>
                <p>No movies available right now.</p>
            <% } %>
        </div>

    </div>

</body>
</html>