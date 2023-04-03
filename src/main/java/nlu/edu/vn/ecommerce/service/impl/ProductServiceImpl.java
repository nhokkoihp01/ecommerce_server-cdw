package nlu.edu.vn.ecommerce.service.impl;

import nlu.edu.vn.ecommerce.exception.ResponseObject;
import nlu.edu.vn.ecommerce.models.Category;
import nlu.edu.vn.ecommerce.models.Product;
import nlu.edu.vn.ecommerce.repositories.CategoryRepository;
import nlu.edu.vn.ecommerce.repositories.ProductRepository;
import nlu.edu.vn.ecommerce.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements IProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public ResponseObject getProductsByCategoryId(String categoryId) {
        return new ResponseObject("ok", "", productRepository.findByCategoryId(categoryId));
    }

    @Override
    public boolean existsByCategoryId(String categoryId) {
        return productRepository.existsByCategoryId(categoryId);
    }

    @Override
    public boolean updateProductById(String productId, Product product) {
        Optional<Product> optionalProduct = productRepository.findById(productId);

        if (optionalProduct.isPresent()) {
            Product existingProduct = optionalProduct.get();
            existingProduct.setName(product.getName());
            existingProduct.setImage(product.getImage());
            existingProduct.setNewPrice(product.getNewPrice());
            existingProduct.setOldPrice(product.getOldPrice());
            existingProduct.setDescription(product.getDescription());
            existingProduct.setQuantity(product.getQuantity());
            existingProduct.setSale(product.getSale());
            existingProduct.setCategoryId(product.getCategoryId());
            productRepository.save(existingProduct);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean deleteProductById(String productId) {
        if (productRepository.existsById(productId)) {
            productRepository.deleteById(productId);
            return true;
        } else {
            return false;
        }
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
        return new ResponseObject("ok", "thêm thành công", productRepository.save(product));
    }

    @Override
    public Optional<Product> getProductById(String id) {
        return productRepository.findById(id);
    }

    @Override
    public List<Product> findProductBySearch(String search, int maxResult) {
        List<Product> productList = productRepository.findByNameContainingIgnoreCase(search);
        if (!productList.isEmpty()) {
            return maxResult > 0 ? productList.stream().limit(maxResult).collect(Collectors.toList()) : productList;
        }

        List<Category> categoryList = categoryRepository.findByNameContainingIgnoreCase(search);
        if (!categoryList.isEmpty()) {
            List<String> categoryIds = categoryList.stream().map(Category::getId).collect(Collectors.toList());
            if (maxResult <= 0) {
                return productRepository.findByCategoryIdIn(categoryIds);
            } else {
                Page<Product> products = productRepository.findByCategoryIdIn(categoryIds, PageRequest.of(0, maxResult));
                return products.getContent();
            }
        }

        return Collections.emptyList();
    }


    @Override
    public List<Product> getProductByFilterPrice(double minPrice, double maxPrice) {
        return productRepository.findByNewPriceBetween(minPrice, maxPrice);
    }


}
