package com.cfctechnology.travel.controller.customer;

import com.cfctechnology.travel.model.ResponseObject;
import com.cfctechnology.travel.model.User;
import com.cfctechnology.travel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("CustomerUser")
@RequestMapping("/customer/user")
public class UserController {

    @Autowired
    private  UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getUserById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "get user successfully", userService.getUserById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseObject> updateUser(@PathVariable Long id, @RequestBody User user) {
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "update successfully", userService.updateUser(user,id)));
    }
}
