package com.cfctechnology.travel.controller.customer;

import com.cfctechnology.travel.model.ResponseObject;
import com.cfctechnology.travel.model.User;
import com.cfctechnology.travel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
@CrossOrigin("*")
@RestController("CustomerUser")
@RequestMapping("/customer/user")
@PreAuthorize("hasRole('ROLE_CUSTOMER')")
public class UserController {

    @Autowired
    private  UserService userService;

    @GetMapping("/current-user")
    public ResponseEntity<ResponseObject> getUserById() {
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "get user successfully", userService.getCurrentUser()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseObject> updateUser(@PathVariable Long id, @RequestBody User user) {
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "update successfully", userService.updateUser(user,id)));
    }
}
