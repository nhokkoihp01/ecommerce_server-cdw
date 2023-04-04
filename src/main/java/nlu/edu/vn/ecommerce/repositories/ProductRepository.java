package nlu.edu.vn.ecommerce.repositories;

import nlu.edu.vn.ecommerce.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {
    List<Product> findByCategoryId(String categoryId);

    List<Product> findAllByOrderByIdDesc();

    Optional<Product> findById(String id);

    boolean existsByCategoryId(String categoryId);

    List<Product> findProductByName(String name);

    List<Product> findByNameContainingIgnoreCase(String name);

    List<Product> findByCategoryIdIn(List<String> categoryIds);

    Page<Product> findByCategoryIdIn(List<String> categoryIds, Pageable pageable);

    List<Product> findByNewPriceBetween(double minPrice, double maxPrice);


}
