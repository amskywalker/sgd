package br.com.adailtonskywalker.sgd.dto;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginRequestData {
    private String username;
    private String password;
}
