import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private UserService userService;

    @Override
    public void init() throws ServletException {
        try {
            userService = new UserServiceImpl(new UserRepository());
        } catch (SQLException e) {
            // Handle the exception gracefully, e.g., by logging and forwarding to an error page
            e.printStackTrace();
            throw new ServletException("Error initializing LoginServlet", e);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Forward the request to the login page
        request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        Optional<User> userOptional = userService.getUserByUsername(username);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (password.equals(user.getPassword())) {
                // Login successful, set session attribute
                request.getSession().setAttribute("loggedInUser", user);
                response.sendRedirect(request.getContextPath() + "/dashboard");
                return;
            }
        }

        // If login fails, show login page with error message
        request.setAttribute("errorMessage", "Invalid username or password");
        request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
    }

}

