package com.cfctechnology.travel.service;

import com.cfctechnology.travel.model.PageResult;
import com.cfctechnology.travel.model.User;
import com.cfctechnology.travel.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User getUserById(long id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElse(null);
    }
    public User getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByName(username)
                .orElseThrow(() -> new IllegalArgumentException("User không tồn tại."));

        return user;
    }

    public PageResult<User> getPageUsers(int page, int size, String name){

        Pageable pageable = PageRequest.of(page, size);
        Page<User> users;
        if (name != null && !name.isEmpty()) {
            users =  userRepository.findByNameContaining(name, pageable);
        } else {
            users =  userRepository.findAll(pageable);
        }
        return new PageResult<>(users.getContent(), users.getTotalPages());
    }

    public User createUser(User user) {

        User newUser = new User();
        newUser.setName(user.getName());
        newUser.setPassword(user.getPassword());
        newUser.setEmail(user.getEmail());
//        newUser.setRole(ERole);
        newUser.setPhoneNumber(user.getPhoneNumber());
        return userRepository.save(newUser);
    }

    public User updateUser(User user,long id) {
        User upUser = this.getUserById(id);
        upUser.setName(user.getName());
        upUser.setPassword(user.getPassword());
        upUser.setEmail(user.getEmail());
//        upUser.setRole(ERole.CUSTOMER);
        upUser.setPhoneNumber(user.getPhoneNumber());
        return userRepository.save(upUser);
    }

    public void deleteUser(long id) {
        Optional<User>   user = userRepository.findById(id);
        if(user.isPresent()) {
            user.get().setDeleted(true);
            userRepository.save(user.get());
        }
    }
}
