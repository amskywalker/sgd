package br.com.adailtonskywalker.sgd.service;

import br.com.adailtonskywalker.sgd.dto.TransactionRequestData;
import br.com.adailtonskywalker.sgd.dto.TransactionResponseData;
import br.com.adailtonskywalker.sgd.dto.UserResponseData;
import br.com.adailtonskywalker.sgd.events.TransactionCreatedEvent;
import br.com.adailtonskywalker.sgd.exception.EntityNotFoundException;
import br.com.adailtonskywalker.sgd.exception.UnauthorizedActionException;
import br.com.adailtonskywalker.sgd.mapper.TransactionMapper;
import br.com.adailtonskywalker.sgd.model.Transaction;
import br.com.adailtonskywalker.sgd.repository.TransactionRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;
    private final ApplicationEventPublisher publisher;
    private final AccountService accountService;
    private final UserService userService;

    @Transactional
    public TransactionResponseData save(TransactionRequestData transactionRequestData) {
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attrs.getRequest();
        String token = request.getHeader("Authorization").substring(7);
        UserResponseData userResponseData = userService.findByToken(token);
        UUID convertedUUID = UUID.fromString(transactionRequestData.getAccountUuid().trim());

        if (!Objects.equals(userResponseData.getAccount().getId(), convertedUUID)) {
            throw new UnauthorizedActionException();
        }

        Transaction transaction = transactionMapper.toEntity(transactionRequestData);
        if(!accountService.existsById(UUID.fromString(transactionRequestData.getAccountUuid()))){
            throw new EntityNotFoundException("Account");
        }
        Transaction savedTransaction = transactionRepository.save(transaction);
        publisher.publishEvent(new TransactionCreatedEvent(savedTransaction));
        return transactionMapper.toDto(savedTransaction);
    }

    @Transactional
    public TransactionResponseData update(UUID transactionId, TransactionRequestData transactionRequestData) {
        Transaction transaction = transactionRepository.findById(transactionId).orElse(null);
        assert transaction != null;
        transactionMapper.updateEntityFromDto(transactionRequestData, transaction);
        Transaction savedTransaction = transactionRepository.save(transaction);
        return transactionMapper.toDto(savedTransaction);
    }

    public List<TransactionResponseData> index() {
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attrs.getRequest();
        String token = request.getHeader("Authorization").substring(7);
        UserResponseData userResponseData = userService.findByToken(token);

        List<Transaction> transactions = transactionRepository.findAllByAccountId(userResponseData.getAccount().getId());
        return transactionMapper.toDtoList(transactions);
    }

    public TransactionResponseData getByUUID(UUID uuid) {

        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attrs.getRequest();
        String token = request.getHeader("Authorization").substring(7);
        UserResponseData userResponseData = userService.findByToken(token);

        Transaction transaction = transactionRepository.findById(uuid)
                .orElseThrow(() -> new EntityNotFoundException("Transaction"));

        if (!Objects.equals(transaction.getAccount().getId(), userResponseData.getAccount().getId())) {
            throw new UnauthorizedActionException();
        }


        return transactionMapper.toDto(transaction);
    }
}
