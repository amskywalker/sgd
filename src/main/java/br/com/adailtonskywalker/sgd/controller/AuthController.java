package br.com.adailtonskywalker.sgd.controller;

import br.com.adailtonskywalker.sgd.dto.LoginRequestData;
import br.com.adailtonskywalker.sgd.dto.LoginResponseData;
import br.com.adailtonskywalker.sgd.service.AuthService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public LoginResponseData login(@RequestBody LoginRequestData loginData) {
        return authService.login(loginData);
    }
}
