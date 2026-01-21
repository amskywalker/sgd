package br.com.adailtonskywalker.sgd.dto;

import br.com.adailtonskywalker.sgd.model.TransactionType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class TransactionResponseData {
    private UUID id;
    private TransactionType type;
    private String description;
    private LocalDateTime date;
    private Float amount;
}
