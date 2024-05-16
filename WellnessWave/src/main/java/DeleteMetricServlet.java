import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/deleteMetric")
public class DeleteMetricServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User loggedInUser = (User) request.getSession().getAttribute("loggedInUser");
        if (loggedInUser != null) {
            List<WellnessData> wellnessData = retrieveWellnessData(loggedInUser.getId());
            request.setAttribute("wellnessData", wellnessData);

            request.getRequestDispatcher("/WEB-INF/views/deleteRow.jsp").forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/login");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int metricId;
        try {
            metricId = Integer.parseInt(request.getParameter("metricId"));
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Invalid Metric ID.");
            request.getRequestDispatcher("/WEB-INF/views/deleteRow.jsp").forward(request, response);
            return;
        }

        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "DELETE FROM health_metrics WHERE metric_id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, metricId);
                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    request.getSession().setAttribute("successMessage", "Row deleted successfully.");
                } else {
                    request.getSession().setAttribute("errorMessage", "No rows deleted. Metric ID not found.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            request.getSession().setAttribute("errorMessage", "Error deleting row.");
        }
        response.sendRedirect(request.getContextPath() + "/deleteMetric");
    }


    
    private List<WellnessData> retrieveWellnessData(long userId) {
        List<WellnessData> wellnessData = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT metric_id, metric_name, value, date FROM health_metrics WHERE user_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setLong(1, userId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                    	int metricId = resultSet.getInt("metric_id");
                        String metricName = resultSet.getString("metric_name");
                        String value = resultSet.getString("value");
                        Date date = resultSet.getDate("date");
                        WellnessData data = new WellnessData();
                        data.setMetricId(metricId);
                        data.setMetricName(metricName);
                        data.setValue(value);
                        data.setDate(date);
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
