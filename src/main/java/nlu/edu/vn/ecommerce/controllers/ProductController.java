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
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private IProductService iProductService;


    @GetMapping("/all")
    public ResponseEntity<?> getAllProducts() {
        return ResponseEntity.ok().body(
                new ResponseObject("oke", "thành công", iProductService.getAllProducts())
        );

    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> findProductById(@PathVariable String id){
        Optional<Product> product = iProductService.getProductById(id);
        if(product.isPresent()){
            return  ResponseEntity.ok().body(new ResponseObject("oke","Thành công",product));
        }
        else{
            throw new NotFoundException("Không tìm thấy sản phẩm");
        }
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
            return new ResponseObject("NOT_FOUND","Không tìm thấy sản phẩm",null);
        }

    }
    @GetMapping("")
    public ResponseEntity<?> searchProducts(
            @RequestParam(name = "search") String search,
            @RequestParam(name = "maxResult", defaultValue = "0") int maxResult
    ) {
        List<Product> products = iProductService.findProductBySearch(search, maxResult);
        if (!products.isEmpty()) {
            return ResponseEntity.ok().body(new ResponseObject("oke","thành công",products));
        } else {
            return ResponseEntity.ok().body(new ResponseObject("failed","Không tìm thấy sản phẩm",null));
        }
    }


}
