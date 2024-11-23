package br.com.adailtonskywalker.sgd.mapper;

import br.com.adailtonskywalker.sgd.dto.TransactionRequestData;
import br.com.adailtonskywalker.sgd.dto.TransactionResponseData;
import br.com.adailtonskywalker.sgd.interfaces.Mapper;
import br.com.adailtonskywalker.sgd.model.Account;
import br.com.adailtonskywalker.sgd.model.Transaction;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class TransactionMapper implements Mapper<Transaction, TransactionRequestData, TransactionResponseData> {

    private final AccountMapper accountMapper;

    public TransactionMapper(AccountMapper accountMapper) {
        this.accountMapper = accountMapper;
    }

    @Override
    public Transaction toEntity(TransactionRequestData inputDto) {
        Transaction transaction = new Transaction();
        transaction.setAmount(inputDto.getAmount());
        transaction.setDescription(inputDto.getDescription());
        transaction.setType(inputDto.getType());
        transaction.setDate(inputDto.getDate());

        Account account = new Account();
        account.setId(UUID.fromString(inputDto.getAccountUuid()));
        transaction.setAccount(account);

        return transaction;
    }

    @Override
    public TransactionResponseData toDto(Transaction entity) {
        return TransactionResponseData.builder()
                .id(entity.getId())
                .amount(entity.getAmount())
                .description(entity.getDescription())
                .type(entity.getType())
                .date(entity.getDate())
                .account(accountMapper.toDto(entity.getAccount()))
                .build();
    }
}
