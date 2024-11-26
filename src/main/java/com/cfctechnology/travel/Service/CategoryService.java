package com.cfctechnology.travel.Service;

import com.cfctechnology.travel.Model.Category;
import com.cfctechnology.travel.Model.PageResult;
import com.cfctechnology.travel.Repository.CategoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category getCategoryById(long id) {
        Optional<Category> category = categoryRepository.findById(id);
        return category.orElse(null);
    }

    public PageResult<Category> getPageCategorys(int page, int size, String name){

        Pageable pageable = PageRequest.of(page, size);
        Page<Category> cates;
        if (name != null && !name.isEmpty()) {
            cates = this.categoryRepository.findByNameContaining(name, pageable);
        } else {
            cates = this.categoryRepository.findAll(pageable);
        }
        return new PageResult<>(cates.getContent(), cates.getTotalPages());
    }

    public Category createCategory(Category category) {
        Category newCategory = new Category();

        newCategory.setName(category.getName());
        newCategory.setDescription(category.getDescription());
        return this.categoryRepository.save(newCategory);
    }

    public Category updateCategory(Category category,long id) {
        Optional<Category> categoryOptional = categoryRepository.findById(id);
        if (categoryOptional.isPresent()) {
            categoryOptional.get().setDescription(category.getDescription());
            categoryOptional.get().setName(category.getName());
            return this.categoryRepository.save(categoryOptional.get());
        }
        return null;
    }

    public void deleteCategory(long id) {
        this.categoryRepository.deleteById(id);
    }


}
