package br.com.adailtonskywalker.sgd.controller;

import br.com.adailtonskywalker.sgd.dto.SuccessRequestResponse;
import br.com.adailtonskywalker.sgd.dto.TransactionRequestData;
import br.com.adailtonskywalker.sgd.dto.TransactionResponseData;
import br.com.adailtonskywalker.sgd.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService service;

    @GetMapping
    public ResponseEntity<SuccessRequestResponse> index() {

        List<TransactionResponseData> responseData = service.index();

        return ResponseEntity.ok(
                SuccessRequestResponse.builder()
                        .data(responseData)
                        .status(HttpStatus.OK.value())
                        .build()
        );
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<SuccessRequestResponse> getById(@PathVariable UUID uuid) {

        TransactionResponseData responseData = service.getByUUID(uuid);

        return ResponseEntity.ok(
                SuccessRequestResponse.builder()
                        .data(responseData)
                        .status(HttpStatus.OK.value())
                        .build()
        );
    }

    @PostMapping
    public ResponseEntity<SuccessRequestResponse> save(
            @Valid @RequestBody TransactionRequestData requestData) {

        TransactionResponseData responseData = service.save(requestData);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                SuccessRequestResponse.builder()
                        .data(responseData)
                        .status(HttpStatus.CREATED.value())
                        .build()
        );
    }
}
