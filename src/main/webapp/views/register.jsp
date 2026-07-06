<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Register - CineBook</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

    <div class="auth-wrapper">
        <div class="auth-card">
            <h1>CINEBOOK</h1>
            <p class="subtitle">Create your account</p>

            <% if (request.getAttribute("errorMessage") != null) { %>
                <div class="error-message"><%= request.getAttribute("errorMessage") %></div>
            <% } %>

            <form action="${pageContext.request.contextPath}/RegisterServlet" method="post">

                <div class="form-group">
                    <label for="fullName">Full Name</label>
                    <input type="text" id="fullName" name="fullName" required>
                </div>

                <div class="form-group">
                    <label for="email">Email</label>
                    <input type="email" id="email" name="email" required>
                </div>

                <div class="form-group">
                    <label for="password">Password</label>
                    <input type="password" id="password" name="password" required>
                </div>

                <div class="form-group">
                    <label for="phone">Phone</label>
                    <input type="text" id="phone" name="phone" required>
                </div>

                <button type="submit" class="auth-submit">Register</button>
            </form>

            <p class="auth-footer">
                Already have an account? <a href="${pageContext.request.contextPath}/views/login.jsp">Login here</a>
            </p>
        </div>
    </div>

</body>
</html>