package br.com.adailtonskywalker.sgd.service;

import br.com.adailtonskywalker.sgd.dto.TransactionRequestData;
import br.com.adailtonskywalker.sgd.dto.TransactionResponseData;
import br.com.adailtonskywalker.sgd.events.TransactionCreatedEvent;
import br.com.adailtonskywalker.sgd.exception.EntityNotFoundException;
import br.com.adailtonskywalker.sgd.exception.UnauthorizedActionException;
import br.com.adailtonskywalker.sgd.mapper.TransactionMapper;
import br.com.adailtonskywalker.sgd.model.Account;
import br.com.adailtonskywalker.sgd.model.Transaction;
import br.com.adailtonskywalker.sgd.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;
    private final ApplicationEventPublisher publisher;

    @Transactional
    public TransactionResponseData save(Account account, TransactionRequestData transactionRequestData) {
        Transaction transaction = transactionMapper.toEntity(transactionRequestData);
        transaction.setAccount(account);
        Transaction savedTransaction = transactionRepository.save(transaction);
        publisher.publishEvent(new TransactionCreatedEvent(savedTransaction.getId(), account.getId(), savedTransaction.getAmount()));
        return transactionMapper.toDto(savedTransaction);
    }

    @Transactional
    public TransactionResponseData update(Account account, UUID transactionId, TransactionRequestData transactionRequestData) {
        Transaction transaction = transactionRepository
                .findByIdAndAccountId(transactionId, account.getId())
                .orElseThrow(() -> new AccessDeniedException("Not allowed"));

        transactionMapper.updateEntityFromDto(transactionRequestData, transaction);
        Transaction savedTransaction = transactionRepository.save(transaction);
        return transactionMapper.toDto(savedTransaction);
    }


    @Transactional
    public void markOnBalance(UUID transactionId) {
        Transaction transation = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new EntityNotFoundException("Transaction"));

        transation.setOnBalance(true);
        transactionRepository.save(transation);
    }

    public Transaction getOwnedTransaction(Account account, UUID transactionId) {
        return transactionRepository
                .findByIdAndAccountId(transactionId, account.getId())
                .orElseThrow(UnauthorizedActionException::new);
    }

    public List<TransactionResponseData> index(Account account) {
        List<Transaction> transactions = transactionRepository.findAllByAccountId(account.getId());
        return transactionMapper.toDtoList(transactions);
    }

    public TransactionResponseData getByUUID(Account account, UUID uuid) {
        Transaction transaction = transactionRepository.findByIdAndAccountId(uuid, account.getId())
                .orElseThrow(() -> new AccessDeniedException("Not allowed"));

        return transactionMapper.toDto(transaction);
    }
}
