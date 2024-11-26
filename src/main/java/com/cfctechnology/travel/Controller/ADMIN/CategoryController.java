package com.cfctechnology.travel.Controller.ADMIN;

import com.cfctechnology.travel.Model.Category;
import com.cfctechnology.travel.Model.PageResult;
import com.cfctechnology.travel.Model.ResponseObject;
import com.cfctechnology.travel.Service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("AdminCategory")
@RequestMapping("/admin/category")
public class CategoryController {

    private final CategoryService categoryService;
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getCategory(@PathVariable long id) {
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok","get successfuly", this.categoryService.getCategoryById(id)));
    }

    @GetMapping("")
    public ResponseEntity<ResponseObject> getAllCategorys(@RequestParam(value ="page",defaultValue = "0") int page,
                                                          @RequestParam(value = "size" ,defaultValue = "10") int size,
                                                          @RequestParam(value = "name", required = false) String name) {
        PageResult<Category> category = this.categoryService.getPageCategorys(page, size, name);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok","get all successfuly", category));
    }

    @PostMapping
    public ResponseEntity<ResponseObject> addCategory(@RequestBody Category category) {
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseObject("ok","add successfuly", this.categoryService.createCategory(category)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseObject> updateCategory(@PathVariable long id, @RequestBody Category category) {
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok","update successfuly", this.categoryService.updateCategory(category,id)));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> deleteCategory(@PathVariable long id) {
        this.categoryService.deleteCategory(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok","get successfuly",null));

    }
}
