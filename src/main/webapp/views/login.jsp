<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login - CineBook</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

    <div class="auth-wrapper">
        <div class="auth-card">
            <h1>CINEBOOK</h1>
            <p class="subtitle">Log in to book your next movie</p>

            <% if (request.getAttribute("errorMessage") != null) { %>
                <div class="error-message"><%= request.getAttribute("errorMessage") %></div>
            <% } %>

            <form action="${pageContext.request.contextPath}/LoginServlet" method="post">

                <div class="form-group">
                    <label for="email">Email</label>
                    <input type="email" id="email" name="email" required>
                </div>

                <div class="form-group">
                    <label for="password">Password</label>
                    <input type="password" id="password" name="password" required>
                </div>

                <button type="submit" class="auth-submit">Log In</button>
            </form>

            <p class="auth-footer">
                Don't have an account? <a href="${pageContext.request.contextPath}/views/register.jsp">Register here</a>
            </p>
        </div>
    </div>

</body>
</html>