package br.com.adailtonskywalker.sgd.controller;

import br.com.adailtonskywalker.sgd.dto.SuccessRequestResponse;
import br.com.adailtonskywalker.sgd.dto.TransactionRequestData;
import br.com.adailtonskywalker.sgd.dto.TransactionResponseData;
import br.com.adailtonskywalker.sgd.service.TransactionService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/transactions")
public class TransactionController {
    private final TransactionService service;

    @Autowired
    public TransactionController(TransactionService service) {
        this.service = service;
    }

    @PostMapping("/")
    public ResponseEntity<SuccessRequestResponse> save(@Valid @RequestBody TransactionRequestData requestData) {
        TransactionResponseData responseData = service.save(requestData);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                SuccessRequestResponse
                        .builder()
                        .data(responseData)
                        .status(HttpStatus.CREATED.value())
                        .build()
        );
    }
}
