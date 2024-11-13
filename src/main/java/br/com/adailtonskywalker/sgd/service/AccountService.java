package br.com.adailtonskywalker.sgd.service;

import br.com.adailtonskywalker.sgd.dto.AccountRequestData;
import br.com.adailtonskywalker.sgd.model.Account;
import br.com.adailtonskywalker.sgd.model.TransactionType;
import br.com.adailtonskywalker.sgd.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public void updateBalance(Account account) {
        double balance = account.getTransactions().stream()
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
        accountRepository.save(account);
    }
}
