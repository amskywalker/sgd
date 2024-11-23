package br.com.adailtonskywalker.sgd.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class AccountResponseData {
    private UUID id;
    private Float balance;
    private Boolean active;
    private LocalDateTime dateCreated;
}
