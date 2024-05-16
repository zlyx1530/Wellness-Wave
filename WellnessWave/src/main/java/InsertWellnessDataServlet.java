import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Date;

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
            String dateString = request.getParameter("date");
            String errorMessage = null;

            if (metricName == null || metricName.isEmpty() || value == null || value.isEmpty() || dateString == null || dateString.isEmpty()) {
                errorMessage = "Invalid input: Metric Name, Value, and Date cannot be empty";
            } else if (!isValidMetric(metricName)) {
                errorMessage = "Invalid input: Metric Name is not valid";
            } else {
                try {
                    double numericValue = Double.parseDouble(value);
                    if (numericValue < 0) {
                        errorMessage = "Invalid input: Value cannot be negative";
                    }
                } catch (NumberFormatException e) {
                    errorMessage = "Invalid input: Value is not a valid number";
                }
            }

            if (errorMessage != null) {
                request.setAttribute("errorMessage", errorMessage);
                request.getRequestDispatcher("WEB-INF/views/insertWellnessData.jsp").forward(request, response);
                return;
            }

            try {
                Date date = Date.valueOf(dateString);
                try (Connection connection = DatabaseConnection.getConnection()) {
                    String insertQuery = "INSERT INTO health_metrics (user_id, metric_name, value, date) VALUES (?, ?, ?, ?)";
                    try (PreparedStatement statement = connection.prepareStatement(insertQuery)) {
                        statement.setLong(1, loggedInUser.getId());
                        statement.setString(2, metricName);
                        statement.setString(3, value);
                        statement.setDate(4, date);

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

    private boolean isValidMetric(String metricName) {
        return metricName.equals("sleep") || metricName.equals("food") || metricName.equals("stress") || metricName.equals("mood") || metricName.equals("nutrition");
    }
}




