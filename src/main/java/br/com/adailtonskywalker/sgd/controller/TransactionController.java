package br.com.adailtonskywalker.sgd.controller;

import br.com.adailtonskywalker.sgd.dto.SuccessRequestResponse;
import br.com.adailtonskywalker.sgd.dto.TransactionRequestData;
import br.com.adailtonskywalker.sgd.dto.TransactionResponseData;
import br.com.adailtonskywalker.sgd.model.User;
import br.com.adailtonskywalker.sgd.service.TransactionService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/transactions")
public class TransactionController {
    private final TransactionService service;

    @Autowired
    public TransactionController(TransactionService service) {
        this.service = service;
    }

    @GetMapping("/")
    public ResponseEntity<SuccessRequestResponse> index(@AuthenticationPrincipal User user) {
        List<TransactionResponseData> responseData = service.index(user.getAccount());

        return ResponseEntity.status(HttpStatus.OK).body(
                SuccessRequestResponse
                        .builder()
                        .data(responseData)
                        .status(HttpStatus.OK.value())
                        .build()
        );
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<SuccessRequestResponse> getById(@PathVariable UUID uuid,
                                                          @AuthenticationPrincipal User user) {
        TransactionResponseData responseData = service.getByUUID(user.getAccount(), uuid);

        return ResponseEntity.status(HttpStatus.OK).body(
                SuccessRequestResponse
                        .builder()
                        .data(responseData)
                        .status(HttpStatus.OK.value())
                        .build()
        );
    }

    @PostMapping("/")
    public ResponseEntity<SuccessRequestResponse> save(@Valid @RequestBody TransactionRequestData requestData,
                                                       @AuthenticationPrincipal User user) {
        TransactionResponseData responseData = service.save(user.getAccount(), requestData);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                SuccessRequestResponse
                        .builder()
                        .data(responseData)
                        .status(HttpStatus.CREATED.value())
                        .build()
        );
    }


}
