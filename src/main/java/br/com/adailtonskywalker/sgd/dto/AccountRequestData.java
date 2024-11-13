package br.com.adailtonskywalker.sgd.dto;

import br.com.adailtonskywalker.sgd.model.User;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountRequestData {
    @NotNull
    private User user;
}
