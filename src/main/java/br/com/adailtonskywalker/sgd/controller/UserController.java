package br.com.adailtonskywalker.sgd.controller;

import br.com.adailtonskywalker.sgd.dto.SuccessRequestResponse;
import br.com.adailtonskywalker.sgd.dto.UserRequestData;
import br.com.adailtonskywalker.sgd.dto.UserResponseData;
import br.com.adailtonskywalker.sgd.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/")
    public ResponseEntity<SuccessRequestResponse> save(@Valid @RequestBody UserRequestData requestData) {
        UserResponseData responseData = userService.save(requestData);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                SuccessRequestResponse.builder()
                        .status(HttpStatus.OK.value())
                        .data(responseData)
                        .build()
        );
    }


    @GetMapping("/me")
    public ResponseEntity<SuccessRequestResponse> me(@RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Token inválido ou ausente");
        }

        String token = authHeader.substring(7);

        UserResponseData responseData = userService.findByToken(token);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                SuccessRequestResponse.builder()
                        .status(HttpStatus.OK.value())
                        .data(responseData)
                        .build()
        );
    }
}
