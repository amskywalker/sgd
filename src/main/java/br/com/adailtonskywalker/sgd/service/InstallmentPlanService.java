package br.com.adailtonskywalker.sgd.service;

import br.com.adailtonskywalker.sgd.dto.InstallmentPlanRequestData;
import br.com.adailtonskywalker.sgd.model.InstallmentPlan;
import br.com.adailtonskywalker.sgd.repository.InstallmentPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InstallmentPlanService {
    private final InstallmentPlanRepository installmentPlanRepository;
    private final InstallmentService installmentService;

    @Autowired
    public InstallmentPlanService(InstallmentPlanRepository installmentPlanRepository, InstallmentService installmentService) {
        this.installmentPlanRepository = installmentPlanRepository;
        this.installmentService = installmentService;
    }

    @Transactional
    public InstallmentPlan save(InstallmentPlanRequestData installmentPlanRequestData) {
        InstallmentPlan installmentPlan = new InstallmentPlan();
        installmentPlan.setFees(installmentPlanRequestData.getFees());
        installmentPlan.setAmount(installmentPlanRequestData.getValue());
        installmentPlan.setQuantity(installmentPlan.getQuantity());
        installmentPlan.setTransaction(installmentPlanRequestData.getTransaction());
        installmentService.createByInstallmentPlan(installmentPlan);
        return installmentPlanRepository.save(installmentPlan);
    }
}
