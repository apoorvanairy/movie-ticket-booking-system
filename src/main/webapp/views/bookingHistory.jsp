<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.moviebooking.model.User" %>
<%@ page import="com.moviebooking.model.Booking" %>
<%@ page import="com.moviebooking.model.Shows" %>
<%@ page import="com.moviebooking.model.Movie" %>
<%@ page import="com.moviebooking.model.Theatre" %>
<%@ page import="java.util.List" %>
<%
    User loggedInUser = (User) session.getAttribute("loggedInUser");
    if (loggedInUser == null) {
        response.sendRedirect(request.getContextPath() + "/views/login.jsp");
        return;
    }

    List<Booking> bookingList = (List<Booking>) request.getAttribute("bookingList");
    List<Shows> showList = (List<Shows>) request.getAttribute("showList");
    List<Movie> movieList = (List<Movie>) request.getAttribute("movieList");
    List<Theatre> theatreList = (List<Theatre>) request.getAttribute("theatreList");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>CineBook - My Bookings</title>
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
            <h3>MY BOOKINGS</h3>
        </div>

        <div class="movie-grid">
            <% if (bookingList != null && !bookingList.isEmpty()) {
                for (int i = 0; i < bookingList.size(); i++) {
                    Booking booking = bookingList.get(i);
                    Shows show = showList.get(i);
                    Movie movie = movieList.get(i);
                    Theatre theatre = theatreList.get(i);
            %>
                    <div class="movie-card">
                        <div class="movie-info">
                            <div class="movie-name"><%= (movie != null) ? movie.getMovieName() : "Movie Unavailable" %></div>
                            <div class="movie-meta"><%= (theatre != null) ? theatre.getTheatreName() : "" %></div>

                            <div class="ticket-divider" style="margin: 14px 0;"></div>

                            <% if (show != null) { %>
                                <div class="movie-meta">Show Date: <%= show.getShowDate() %></div>
                                <div class="movie-meta">Show Time: <%= show.getShowTime() %></div>
                            <% } %>

                            <div class="movie-meta">Booked On: <%= booking.getBookingDate() %></div>
                            <div class="movie-meta">Tickets: <%= booking.getNumTickets() %></div>
                            <div class="movie-meta">Total: &#8377;<%= booking.getTotalAmount() %></div>

                            <span class="badge"><%= booking.getBookingStatus() %></span>
                        </div>
                    </div>
            <%  }
            } else { %>
                <p>You haven't booked any tickets yet.</p>
            <% } %>
        </div>

    </div>

</body>
</html>