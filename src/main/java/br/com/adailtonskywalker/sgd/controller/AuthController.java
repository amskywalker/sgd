package br.com.adailtonskywalker.sgd.controller;

import br.com.adailtonskywalker.sgd.dto.LoginRequestData;
import br.com.adailtonskywalker.sgd.dto.LoginResponseData;
import br.com.adailtonskywalker.sgd.service.AuthService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/login")
    public LoginResponseData login(LoginRequestData loginData) {
        return authService.login(loginData);
    }
}
