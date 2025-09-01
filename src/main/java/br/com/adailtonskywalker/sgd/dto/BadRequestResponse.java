package br.com.adailtonskywalker.sgd.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class BadRequestResponse {
    public String description;
    public List<BadRequestData> errors;
}
