package nlu.edu.vn.ecommerce.service.impl;

import nlu.edu.vn.ecommerce.exception.ResponseObject;
import nlu.edu.vn.ecommerce.models.Product;
import nlu.edu.vn.ecommerce.repositories.ProductRepository;
import nlu.edu.vn.ecommerce.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements IProductService {
    @Autowired
    private ProductRepository productRepository;

    @Override
    public ResponseObject getProductsByCategoryId(String categoryId) {
        return new ResponseObject("ok","",productRepository.findByCategoryId(categoryId));
    }

    @Override
    public boolean existsByCategoryId(String categoryId) {
        return productRepository.existsByCategoryId(categoryId);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> findProductByName(String name) {
        return productRepository.findProductByName(name);
    }

    @Override
    public ResponseObject insertProduct(Product product) {
       return  new ResponseObject("ok","thêm thành công",productRepository.save(product));
    }

    @Override
    public Optional<Product> getProductById(String id) {
        return productRepository.findById(id);
    }

}
