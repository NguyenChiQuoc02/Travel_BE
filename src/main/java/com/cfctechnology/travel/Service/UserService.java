package com.cfctechnology.travel.Service;

import com.cfctechnology.travel.Model.Enum.ERole;
import com.cfctechnology.travel.Model.User;
import com.cfctechnology.travel.Repository.UserRepository;
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

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    public User createUser(User user) {

        User newUser = new User();
        newUser.setUsername(user.getUsername());
        newUser.setPassword(user.getPassword());
        newUser.setEmail(user.getEmail());
        newUser.setRole(ERole.CUSTOMER);
        newUser.setPhoneNumber(user.getPhoneNumber());
        return userRepository.save(newUser);
    }

    public User updateUser(User user,long id) {
        User upUser = this.getUserById(id);
        upUser.setUsername(user.getUsername());
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
