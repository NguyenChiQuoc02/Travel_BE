package com.cfctechnology.travel.Controller.ADMIN;

import com.cfctechnology.travel.Model.PageResult;
import com.cfctechnology.travel.Model.ResponseObject;
import com.cfctechnology.travel.Model.User;
import com.cfctechnology.travel.Service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("AdminUser")
@RequestMapping("/admin/user")
public class UserController {

    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }



    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getUserById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "get user successfully", this.userService.getUserById(id)));
    }

    @GetMapping("")
    public ResponseEntity<ResponseObject> getAllUsers(@RequestParam(value ="page",defaultValue = "0") int page,
                                                      @RequestParam(value = "size" ,defaultValue = "10") int size,
                                                      @RequestParam(value = "name", required = false) String name) {
        PageResult<User> users = this.userService.getPageUsers(page, size, name);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "get all successfully", users));
    }
    @PostMapping("")
    public ResponseEntity<ResponseObject> createUser(@RequestBody User user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseObject("ok", "add successfully", this.userService.createUser(user)));

    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseObject> updateUser(@PathVariable Long id, @RequestBody User user) {
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "update successfully", this.userService.updateUser(user,id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> deleteUser(@PathVariable Long id) {
        this.userService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "delete successfully", null));
    }
}
