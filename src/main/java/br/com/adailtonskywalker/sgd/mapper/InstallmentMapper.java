package br.com.adailtonskywalker.sgd.mapper;

import br.com.adailtonskywalker.sgd.dto.InstallmentRequestData;
import br.com.adailtonskywalker.sgd.dto.InstallmentResponseData;
import br.com.adailtonskywalker.sgd.interfaces.Mapper;
import br.com.adailtonskywalker.sgd.model.Installment;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class InstallmentMapper implements Mapper<Installment, InstallmentRequestData, InstallmentResponseData> {
    @Override
    public Installment toEntity(InstallmentRequestData inputDto) {
        Installment installment = new Installment();
        installment.setNumber(inputDto.getNumber());
        installment.setAmount(inputDto.getAmount());
        installment.setDate(installment.getDateCreated());
        return installment;
    }

    public List<InstallmentResponseData> toDtoList(List<Installment> dtoList) {
        if (dtoList == null) {
            return List.of();
        }

        return dtoList.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public InstallmentResponseData toDto(Installment entity) {
        return InstallmentResponseData.builder()
                .id(entity.getId())
                .amount(entity.getAmount())
                .date(entity.getDate())
                .dateCreated(entity.getDateCreated())
                .number(entity.getNumber())
                .build();
    }
}
