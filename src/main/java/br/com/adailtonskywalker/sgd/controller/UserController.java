package br.com.adailtonskywalker.sgd.controller;

import br.com.adailtonskywalker.sgd.dto.MeRequestData;
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
    public UserResponseData me(@Valid @RequestBody MeRequestData meRequestData) {
        return userService.findByToken(meRequestData);
    }
}
