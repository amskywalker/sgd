package br.com.adailtonskywalker.sgd.service;

import br.com.adailtonskywalker.sgd.dto.AccountRequestData;
import br.com.adailtonskywalker.sgd.events.TransactionOnBalanceEvent;
import br.com.adailtonskywalker.sgd.exception.EntityNotFoundException;
import br.com.adailtonskywalker.sgd.model.Account;
import br.com.adailtonskywalker.sgd.model.TransactionType;
import br.com.adailtonskywalker.sgd.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final InstallmentService installmentService;
    private final ApplicationEventPublisher publisher;

    public Account findById(UUID uuid) {
        return accountRepository
                .findById(uuid)
                .orElseThrow(() -> new EntityNotFoundException("Account"));
    }


    @Transactional
    public Account save(AccountRequestData accountRequestData) {
        Account account = new Account();
        account.setUser(accountRequestData.getUser());
        return accountRepository.save(account);
    }

    @Transactional
    public Account updateBalance(UUID accountUuid) {
        try {
            log.info("uuid {}", accountUuid);
            Account account = findById(accountUuid);
            account.setBalance((float) calculateBalance(account));
            accountRepository.save(account);
            publishTransactionOnBalance(account);
            return account;
        } catch (Exception ec){
            log.info("{}", ec.getMessage());
            return null;
        }
    }

    public double calculateBalance(Account account) {
        return account.getTransactions().stream()
                .filter(transaction -> transaction.getOnBalance() == false)
                .mapToDouble(transaction -> {
                    double amount;
                    if (transaction.getInstallmentPlan() == null) {
                        amount = transaction.getType() == TransactionType.INPUT
                                ? +transaction.getAmount()
                                : -transaction.getAmount();

                    } else {
                        amount =- installmentService.getCurrentInstallment(transaction.getInstallmentPlan()).getAmount();
                    }
                    return amount;
                })
                .sum() + account.getBalance();
    }

    public void publishTransactionOnBalance(Account account) {
        account.getTransactions().stream()
                .filter(transaction -> transaction.getOnBalance() == false)
                .forEach(transaction -> publisher.publishEvent(new TransactionOnBalanceEvent(transaction)));
    }

    public boolean existsById(UUID id) {
        if(id == null){
            throw new EntityNotFoundException("Account");
        }
        return accountRepository.existsById(id);
    }
}
