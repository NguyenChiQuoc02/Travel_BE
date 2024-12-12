package com.cfctechnology.travel.controller.home;

import com.cfctechnology.travel.model.Category;
import com.cfctechnology.travel.model.PageResult;
import com.cfctechnology.travel.model.ResponseObject;
import com.cfctechnology.travel.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//@CrossOrigin("*")
@CrossOrigin(origins = {"https://sound-honestly-bird.ngrok-free.app", "*"})

@RestController("HomeCategory")
@RequestMapping("home/category")
public class CategoryController {
    @Autowired
    private  CategoryService categoryService;

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getCategory(@PathVariable long id) {
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok","get successfuly", categoryService.getCategoryById(id)));
    }

    @GetMapping("")
    public ResponseEntity<ResponseObject> getAllCategorys(@RequestParam(value ="page",defaultValue = "0") int page,
                                                          @RequestParam(value = "size" ,defaultValue = "10") int size,
                                                          @RequestParam(value = "name", required = false) String name) {
        PageResult<Category> category = categoryService.getPageCategorys(page, size, name);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok","get all successfuly", category));
    }

}
