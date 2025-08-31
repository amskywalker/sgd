package br.com.adailtonskywalker.sgd.dto;

import br.com.adailtonskywalker.sgd.model.TransactionType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class TransactionRequestData {
    @NotNull
    private TransactionType type;
    @NotNull
    @Size(min = 1, max = 100)
    private String description;
    @NotNull
    private LocalDateTime date;
    @NotNull
    private Float amount;
    @NotNull
    private String accountUuid;

    private Boolean onBalance;
}
