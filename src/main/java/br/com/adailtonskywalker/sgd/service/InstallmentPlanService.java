package br.com.adailtonskywalker.sgd.service;

import br.com.adailtonskywalker.sgd.config.security.AccountContext;
import br.com.adailtonskywalker.sgd.dto.InstallmentPlanRequestData;
import br.com.adailtonskywalker.sgd.dto.InstallmentPlanResponseData;
import br.com.adailtonskywalker.sgd.events.InstallmentPlanCreatedEvent;
import br.com.adailtonskywalker.sgd.exception.EntityNotFoundException;
import br.com.adailtonskywalker.sgd.mapper.InstallmentPlanMapper;
import br.com.adailtonskywalker.sgd.model.InstallmentPlan;
import br.com.adailtonskywalker.sgd.model.Transaction;
import br.com.adailtonskywalker.sgd.repository.InstallmentPlanRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Log
public class InstallmentPlanService {
    private final InstallmentPlanRepository installmentPlanRepository;
    private final TransactionService transactionService;
    private final InstallmentPlanMapper installmentPlanMapper;
    private final ApplicationEventPublisher publisher;

    @Transactional
    public InstallmentPlanResponseData save(UUID transactionId, InstallmentPlanRequestData installmentPlanRequestData) {
        Transaction transaction = transactionService.getOwnedTransaction(transactionId);

        InstallmentPlan installmentPlan = installmentPlanMapper.toEntity(installmentPlanRequestData);
        installmentPlan.setTransaction(transaction);

        InstallmentPlan savedInstallmentPlan = installmentPlanRepository.save(installmentPlan);
        publisher.publishEvent(new InstallmentPlanCreatedEvent(savedInstallmentPlan));
        return installmentPlanMapper.toDto(savedInstallmentPlan);
    }


    @Transactional
    public InstallmentPlanResponseData getByTransactionID(UUID transactionId) {
        Transaction transaction = transactionService.getOwnedTransaction(transactionId);
        InstallmentPlan savedInstallmentPlan = installmentPlanRepository.findByTransactionId(transaction.getId());
        return installmentPlanMapper.toDto(savedInstallmentPlan);
    }

    @Transactional
    public InstallmentPlan getById(UUID installmentPlanId) {
        UUID accountId = AccountContext.get();

        return installmentPlanRepository.findByIdAndTransaction_Account_Id(installmentPlanId, accountId)
                .orElseThrow(() -> new EntityNotFoundException("InstallmentPlan"));
    }
}
