package br.com.adailtonskywalker.sgd.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BadRequestData {
    public String field;
    public String message;
}
