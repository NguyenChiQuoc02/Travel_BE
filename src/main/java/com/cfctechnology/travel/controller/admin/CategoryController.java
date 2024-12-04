package com.cfctechnology.travel.controller.admin;

import com.cfctechnology.travel.model.Category;
import com.cfctechnology.travel.model.PageResult;
import com.cfctechnology.travel.model.ResponseObject;
import com.cfctechnology.travel.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController("AdminCategory")
@RequestMapping("/admin/category")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class CategoryController {

    @Autowired
    private  CategoryService categoryService;

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getCategory(@PathVariable long id) {
        if(categoryService.getCategoryById(id) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok","get successfuly", categoryService.getCategoryById(id)));
    }

    @GetMapping("")
    public ResponseEntity<ResponseObject> getAllCategorys(@RequestParam(value ="page",defaultValue = "0") int page,
                                                          @RequestParam(value = "size" ,defaultValue = "10") int size,
                                                          @RequestParam(value = "name", required = false) String name) {
        PageResult<Category> category = categoryService.getPageCategorys(page, size, name);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok","get all successfuly", category));
    }

    @PostMapping
    public ResponseEntity<ResponseObject> addCategory(@Validated @RequestBody Category category) {
        if(category.getName().isEmpty() || category.getDescription().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseObject("ok","add successfuly", categoryService.createCategory(category)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseObject> updateCategory(@PathVariable long id, @RequestBody Category category) {
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok","update successfuly",categoryService.updateCategory(category,id)));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> deleteCategory(@PathVariable long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok","get successfuly",null));

    }
}
