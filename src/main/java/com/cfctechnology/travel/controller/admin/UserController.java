package com.cfctechnology.travel.controller.admin;

import com.cfctechnology.travel.model.PageResult;
import com.cfctechnology.travel.model.ResponseObject;
import com.cfctechnology.travel.model.User;
import com.cfctechnology.travel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("AdminUser")
@RequestMapping("/admin/user")
public class UserController {

    @Autowired
    private  UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getUserById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "get user successfully", userService.getUserById(id)));
    }

    @GetMapping("")
    public ResponseEntity<ResponseObject> getAllUsers(@RequestParam(value ="page",defaultValue = "0") int page,
                                                      @RequestParam(value = "size" ,defaultValue = "10") int size,
                                                      @RequestParam(value = "name", required = false) String name) {
        PageResult<User> users = userService.getPageUsers(page, size, name);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "get all successfully", users));
    }
    @PostMapping("")
    public ResponseEntity<ResponseObject> createUser(@RequestBody User user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseObject("ok", "add successfully", userService.createUser(user)));

    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseObject> updateUser(@PathVariable Long id, @RequestBody User user) {
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "update successfully", userService.updateUser(user,id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "delete successfully", null));
    }
}
