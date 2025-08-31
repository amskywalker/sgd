package br.com.adailtonskywalker.sgd.config.queues;

import br.com.adailtonskywalker.sgd.messaging.EventQueue;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TransactionQueueConfig {
    @Bean
    public Queue transactionCreatedQueue() {
        return new Queue(EventQueue.TRANSACTION_CREATED.queue, true);
    }

    @Bean
    public Binding transactionCreatedBinding(DirectExchange exchange) {
        return BindingBuilder.bind(transactionCreatedQueue()).to(exchange).with(EventQueue.TRANSACTION_CREATED.routingKey);
    }

    @Bean
    public Queue transactionOnBalanceQueue() {
        return new Queue(EventQueue.TRANSACTION_ON_BALANCE.queue, true);
    }

    @Bean
    public Binding transactionOnBalanceBinding(DirectExchange exchange) {
        return BindingBuilder.bind(transactionOnBalanceQueue()).to(exchange).with(EventQueue.TRANSACTION_ON_BALANCE.routingKey);
    }
}
