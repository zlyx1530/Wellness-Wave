import java.util.Optional;

public interface UserService {
    Optional<User> getUserByUsername(String username);
    boolean saveUser(User user);
}
