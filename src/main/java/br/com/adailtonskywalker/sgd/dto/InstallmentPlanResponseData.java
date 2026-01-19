package br.com.adailtonskywalker.sgd.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class InstallmentPlanResponseData {
    @NotNull
    private UUID id;

    @NotNull
    private Integer quantity;

    @NotNull
    private Float amount;

    private Float fees;
    private LocalDateTime dateCreated;
}
