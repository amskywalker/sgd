package br.com.adailtonskywalker.sgd.controller;

import br.com.adailtonskywalker.sgd.dto.*;
import br.com.adailtonskywalker.sgd.model.User;
import br.com.adailtonskywalker.sgd.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @GetMapping("/")
    public ResponseEntity<SuccessRequestResponse> me(@AuthenticationPrincipal User user) {
        List<AccountResponseData> accounts = accountService.getAccountsFromUser(user.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(
                SuccessRequestResponse.builder()
                        .status(HttpStatus.OK.value())
                        .data(accounts)
                        .build()
        );
    }

    @PostMapping("/")
    public ResponseEntity<SuccessRequestResponse> create() {
        AccountResponseData account = accountService.save();

        return ResponseEntity.status(HttpStatus.CREATED).body(
                SuccessRequestResponse.builder()
                        .status(HttpStatus.OK.value())
                        .data(account)
                        .build()
        );
    }
}
