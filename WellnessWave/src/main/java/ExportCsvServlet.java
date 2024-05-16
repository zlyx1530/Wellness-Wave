import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/exportCsv")
public class ExportCsvServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User loggedInUser = (User) request.getSession().getAttribute("loggedInUser");
        if (loggedInUser != null) {
            List<WellnessData> wellnessData = retrieveWellnessData(loggedInUser.getId());

            response.setContentType("text/csv");
            response.setHeader("Content-Disposition", "attachment; filename=WellnessData.csv");

            try (PrintWriter writer = response.getWriter()) {
                writer.println("Metric Name,Value,Date");
                for (WellnessData data : wellnessData) {
                    writer.println(data.getMetricName() + "," + data.getValue() + "," + data.getDate());
                }
            }
        } else {
            response.sendRedirect(request.getContextPath() + "/login");
        }
    }

    private List<WellnessData> retrieveWellnessData(long userId) {
        List<WellnessData> wellnessData = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT metric_name, value, date FROM health_metrics WHERE user_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setLong(1, userId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        String metricName = resultSet.getString("metric_name");
                        String value = resultSet.getString("value");
                        Date date = resultSet.getDate("date");
                        WellnessData data = new WellnessData();
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

