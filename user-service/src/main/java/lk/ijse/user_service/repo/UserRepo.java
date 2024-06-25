package lk.ijse.user_service.repo;

import lk.ijse.user_service.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepo extends MongoRepository<User, String> {
    boolean existsByEmail(String email);
    User getUserByEmail(String email);
    void deleteByEmail(String email);
}