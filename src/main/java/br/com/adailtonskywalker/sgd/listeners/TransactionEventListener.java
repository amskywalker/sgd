package br.com.adailtonskywalker.sgd.listeners;

import br.com.adailtonskywalker.sgd.events.TransactionCreatedEvent;
import br.com.adailtonskywalker.sgd.events.TransactionOnBalanceEvent;
import br.com.adailtonskywalker.sgd.mapper.TransactionMapper;
import br.com.adailtonskywalker.sgd.messaging.EventQueue;
import br.com.adailtonskywalker.sgd.sender.Sender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@RequiredArgsConstructor
@Component
public class TransactionEventListener {

    private final Sender sender;
    private final TransactionMapper transactionMapper;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onTransactionCreated(TransactionCreatedEvent event) {
        sender.sendMessage(EventQueue.TRANSACTION_CREATED, transactionMapper.toDto(event.transaction()));
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onTransactionOnBalance(TransactionOnBalanceEvent event) {
        sender.sendMessage(EventQueue.TRANSACTION_ON_BALANCE, transactionMapper.toDto(event.transaction()));
    }
}
