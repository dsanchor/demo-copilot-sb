package com.microsoft.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import com.microsoft.demo.model.User;
import com.microsoft.demo.repository.UserRepository;
import com.microsoft.demo.utils.Utils;


@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody User user) {
        if (!Utils.validateUser(user)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid user data");
        }
        return ResponseEntity.ok(userRepository.save(user));
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/by-email")
    public Optional<User> getUserByEmail(@RequestParam String email) {
        return userRepository.findByEmail(email);
    }    

    @GetMapping("/average-age")
    public double getAverageAge() {
        return calculateAverageAge(userRepository.findAll());
    }

    // create a private methof to calculate the average age of all users
    private double calculateAverageAge(List<User> users) {
        return users.stream()
                    .mapToInt(User::getAge)
                    .average()
                    .orElse(0.0);
    }

}

