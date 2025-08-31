package br.com.adailtonskywalker.sgd.service;

import br.com.adailtonskywalker.sgd.dto.TransactionRequestData;
import br.com.adailtonskywalker.sgd.dto.TransactionResponseData;
import br.com.adailtonskywalker.sgd.mapper.TransactionMapper;
import br.com.adailtonskywalker.sgd.messaging.EventQueue;
import br.com.adailtonskywalker.sgd.model.Transaction;
import br.com.adailtonskywalker.sgd.repository.TransactionRepository;
import br.com.adailtonskywalker.sgd.sender.Sender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;
    private final Sender sender;

    @Transactional
    public TransactionResponseData save(TransactionRequestData transactionRequestData) {
        Transaction transaction = transactionMapper.toEntity(transactionRequestData);
        Transaction savedTransaction =  transactionRepository.save(transaction);
        TransactionResponseData responseData = transactionMapper.toDto(savedTransaction);
        sender.sendMessage(EventQueue.TRANSACTION_CREATED, responseData);
        return responseData;
    }

    @Transactional
    public TransactionResponseData update(UUID transactionId, TransactionRequestData transactionRequestData) {
        Transaction transaction = transactionRepository.findById(transactionId).orElse(null);
        assert transaction != null;
        transactionMapper.updateEntityFromDto(transactionRequestData, transaction);
        Transaction savedTransaction = transactionRepository.save(transaction);
        return transactionMapper.toDto(savedTransaction);
    }
}
