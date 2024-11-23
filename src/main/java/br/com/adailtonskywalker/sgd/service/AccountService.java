package br.com.adailtonskywalker.sgd.service;

import br.com.adailtonskywalker.sgd.dto.AccountRequestData;
import br.com.adailtonskywalker.sgd.model.Account;
import br.com.adailtonskywalker.sgd.model.TransactionType;
import br.com.adailtonskywalker.sgd.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final InstallmentService installmentService;

    @Autowired
    public AccountService(AccountRepository accountRepository, InstallmentService installmentService) {
        this.accountRepository = accountRepository;
        this.installmentService = installmentService;
    }

    @Transactional
    public Account save(AccountRequestData accountRequestData) {
        Account account = new Account();
        account.setUser(accountRequestData.getUser());
        return accountRepository.save(account);
    }

//  @TODO create AccountNotFoundException
    @Transactional
    public Account updateBalance(UUID accountUuid) {
        Account account = accountRepository.findById(accountUuid).orElse(null);
        assert account != null;
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
                    return amount;
                })
                .sum();

        account.setBalance((float) balance);
        return accountRepository.save(account);
    }
}
