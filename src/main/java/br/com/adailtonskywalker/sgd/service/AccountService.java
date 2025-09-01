package br.com.adailtonskywalker.sgd.service;

import br.com.adailtonskywalker.sgd.dto.AccountRequestData;
import br.com.adailtonskywalker.sgd.events.TransactionOnBalanceEvent;
import br.com.adailtonskywalker.sgd.model.Account;
import br.com.adailtonskywalker.sgd.model.Transaction;
import br.com.adailtonskywalker.sgd.model.TransactionType;
import br.com.adailtonskywalker.sgd.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final InstallmentService installmentService;
    private final ApplicationEventPublisher publisher;

    @Transactional
    public Account save(AccountRequestData accountRequestData) {
        Account account = new Account();
        account.setUser(accountRequestData.getUser());
        return accountRepository.save(account);
    }

    @Transactional
    public Account updateBalance(UUID accountUuid) {
        Account account = accountRepository.findById(accountUuid).orElse(null);
        assert account != null;

        List<Transaction> onBalanceTransactions = new ArrayList<>();
        double balance = account.getTransactions().stream()
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
                    onBalanceTransactions.add(transaction);
                    return amount;
                })
                .sum();

        balance = balance + account.getBalance();
        account.setBalance((float) balance);
        accountRepository.save(account);
        onBalanceTransactions.forEach(transaction -> publisher.publishEvent(new TransactionOnBalanceEvent(transaction)));
        return account;
    }
}
