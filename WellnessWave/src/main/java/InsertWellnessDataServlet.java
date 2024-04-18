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
	    User loggedInUser = (User) request.getSession().getAttribute("loggedInUser");
	    if (loggedInUser != null) {
	        String metricName = request.getParameter("metricName");
	        String value = request.getParameter("value");

	        if (metricName == null || metricName.isEmpty() || value == null || value.isEmpty()) {
	            response.sendRedirect(request.getContextPath() + "/insertWellnessData.jsp?error=invalidInput");
	            return;
	        }

	        if (metricName.equals("sleep") || metricName.equals("cardio") || metricName.equals("dietary_intake") || metricName.equals("calorie_intake")) {
	            try {
	                double numericValue = Double.parseDouble(value);
	                if (numericValue < 0) {
	                    response.sendRedirect(request.getContextPath() + "/insertWellnessData.jsp?error=negativeValue");
	                    return;
	                }
	            } catch (NumberFormatException e) {
	                // Value is not numeric, redirect back to the insertWellnessData page with error message
	                response.sendRedirect(request.getContextPath() + "/insertWellnessData.jsp?error=invalidValueFormat");
	                return;
	            }
	        }

	        try {
	            try (Connection connection = DatabaseConnection.getConnection()) {
	                String insertQuery = "INSERT INTO health_metrics (user_id, metric_name, value, logged_at) VALUES (?, ?, ?, CURRENT_TIMESTAMP)";
	                try (PreparedStatement statement = connection.prepareStatement(insertQuery)) {
	                    statement.setLong(1, loggedInUser.getId());
	                    statement.setString(2, metricName);
	                    statement.setString(3, value);

	                    statement.executeUpdate();
	                }
	            }

	            response.sendRedirect(request.getContextPath() + "/dashboard");
	        } catch (SQLException e) {
	            e.printStackTrace();
	            response.sendRedirect(request.getContextPath() + "/insertWellnessData.jsp?error=databaseError");
	        }
	    } else {
	        response.sendRedirect(request.getContextPath() + "/login");
	    }
	}


}
