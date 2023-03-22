package nlu.edu.vn.ecommerce.controllers;

import nlu.edu.vn.ecommerce.exception.ResponseObject;
import nlu.edu.vn.ecommerce.models.Category;
import nlu.edu.vn.ecommerce.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
    @Autowired
    private ICategoryService iCategoryService;
    @GetMapping("")
    public ResponseEntity<?> getAllCategories(){
        return ResponseEntity.ok().body(
                iCategoryService.getAllCategories()
        );
    }
    @PostMapping("")
    public ResponseEntity<ResponseObject> insertProduct(@RequestBody Category category) {
        List<Category> products = iCategoryService.findCategoryByName(category.getName().trim());
        if (products.size() > 0) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseObject("failed", "Sản phẩm đã tồn tại", null)
            );
        }
        return ResponseEntity.ok().body(
                iCategoryService.insertCategory(category)
        );

    }
}
