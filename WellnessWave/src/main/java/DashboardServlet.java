import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Check if user is logged in
        User loggedInUser = (User) request.getSession().getAttribute("loggedInUser");
        if (loggedInUser != null) {
            // User is logged in, show dashboard
            request.getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(request, response);
        } else {
            // User is not logged in, redirect to login page
            response.sendRedirect(request.getContextPath() + "/login");
        }
    }
}

