package nlu.edu.vn.ecommerce.controllers;

import io.swagger.annotations.*;
import nlu.edu.vn.ecommerce.exception.NotFoundException;
import nlu.edu.vn.ecommerce.exception.ResponseObject;
import nlu.edu.vn.ecommerce.models.Product;
import nlu.edu.vn.ecommerce.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private IProductService iProductService;


    @GetMapping("/all")
    @ApiOperation(value = "Get all products", notes = "Get all products with optional max result", response = ResponseObject.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful operation", response = ResponseObject.class),
            @ApiResponse(code = 404, message = "Products not found", response = ResponseObject.class)
    })
    public ResponseEntity<?> getAllProducts(@RequestParam(name = "maxResult", defaultValue = "0") int maxResult) {
        return ResponseEntity.ok().body(
                new ResponseObject("oke", "thành công", iProductService.getAllProducts(maxResult))
        );

    }

    @GetMapping("/all/admin")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, dataType = "string", paramType = "header")
    })
    public ResponseEntity<?> getAllProductsByAdmin(@RequestParam(name = "maxResult", defaultValue = "0") int maxResult) {
        return ResponseEntity.ok().body(
                new ResponseObject("oke", "thành công", iProductService.getAllProducts(maxResult))
        );

    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> findProductById(@PathVariable String id) {
        Optional<Product> product = iProductService.getProductById(id);
        if (product.isPresent()) {
            return ResponseEntity.ok().body(new ResponseObject("oke", "Thành công", product));
        } else {
            throw new NotFoundException("Không tìm thấy sản phẩm");
        }
    }

    @PostMapping("")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, dataType = "string", paramType = "header")
    })
    public ResponseEntity<ResponseObject> insertProduct(@RequestBody Product product,@RequestParam("userId") String userId) {
        List<Product> products = iProductService.findProductByName(product.getName().trim());
        if (products.size() > 0) {
            return ResponseEntity.ok().body(
                    new ResponseObject("400", "Sản phẩm đã tồn tại", null)
            );
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                iProductService.insertProduct(product,userId)
        );

    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, dataType = "string", paramType = "header")
    })
    public ResponseEntity<?> updateProduct(@PathVariable("id") String productId, @RequestBody Product product, @RequestParam String userId) {
        boolean isUpdated = iProductService.updateProductById(productId, product, userId);
        if (isUpdated) {
            return ResponseEntity.ok().body(new ResponseObject("200", "Thành công", null));
        } else {
            return ResponseEntity.ok().body(new ResponseObject("400", "Thất bại", null));
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, dataType = "string", paramType = "header")
    })
    public ResponseEntity<?> deleteProduct(@PathVariable("id") String productId) {
        boolean isDeleted = iProductService.deleteProductById(productId);
        if (isDeleted) {
            return ResponseEntity.ok().body(new ResponseObject("200", "Thành công", null));
        } else {
            return ResponseEntity.ok().body(new ResponseObject("400", "Thất bại", null));
        }
    }


    @GetMapping("/category/{categoryId}")
    public ResponseObject getProductsByCategory(@PathVariable String categoryId) {
        if (iProductService.existsByCategoryId(categoryId)) {
            return iProductService.getProductsByCategoryId(categoryId);
        } else {
            return new ResponseObject("NOT_FOUND", "Không tìm thấy sản phẩm", null);
        }

    }

    @GetMapping("")
    public ResponseEntity<?> searchProducts(
            @RequestParam(name = "search") String search,
            @RequestParam(name = "maxResult", defaultValue = "0") int maxResult
    ) {
        List<Product> products = iProductService.findProductBySearch(search, maxResult);
        if (!products.isEmpty()) {
            return ResponseEntity.ok().body(new ResponseObject("oke", "thành công", products));
        } else {
            return ResponseEntity.ok().body(new ResponseObject("NOT_FOUND", "Không tìm thấy sản phẩm", null));
        }
    }

    @GetMapping("/filter/price")
    public ResponseEntity<?> getProductByFilterPrice(@RequestParam double minPrice, @RequestParam double maxPrice) {
        List<Product> products = iProductService.getProductByFilterPrice(minPrice, maxPrice);
        if (!products.isEmpty()) {
            return ResponseEntity.ok().body(new ResponseObject("oke", "thành công", products));

        } else {
            return ResponseEntity.ok().body(new ResponseObject("NOT_FOUND", "Không tìm thấy sản phẩm", null));
        }
    }


}
