package br.com.adailtonskywalker.sgd.consumers;

import br.com.adailtonskywalker.sgd.events.TransactionCreatedEvent;
import br.com.adailtonskywalker.sgd.messaging.Queues;
import br.com.adailtonskywalker.sgd.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionCreatedConsumer {
    private final AccountService accountService;

    @RabbitListener(queues = Queues.TRANSACTION_CREATED)
    public void consume(TransactionCreatedEvent event) {
        log.info("Transaction created: {}", event.transactionId());
        accountService.updateBalance(event.accountId());
    }
}