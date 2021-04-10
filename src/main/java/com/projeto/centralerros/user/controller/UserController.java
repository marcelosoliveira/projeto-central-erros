package com.projeto.centralerros.user.controller;

import com.projeto.centralerros.user.model.User;
import com.projeto.centralerros.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(name = "/")
@AllArgsConstructor
public class UserController {

    private UserRepository userRepository;

    @GetMapping("/login")
    public List<User> userList() {
        return this.userRepository.findAll();
    }

    @PostMapping("/login")
    public User create(@RequestBody User user) {
        return this.userRepository.save(user);
    }

}
