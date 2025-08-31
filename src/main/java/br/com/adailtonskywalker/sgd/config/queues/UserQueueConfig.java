package br.com.adailtonskywalker.sgd.config.queues;

import br.com.adailtonskywalker.sgd.messaging.EventQueue;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserQueueConfig {

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange("direct.exchange", true, false);
    }

    @Bean
    public Queue userCreatedQueue() {
        return new Queue(EventQueue.USER_CREATED.queue, true);
    }

    @Bean
    public Queue userDeletedQueue() {
        return new Queue(EventQueue.USER_DELETED.queue, true);
    }

    @Bean
    public Binding userCreatedBinding(DirectExchange exchange) {
        return BindingBuilder
                .bind(userCreatedQueue())
                .to(exchange)
                .with(EventQueue.USER_CREATED.routingKey);
    }

    @Bean
    public Binding userDeletedBinding(DirectExchange exchange) {
        return BindingBuilder
                .bind(userDeletedQueue())
                .to(exchange)
                .with(EventQueue.USER_DELETED.routingKey);
    }
}
