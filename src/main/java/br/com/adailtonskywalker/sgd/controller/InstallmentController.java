package br.com.adailtonskywalker.sgd.controller;


import br.com.adailtonskywalker.sgd.dto.InstallmentResponseData;
import br.com.adailtonskywalker.sgd.dto.SuccessRequestResponse;
import br.com.adailtonskywalker.sgd.service.InstallmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/installment")
@RequiredArgsConstructor
public class InstallmentController {

    private final InstallmentService service;

    @GetMapping("/{installmentPlanId}")
    public ResponseEntity<SuccessRequestResponse> getByInstallmentPlanId(@PathVariable UUID installmentPlanId) {

        List<InstallmentResponseData> responseData = service.getByInstallmentPlan(installmentPlanId);

        return ResponseEntity.status(HttpStatus.OK).body(
                SuccessRequestResponse
                        .builder()
                        .data(responseData)
                        .status(HttpStatus.OK.value())
                        .build()
        );
    }
}
