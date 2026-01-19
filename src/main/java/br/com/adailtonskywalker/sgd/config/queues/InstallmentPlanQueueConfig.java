package br.com.adailtonskywalker.sgd.config.queues;

import br.com.adailtonskywalker.sgd.messaging.EventQueue;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InstallmentPlanQueueConfig {
    @Bean
    public Queue installmentePlanCreatedQueue() {
        return new Queue(EventQueue.INSTALLMENT_PLAN_CREATED.queue, true);
    }

    @Bean
    public Binding installmentePlanCreatedBinding(DirectExchange exchange) {
        return BindingBuilder
                .bind(installmentePlanCreatedQueue())
                .to(exchange)
                .with(EventQueue.INSTALLMENT_PLAN_CREATED.routingKey);
    }

}
