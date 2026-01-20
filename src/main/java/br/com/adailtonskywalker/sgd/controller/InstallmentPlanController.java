package br.com.adailtonskywalker.sgd.controller;


import br.com.adailtonskywalker.sgd.dto.*;
import br.com.adailtonskywalker.sgd.model.User;
import br.com.adailtonskywalker.sgd.service.InstallmentPlanService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/installment-plan")
@RequiredArgsConstructor
public class InstallmentPlanController {

    private final InstallmentPlanService service;

    @PostMapping("/{transactionId}/create")
    public ResponseEntity<SuccessRequestResponse> save(@PathVariable UUID transactionId,
                                                       @Valid @RequestBody InstallmentPlanRequestData requestData,
                                                       @AuthenticationPrincipal User user) {

        InstallmentPlanResponseData responseData = service.save(user.getAccount(), transactionId, requestData);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                SuccessRequestResponse
                        .builder()
                        .data(responseData)
                        .status(HttpStatus.CREATED.value())
                        .build()
        );
    }

    @GetMapping("/{transactionId}")
    public ResponseEntity<SuccessRequestResponse> getByTransactionId(@PathVariable UUID transactionId,
                                                       @AuthenticationPrincipal User user) {

        InstallmentPlanResponseData responseData = service.getByTransactionID(user.getAccount(), transactionId);

        return ResponseEntity.status(HttpStatus.OK).body(
                SuccessRequestResponse
                        .builder()
                        .data(responseData)
                        .status(HttpStatus.OK.value())
                        .build()
        );
    }
}
