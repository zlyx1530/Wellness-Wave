import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@WebServlet("/dataGraph")
public class DataGraphServlet extends HttpServlet {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User loggedInUser = (User) request.getSession().getAttribute("loggedInUser");
        if (loggedInUser != null) {
            String metric = request.getParameter("metric");
            List<WellnessData> wellnessData = retrieveWellnessData(loggedInUser.getId(), metric);

            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
            
            response.setContentType("application/json");
            PrintWriter out = response.getWriter();
            out.print(gson.toJson(wellnessData));
            out.flush();
        } else {
            response.sendRedirect(request.getContextPath() + "/login");
        }
    }

    private List<WellnessData> retrieveWellnessData(long userId, String metric) {
        List<WellnessData> wellnessData = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT metric_name, value, date FROM health_metrics WHERE user_id = ? AND metric_name = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setLong(1, userId);
                statement.setString(2, metric);
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


