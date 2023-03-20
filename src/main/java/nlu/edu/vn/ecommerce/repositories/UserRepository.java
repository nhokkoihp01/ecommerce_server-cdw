package nlu.edu.vn.ecommerce.repositories;

import nlu.edu.vn.ecommerce.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface UserRepository extends MongoRepository<User, String> {
    User findByUsername(String username);
    boolean existsByUsername(String s);
    boolean existsByEmail(String s);
}
