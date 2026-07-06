<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.moviebooking.model.User" %>
<%@ page import="com.moviebooking.model.Movie" %>
<%@ page import="com.moviebooking.model.Shows" %>
<%@ page import="com.moviebooking.model.Theatre" %>
<%@ page import="java.util.List" %>
<%
    User loggedInUser = (User) session.getAttribute("loggedInUser");
    if (loggedInUser == null) {
        response.sendRedirect(request.getContextPath() + "/views/login.jsp");
        return;
    }

    Movie movie = (Movie) request.getAttribute("movie");
    List<Shows> showList = (List<Shows>) request.getAttribute("showList");
    List<Theatre> theatreList = (List<Theatre>) request.getAttribute("theatreList");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>CineBook - Select Show</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

    <div class="navbar">
        <div class="brand">CINEBOOK</div>
        <div class="nav-right">
            <span>Hello, <%= loggedInUser.getFullName() %></span>
            <a href="${pageContext.request.contextPath}/LogoutServlet" class="logout-btn">Logout</a>
        </div>
    </div>

    <div class="page-container">

        <a href="${pageContext.request.contextPath}/MovieListServlet">&larr; Back to Movies</a>

        <div class="section-title" style="margin-top: 20px;">
            <h2><%= (movie != null) ? movie.getMovieName() : "Movie" %></h2>
        </div>

        <p class="movie-meta" style="margin-bottom: 24px;">
            <%= (movie != null) ? movie.getGenre() + " &middot; " + movie.getLanguage() + " &middot; " + movie.getDurationMinutes() + " mins" : "" %>
        </p>

        <h3 style="margin-bottom: 16px;">SELECT A SHOW</h3>

        <div class="movie-grid">
            <% if (showList != null && !showList.isEmpty()) {
                for (int i = 0; i < showList.size(); i++) {
                    Shows show = showList.get(i);
                    Theatre theatre = theatreList.get(i);
            %>
                    <div class="movie-card">
                        <div class="movie-info">
                            <div class="movie-name"><%= (theatre != null) ? theatre.getTheatreName() : "Theatre" %></div>
                            <div class="movie-meta"><%= (theatre != null) ? theatre.getLocation() : "" %></div>

                            <div class="ticket-divider" style="margin: 14px 0;"></div>

                            <div class="movie-meta">Date: <%= show.getShowDate() %></div>
                            <div class="movie-meta">Time: <%= show.getShowTime() %></div>
                            <div class="movie-meta">Price: &#8377;<%= show.getTicketPrice() %></div>
                            <span class="badge"><%= show.getAvailableSeats() %> seats left</span>

                            <a href="${pageContext.request.contextPath}/SeatSelectionServlet?showId=<%= show.getShowId() %>">
                                <button class="book-btn">Select Seats</button>
                            </a>
                        </div>
                    </div>
            <%  }
            } else { %>
                <p>No shows available for this movie right now.</p>
            <% } %>
        </div>

    </div>

</body>
</html>