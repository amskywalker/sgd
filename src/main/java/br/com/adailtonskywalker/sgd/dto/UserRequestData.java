package br.com.adailtonskywalker.sgd.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRequestData {
    @NotNull
    @Size(min = 1, max = 50)
    private String name;
    @NotNull
    @Size(min = 1, max = 15)
    private String username;
    @NotNull
    @Size(min = 8, max = 15)
    private String password;

}
