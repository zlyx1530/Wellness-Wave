import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/deleteData")
public class DeleteDataServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("WEB-INF/views/deleteData.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String metric = request.getParameter("metric");
        boolean success = deleteData(metric);
        if (success) {
            request.setAttribute("message", "Data successfully deleted.");
        } else {
            request.setAttribute("message", "Data not found.");
        }
        request.getRequestDispatcher("WEB-INF/views/deleteData.jsp").forward(request, response);
    }

    private boolean deleteData(String metric) {
        boolean success = false;
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "DELETE FROM health_metrics WHERE metric_name = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, metric);
                int rowsAffected = statement.executeUpdate();
                success = rowsAffected > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return success;
    }
}
