package nlu.edu.vn.ecommerce.service;

import nlu.edu.vn.ecommerce.exception.ResponseObject;
import nlu.edu.vn.ecommerce.models.Category;


import java.util.List;

public interface ICategoryService {
    ResponseObject getAllCategories();
    List<Category> findCategoryByName(String name);
    ResponseObject insertCategory(Category category);
}
