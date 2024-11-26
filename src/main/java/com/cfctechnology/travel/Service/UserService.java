package com.cfctechnology.travel.Service;

import com.cfctechnology.travel.Model.Enum.ERole;
import com.cfctechnology.travel.Model.PageResult;
import com.cfctechnology.travel.Model.User;
import com.cfctechnology.travel.Repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserById(long id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElse(null);
    }

//    public List<User> getAllUsers() {
//        return userRepository.findAll();
//    }

    public PageResult<User> getPageUsers(int page, int size, String name){

        Pageable pageable = PageRequest.of(page, size);
        Page<User> users;
        if (name != null && !name.isEmpty()) {
            users = this.userRepository.findByNameContaining(name, pageable);
        } else {
            users = this.userRepository.findAll(pageable);
        }
        return new PageResult<>(users.getContent(), users.getTotalPages());
    }

    public User createUser(User user) {

        User newUser = new User();
        newUser.setName(user.getName());
        newUser.setPassword(user.getPassword());
        newUser.setEmail(user.getEmail());
        newUser.setRole(ERole.CUSTOMER);
        newUser.setPhoneNumber(user.getPhoneNumber());
        return userRepository.save(newUser);
    }

    public User updateUser(User user,long id) {
        User upUser = this.getUserById(id);
        upUser.setName(user.getName());
        upUser.setPassword(user.getPassword());
        upUser.setEmail(user.getEmail());
        upUser.setRole(ERole.CUSTOMER);
        upUser.setPhoneNumber(user.getPhoneNumber());
        return userRepository.save(upUser);
    }

    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }
}
