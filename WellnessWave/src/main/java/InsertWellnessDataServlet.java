import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/insertWellnessData")
public class InsertWellnessDataServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("WEB-INF/views/insertWellnessData.jsp").forward(request, response);
    }
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    // Check if user is logged in
	    User loggedInUser = (User) request.getSession().getAttribute("loggedInUser");
	    if (loggedInUser != null) {
	        // User is logged in, proceed with inserting wellness data
	        String metricName = request.getParameter("metricName");
	        String value = request.getParameter("value");

	        // Validate metricName and value
	        if (metricName == null || metricName.isEmpty() || value == null || value.isEmpty()) {
	            // Invalid parameters, redirect back to the insertWellnessData page with error message
	            response.sendRedirect(request.getContextPath() + "/insertWellnessData.jsp?error=invalidInput");
	            return;
	        }

	        // Validate value format for numeric metrics
	        if (metricName.equals("sleep") || metricName.equals("cardio") || metricName.equals("dietary_intake") || metricName.equals("calorie_intake")) {
	            try {
	                double numericValue = Double.parseDouble(value);
	                // Check if the value is non-negative
	                if (numericValue < 0) {
	                    // Negative value, redirect back to the insertWellnessData page with error message
	                    response.sendRedirect(request.getContextPath() + "/insertWellnessData.jsp?error=negativeValue");
	                    return;
	                }
	            } catch (NumberFormatException e) {
	                // Value is not numeric, redirect back to the insertWellnessData page with error message
	                response.sendRedirect(request.getContextPath() + "/insertWellnessData.jsp?error=invalidValueFormat");
	                return;
	            }
	        }

	        // Insert wellness data into the database based on metric name
	        try {
	            // Create a connection to the database
	            try (Connection connection = DatabaseConnection.getConnection()) {
	                // Create a prepared statement to insert the data
	                String insertQuery = "INSERT INTO health_metrics (user_id, metric_name, value, logged_at) VALUES (?, ?, ?, CURRENT_TIMESTAMP)";
	                try (PreparedStatement statement = connection.prepareStatement(insertQuery)) {
	                    // Set parameters for the prepared statement
	                    statement.setLong(1, loggedInUser.getId());
	                    statement.setString(2, metricName);
	                    statement.setString(3, value);

	                    // Execute the insert statement
	                    statement.executeUpdate();
	                }
	            }

	            // Redirect back to the dashboard or show success message
	            response.sendRedirect(request.getContextPath() + "/dashboard");
	        } catch (SQLException e) {
	            // Handle database insertion error
	            e.printStackTrace();
	            response.sendRedirect(request.getContextPath() + "/insertWellnessData.jsp?error=databaseError");
	        }
	    } else {
	        // User is not logged in, redirect to login page
	        response.sendRedirect(request.getContextPath() + "/login");
	    }
	}


}
