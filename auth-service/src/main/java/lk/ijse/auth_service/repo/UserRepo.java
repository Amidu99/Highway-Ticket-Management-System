package lk.ijse.auth_service.repo;

import lk.ijse.auth_service.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface UserRepo extends MongoRepository<User, String> {
    boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);
    User findUserByEmail(String email);
    void deleteByEmail(String email);
}