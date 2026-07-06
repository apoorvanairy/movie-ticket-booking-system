package com.moviebooking.controller;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.moviebooking.dao.TheatreDao;
import com.moviebooking.dao.impl.TheatreDaoImpl;
import com.moviebooking.model.Theatre;
import com.moviebooking.model.User;

/**
 * AdminTheatreServlet handles all admin actions related to Theatres:
 * - Viewing all theatres (default action)
 * - Adding a new theatre
 * - Deleting an existing theatre
 */
@WebServlet("/AdminTheatreServlet")
public class AdminTheatreServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private TheatreDao theatreDao = new TheatreDaoImpl();

    private boolean isAdmin(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        User loggedInUser = (session != null) ? (User) session.getAttribute("loggedInUser") : null;
        return loggedInUser != null && loggedInUser.getRole().equalsIgnoreCase("ADMIN");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (!isAdmin(request)) {
            response.sendRedirect(request.getContextPath() + "/views/login.jsp");
            return;
        }

        String action = request.getParameter("action");

        if ("delete".equals(action)) {
            int theatreId = Integer.parseInt(request.getParameter("theatreId"));
            boolean deleted = theatreDao.deleteTheatre(theatreId);

            List<Theatre> theatreList = theatreDao.getAllTheatres();
            request.setAttribute("theatreList", theatreList);

            if (!deleted) {
                request.setAttribute("errorMessage",
                        "Cannot delete this theatre because it has active shows. Please delete its shows first.");
            }

            request.getRequestDispatcher("/views/adminTheatres.jsp").forward(request, response);
            return;
        }

        // ---------- DEFAULT: LIST ALL THEATRES ----------
        List<Theatre> theatreList = theatreDao.getAllTheatres();
        request.setAttribute("theatreList", theatreList);
        request.getRequestDispatcher("/views/adminTheatres.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (!isAdmin(request)) {
            response.sendRedirect(request.getContextPath() + "/views/login.jsp");
            return;
        }

        // ---------- ADD NEW THEATRE ----------
        String theatreName = request.getParameter("theatreName");
        String location = request.getParameter("location");

        Theatre newTheatre = new Theatre(theatreName, location);
        theatreDao.addTheatre(newTheatre);

        response.sendRedirect(request.getContextPath() + "/AdminTheatreServlet");
    }
}