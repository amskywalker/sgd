package br.com.adailtonskywalker.sgd.controller;

import br.com.adailtonskywalker.sgd.dto.UserRequestData;
import br.com.adailtonskywalker.sgd.model.User;
import br.com.adailtonskywalker.sgd.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/")
    public User save(@Valid @RequestBody UserRequestData userData) {
        return userService.save(userData);
    }
}
