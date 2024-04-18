import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class UserRepository {

    private static final String FIND_BY_USERNAME_QUERY = "SELECT * FROM users WHERE username = ?";

    private Connection connection;

    public UserRepository() throws SQLException {
        this.connection = DatabaseConnection.getConnection(); // Assigning to class field
    }

    public Optional<User> findByUsername(String username) {
        try (PreparedStatement statement = connection.prepareStatement(FIND_BY_USERNAME_QUERY)) {
            statement.setString(1, username);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    User user = new User();
                    user.setId(resultSet.getLong("user_id"));
                    user.setUsername(resultSet.getString("username"));
                    user.setPassword(resultSet.getString("password"));
                    user.setEnabled(resultSet.getBoolean("enabled"));
                    return Optional.of(user);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    // ADD CRUD OPS
    
}
