package br.com.adailtonskywalker.sgd.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class UserResponseData {
    private Long id;
    private String name;
    private String username;
    private LocalDateTime dateCreated;
    private AccountResponseData account;
}
