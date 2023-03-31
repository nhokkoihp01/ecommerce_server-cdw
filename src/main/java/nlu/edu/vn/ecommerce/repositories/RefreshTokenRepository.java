package nlu.edu.vn.ecommerce.repositories;

import nlu.edu.vn.ecommerce.models.RefreshToken;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends MongoRepository<RefreshToken, String> {

}
