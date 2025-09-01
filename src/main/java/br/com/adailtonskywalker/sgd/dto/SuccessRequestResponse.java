package br.com.adailtonskywalker.sgd.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SuccessRequestResponse {
    public Integer status;
    public Object data;
}
