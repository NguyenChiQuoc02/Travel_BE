package com.cfctechnology.travel.Controller.HOME;

import com.cfctechnology.travel.Model.Category;
import com.cfctechnology.travel.Model.PageResult;
import com.cfctechnology.travel.Model.ResponseObject;
import com.cfctechnology.travel.Service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("HomeCategory")
@RequestMapping("home/category")
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

}
