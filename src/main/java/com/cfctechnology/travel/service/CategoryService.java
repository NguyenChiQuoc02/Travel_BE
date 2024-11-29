package com.cfctechnology.travel.service;

import com.cfctechnology.travel.model.Category;
import com.cfctechnology.travel.model.PageResult;
import com.cfctechnology.travel.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    private  CategoryRepository categoryRepository;

    public Category getCategoryById(long id) {
        Optional<Category> category = categoryRepository.findById(id);
        return category.orElse(null);
    }

    public PageResult<Category> getPageCategorys(int page, int size, String name){

        Pageable pageable = PageRequest.of(page, size);
        Page<Category> cates;
        if (name != null && !name.isEmpty()) {
            cates = categoryRepository.findByNameContaining(name, pageable);
        } else {
            cates = categoryRepository.findAll(pageable);
        }
        return new PageResult<>(cates.getContent(), cates.getTotalPages());
    }

    public Category createCategory(Category category) {
        Category newCategory = new Category();

        newCategory.setName(category.getName());
        newCategory.setDescription(category.getDescription());
        return categoryRepository.save(newCategory);
    }

    public Category updateCategory(Category category,long id) {
        Optional<Category> categoryOptional = categoryRepository.findById(id);
        if (categoryOptional.isPresent()) {
            categoryOptional.get().setDescription(category.getDescription());
            categoryOptional.get().setName(category.getName());
            return categoryRepository.save(categoryOptional.get());
        }
        return null;
    }

    public void deleteCategory(long id) {
        categoryRepository.deleteById(id);
    }


}
