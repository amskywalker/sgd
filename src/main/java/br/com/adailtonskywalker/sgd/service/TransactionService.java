package br.com.adailtonskywalker.sgd.service;

import br.com.adailtonskywalker.sgd.dto.TransactionRequestData;
import br.com.adailtonskywalker.sgd.dto.TransactionResponseData;
import br.com.adailtonskywalker.sgd.mapper.TransactionMapper;
import br.com.adailtonskywalker.sgd.model.Account;
import br.com.adailtonskywalker.sgd.model.Transaction;
import br.com.adailtonskywalker.sgd.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final AccountService accountService;
    private final TransactionMapper transactionMapper;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository, AccountService accountService, TransactionMapper transactionMapper) {
        this.transactionRepository = transactionRepository;
        this.accountService = accountService;
        this.transactionMapper = transactionMapper;
    }

    @Transactional
    public TransactionResponseData save(TransactionRequestData transactionRequestData) {
        Transaction transaction = transactionMapper.toEntity(transactionRequestData);
        Transaction savedTransaction =  transactionRepository.save(transaction);
        Account accountWithUpdatedBalance = accountService.updateBalance(UUID.fromString(transactionRequestData.getAccountUuid()));
        savedTransaction.setAccount(accountWithUpdatedBalance);
        return transactionMapper.toDto(savedTransaction);
    }
}
