package br.com.adailtonskywalker.sgd.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class InstallmentResponseData {
    @NotNull
    private UUID id;
    @NotNull
    private Integer number;
    @NotNull
    private Float amount;
    @NotNull
    private LocalDateTime date;

    private LocalDateTime dateCreated;
}
