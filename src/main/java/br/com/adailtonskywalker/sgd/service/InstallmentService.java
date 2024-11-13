package br.com.adailtonskywalker.sgd.service;

import br.com.adailtonskywalker.sgd.dto.InstallmentRequestData;
import br.com.adailtonskywalker.sgd.model.Installment;
import br.com.adailtonskywalker.sgd.model.InstallmentPlan;
import br.com.adailtonskywalker.sgd.repository.InstallmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class InstallmentService {
    private final InstallmentRepository installmentRepository;

    @Autowired
    public InstallmentService(InstallmentRepository installmentRepository) {
        this.installmentRepository = installmentRepository;
    }

    @Transactional
    public Installment save(InstallmentRequestData installmentRequestData) {
        Installment installmentPlan = new Installment();
        installmentPlan.setInstallmentPlan(installmentRequestData.getInstallmentPlan());
        installmentPlan.setAmount(installmentRequestData.getAmount());
        installmentPlan.setDate(installmentRequestData.getDate());
        installmentPlan.setNumber(installmentRequestData.getNumber());
        return installmentRepository.save(installmentPlan);
    }

    @Transactional
    public void createByInstallmentPlan(InstallmentPlan installmentPlan) {
        LocalDateTime localDateTime = LocalDateTime.now();
        for (int i = 1; i <= installmentPlan.getQuantity(); i++) {
            save(InstallmentRequestData.builder()
                    .installmentPlan(installmentPlan)
                    .number(i)
                    .date(localDateTime.plusMonths(i))
                    .amount(calculateInstallmentValue(installmentPlan))
                    .build()
            );
        }
    }

    @Transactional
    public float calculateInstallmentValue(InstallmentPlan installmentPlan) {
        return installmentPlan.getValue() / installmentPlan.getQuantity();
    }

    @Transactional
    public Installment getCurrentInstallment(InstallmentPlan installmentPlan) {
        return installmentRepository.findFirstByInstallmentPlanAndMonthAndYear(
                installmentPlan,
                LocalDateTime.now().getMonthValue(),
                LocalDateTime.now().getYear()
        );
    }

}
