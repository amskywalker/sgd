package br.com.adailtonskywalker.sgd.dto;

import br.com.adailtonskywalker.sgd.model.InstallmentPlan;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class InstallmentRequestData {
    @NotNull
    private Integer number;
    @NotNull
    private Float amount;
    @NotNull
    private InstallmentPlan installmentPlan;
    @NotNull
    private LocalDateTime date;
}
