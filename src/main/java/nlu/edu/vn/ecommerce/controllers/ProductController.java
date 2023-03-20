package nlu.edu.vn.ecommerce.controllers;

import nlu.edu.vn.ecommerce.exception.NotFoundException;
import nlu.edu.vn.ecommerce.exception.ResponseObject;
import nlu.edu.vn.ecommerce.models.Product;
import nlu.edu.vn.ecommerce.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private IProductService iProductService;


    @GetMapping("/all")
    public ResponseEntity<?> getAllProducts() {
        return ResponseEntity.ok().body(
                new ResponseObject(HttpStatus.FOUND.toString(), "thành công", iProductService.getAllProducts())
        );

    }

    @PostMapping("")
    public ResponseEntity<ResponseObject> insertProduct(@RequestBody Product product) {
        List<Product> products = iProductService.findProductByName(product.getName().trim());
        if (products.size() > 0) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseObject("failed", "Sản phẩm đã tồn tại", null)
            );
        }
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                iProductService.insertProduct(product)
        );

    }


    @GetMapping("/category/{categoryId}")
    public ResponseObject getProductsByCategory(@PathVariable String categoryId) {
        if (iProductService.existsByCategoryId(categoryId)) {
            return iProductService.getProductsByCategoryId(categoryId);
        } else {
            throw new NotFoundException("Không tìm thấy sản phẩm category:" + categoryId);
        }

    }


}
