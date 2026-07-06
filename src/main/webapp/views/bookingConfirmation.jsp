<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.moviebooking.model.User" %>
<%@ page import="com.moviebooking.model.Booking" %>
<%
    User loggedInUser = (User) session.getAttribute("loggedInUser");
    if (loggedInUser == null) {
        response.sendRedirect(request.getContextPath() + "/views/login.jsp");
        return;
    }

    Booking booking = (Booking) request.getAttribute("booking");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>CineBook - Booking Confirmed</title>
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
        <div class="auth-wrapper" style="min-height: auto; margin-top: 40px;">
            <div class="auth-card">

                <h1 style="color: var(--accent-gold); text-align:center;">Booking Confirmed!</h1>
                <p class="subtitle">Your tickets are booked successfully</p>

                <div class="ticket-divider" style="margin: 20px 0;"></div>

                <% if (booking != null) { %>
                    <p class="movie-meta">Booking ID: <%= booking.getBookingId() %></p>
                    <p class="movie-meta">Tickets: <%= booking.getNumTickets() %></p>
                    <p class="movie-meta">Total Paid: &#8377;<%= booking.getTotalAmount() %></p>
                    <p class="movie-meta">Status: <%= booking.getBookingStatus() %></p>
                <% } %>

                <a href="${pageContext.request.contextPath}/MovieListServlet">
                    <button class="auth-submit" style="margin-top: 20px;">Back to Movies</button>
                </a>

            </div>
        </div>
    </div>

</body>
</html>