package br.com.adailtonskywalker.sgd.dto;

import br.com.adailtonskywalker.sgd.model.Transaction;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InstallmentPlanRequestData {
    @NotNull
    private Integer quantity;
    @NotNull
    private Float value;
    @NotNull
    private Transaction transaction;
    private Float fees;
}
