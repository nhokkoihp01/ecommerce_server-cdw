package nlu.edu.vn.ecommerce.service;

import nlu.edu.vn.ecommerce.exception.ResponseObject;
import nlu.edu.vn.ecommerce.models.Product;

import java.util.List;
import java.util.Optional;


public interface IProductService {
    ResponseObject getProductsByCategoryId(String categoryId);

    boolean existsByCategoryId(String categoryId);

    boolean updateProductById(String productId, Product product,String userId);

    boolean deleteProductById(String productId);

    List<Product> getAllProducts(int maxResult);

    List<Product> findProductByName(String name);

    ResponseObject insertProduct(Product product,String userId);

    Optional<Product> getProductById(String id);

    List<Product> findProductBySearch(String search, int maxResult);

    List<Product> getProductByFilterPrice(double minPrice, double maxPrice);

}
