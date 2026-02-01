package br.com.adailtonskywalker.sgd.service;

import br.com.adailtonskywalker.sgd.config.CurrentUserProvider;
import br.com.adailtonskywalker.sgd.config.JpaUserLoader;
import br.com.adailtonskywalker.sgd.dto.AccountRequestData;
import br.com.adailtonskywalker.sgd.dto.AccountResponseData;
import br.com.adailtonskywalker.sgd.events.TransactionOnBalanceEvent;
import br.com.adailtonskywalker.sgd.exception.EntityNotFoundException;
import br.com.adailtonskywalker.sgd.mapper.AccountMapper;
import br.com.adailtonskywalker.sgd.model.Account;
import br.com.adailtonskywalker.sgd.model.TransactionType;
import br.com.adailtonskywalker.sgd.model.User;
import br.com.adailtonskywalker.sgd.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final InstallmentService installmentService;
    private final ApplicationEventPublisher publisher;
    private final CurrentUserProvider userProvider;
    private final JpaUserLoader userLoader;

    public Account findById(UUID uuid) {
        return accountRepository
                .findById(uuid)
                .orElseThrow(() -> new EntityNotFoundException("Account"));
    }

    @Transactional
    public AccountResponseData save() {
        Long userId = userProvider.getUserId();
        User user = userLoader.loadById(userId);

        Account account = new Account();
        account.addUser(user);

        Account savedAccount = accountRepository.save(account);
        return accountMapper.toDto(savedAccount);
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

    public List<AccountResponseData> getAccountsFromUser(Long userId){
        List<Account> accounts = accountRepository.findByUsersId(userId);
        return accountMapper.toDtoList(accounts);
    }

    public boolean userHasAccount(UUID acountUuid, Long userId) {
        return accountRepository.userHasAccess(acountUuid, userId);
    }
}
