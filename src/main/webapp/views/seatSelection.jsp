<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.moviebooking.model.User" %>
<%@ page import="com.moviebooking.model.Movie" %>
<%@ page import="com.moviebooking.model.Shows" %>
<%@ page import="com.moviebooking.model.Theatre" %>
<%
    User loggedInUser = (User) session.getAttribute("loggedInUser");
    if (loggedInUser == null) {
        response.sendRedirect(request.getContextPath() + "/views/login.jsp");
        return;
    }

    Movie movie = (Movie) request.getAttribute("movie");
    Shows show = (Shows) request.getAttribute("show");
    Theatre theatre = (Theatre) request.getAttribute("theatre");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>CineBook - Select Tickets</title>
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

        <div class="auth-wrapper" style="min-height: auto; margin-top: 20px;">
            <div class="auth-card">

                <h1 style="font-size: 1.8rem;"><%= movie.getMovieName() %></h1>
                <p class="subtitle">
                    <%= theatre.getTheatreName() %> &middot; <%= show.getShowDate() %> &middot; <%= show.getShowTime() %>
                </p>

                <div class="ticket-divider" style="margin: 20px 0;"></div>

                <p class="movie-meta" style="margin-bottom: 20px;">
                    Price per ticket: &#8377;<%= show.getTicketPrice() %><br>
                    Available seats: <%= show.getAvailableSeats() %>
                </p>

                <form action="${pageContext.request.contextPath}/BookingServlet" method="post">

                    <!-- Hidden field to carry the showId along with the form -->
                    <input type="hidden" name="showId" value="<%= show.getShowId() %>">

                    <div class="form-group">
                        <label for="numTickets">Number of Tickets</label>
                        <select id="numTickets" name="numTickets" required
                                style="width:100%; padding:10px; background-color:var(--bg); color:var(--text-primary); border:1px solid var(--border); border-radius:4px;">
                            <% for (int i = 1; i <= Math.min(show.getAvailableSeats(), 6); i++) { %>
                                <option value="<%= i %>"><%= i %></option>
                            <% } %>
                        </select>
                    </div>

                    <button type="submit" class="auth-submit">Confirm Booking</button>
                </form>

            </div>
        </div>

    </div>

</body>
</html>