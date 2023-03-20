package nlu.edu.vn.ecommerce.service.impl;

import nlu.edu.vn.ecommerce.exception.ResponseObject;
import nlu.edu.vn.ecommerce.models.Category;
import nlu.edu.vn.ecommerce.repositories.CategoryRepository;
import nlu.edu.vn.ecommerce.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements ICategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public ResponseObject getAllCategories() {
        return  new ResponseObject("ok","thành công",categoryRepository.findAll());
    }

    @Override
    public List<Category> findCategoryByName(String name) {
        return categoryRepository.findCategoryByName(name);
    }

    @Override
    public ResponseObject insertCategory(Category category) {
        return  new ResponseObject("ok","thêm thành công",categoryRepository.save(category));
    }


}
