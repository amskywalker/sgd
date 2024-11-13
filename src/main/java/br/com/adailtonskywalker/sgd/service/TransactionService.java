package br.com.adailtonskywalker.sgd.service;

import br.com.adailtonskywalker.sgd.dto.TransactionRequestData;
import br.com.adailtonskywalker.sgd.model.Transaction;
import br.com.adailtonskywalker.sgd.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final AccountService accountService;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository, AccountService accountService) {
        this.transactionRepository = transactionRepository;
        this.accountService = accountService;
    }

    @Transactional
    public Transaction save(TransactionRequestData transactionRequestData) {
        Transaction transaction = new Transaction();
        transaction.setDate(transactionRequestData.getDate());
        transaction.setType(transactionRequestData.getType());
        transaction.setDescription(transactionRequestData.getDescription());
        transaction.setAmount(transactionRequestData.getAmount());
        transaction.setAccount(transactionRequestData.getAccount());
        Transaction savedTransaction =  transactionRepository.save(transaction);
        accountService.updateBalance(savedTransaction.getAccount());
        return savedTransaction;
    }
}
