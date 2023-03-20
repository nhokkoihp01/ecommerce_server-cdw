package nlu.edu.vn.ecommerce.repositories;

import nlu.edu.vn.ecommerce.models.Category;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CategoryRepository extends MongoRepository<Category,String> {
    List<Category> findCategoryByName(String name);

}
