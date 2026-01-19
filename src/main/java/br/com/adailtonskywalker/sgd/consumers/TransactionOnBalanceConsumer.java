package br.com.adailtonskywalker.sgd.consumers;

import br.com.adailtonskywalker.sgd.dto.TransactionResponseData;
import br.com.adailtonskywalker.sgd.messaging.Queues;
import br.com.adailtonskywalker.sgd.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionOnBalanceConsumer {
    private final TransactionService transactionService;

    @RabbitListener(queues = Queues.TRANSACTION_ON_BALANCE)
    public void consume(TransactionResponseData transaction) {
        log.info("Transaction on balance: {}", transaction);
        transactionService.markOnBalance(transaction.getId());
    }
}