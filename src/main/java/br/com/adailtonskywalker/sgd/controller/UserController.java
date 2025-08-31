package br.com.adailtonskywalker.sgd.controller;

import br.com.adailtonskywalker.sgd.dto.UserRequestData;
import br.com.adailtonskywalker.sgd.dto.UserResponseData;
import br.com.adailtonskywalker.sgd.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/")
    public UserResponseData save(@Valid @RequestBody UserRequestData userData) {
        return userService.save(userData);
    }


    @GetMapping("/me")
    public UserResponseData me(@RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Token inválido ou ausente");
        }

        String token = authHeader.substring(7);
        return userService.findByToken(token);
    }
}
