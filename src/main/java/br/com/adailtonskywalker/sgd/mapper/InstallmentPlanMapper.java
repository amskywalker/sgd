package br.com.adailtonskywalker.sgd.mapper;

import br.com.adailtonskywalker.sgd.dto.InstallmentPlanRequestData;
import br.com.adailtonskywalker.sgd.dto.InstallmentPlanResponseData;
import br.com.adailtonskywalker.sgd.interfaces.Mapper;
import br.com.adailtonskywalker.sgd.model.InstallmentPlan;
import org.springframework.stereotype.Component;

@Component
public class InstallmentPlanMapper implements Mapper<InstallmentPlan, InstallmentPlanRequestData, InstallmentPlanResponseData> {
    @Override
    public InstallmentPlan toEntity(InstallmentPlanRequestData inputDto) {
        InstallmentPlan installmentPlan = new InstallmentPlan();
        installmentPlan.setFees(inputDto.getFees());
        installmentPlan.setAmount(inputDto.getAmount());
        installmentPlan.setQuantity(inputDto.getQuantity());
        return installmentPlan;
    }

    @Override
    public InstallmentPlanResponseData toDto(InstallmentPlan entity) {
        return InstallmentPlanResponseData.builder()
                .id(entity.getId())
                .amount(entity.getAmount())
                .fees(entity.getFees())
                .dateCreated(entity.getDateCreated())
                .build();
    }
}
