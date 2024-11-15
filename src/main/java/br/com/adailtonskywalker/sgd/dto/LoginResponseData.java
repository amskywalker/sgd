package br.com.adailtonskywalker.sgd.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class LoginResponseData {
    private String token;
    private String tokenType;
    private String timeExpiration;
}
