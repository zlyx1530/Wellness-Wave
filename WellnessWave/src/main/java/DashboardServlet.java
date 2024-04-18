import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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
            // Retrieve wellness metrics for the logged-in user
            List<WellnessData> wellnessData = retrieveWellnessData(loggedInUser.getId());

            // Print the received list to the console
            System.out.println("Received wellness data: " + wellnessData);

            // Set wellness data as request attribute
            request.setAttribute("wellnessData", wellnessData);

            // Forward the request to the dashboard page
            request.getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(request, response);
        } else {
            // User is not logged in, redirect to login page
            response.sendRedirect(request.getContextPath() + "/login");
        }
    }

    private List<WellnessData> retrieveWellnessData(long userId) {
        List<WellnessData> wellnessData = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection()) {
            // Debug statement to verify connection
            System.out.println("Connection established successfully.");

            String query = "SELECT metric_name, value FROM health_metrics WHERE user_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setLong(1, userId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        String metricName = resultSet.getString("metric_name");
                        String value = resultSet.getString("value");
                        WellnessData data = new WellnessData();
                        data.setMetricName(metricName);
                        data.setValue(value);
                        data.setUserId(userId);
                        wellnessData.add(data);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return wellnessData;
    }
}


