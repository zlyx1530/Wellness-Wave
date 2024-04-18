import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    private UserService userService;

    @Override
    public void init() throws ServletException {
        try {
            userService = new UserServiceImpl(new UserRepository()); // Instantiate with your actual repository
        } catch (SQLException e) {
            throw new ServletException("Error initializing RegisterServlet", e);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Forward the request to the registration page
        request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Check if username already exists
        if (userService.getUserByUsername(username).isPresent()) {
            // Username already exists, show error message
            request.setAttribute("errorMessage", "Username already exists");
            request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
            return;
        }

        // Create new user
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEnabled(true);

        // Save user to database
        if (userService.saveUser(user)) {
            // User created successfully, add confirmation message to redirect
            request.getSession().setAttribute("successMessage", "User created successfully. You can now login.");
            response.sendRedirect(request.getContextPath() + "/login");
        } else {
            // Error saving user to database, show error message
            request.setAttribute("errorMessage", "Error creating user. Please try again.");
            request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
        }
    }
}

