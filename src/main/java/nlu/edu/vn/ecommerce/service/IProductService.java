package nlu.edu.vn.ecommerce.service;

import nlu.edu.vn.ecommerce.exception.ResponseObject;
import nlu.edu.vn.ecommerce.models.Product;

import java.util.List;


public interface IProductService {
    ResponseObject getProductsByCategoryId(String categoryId);
    boolean existsByCategoryId(String categoryId);
    List<Product> getAllProducts();
    List<Product> findProductByName(String name);
    ResponseObject insertProduct(Product product);

}
