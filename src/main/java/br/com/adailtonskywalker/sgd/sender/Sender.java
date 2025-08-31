package br.com.adailtonskywalker.sgd.sender;

import br.com.adailtonskywalker.sgd.messaging.EventQueue;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class Sender {
    private final RabbitTemplate rabbitTemplate;

    public void sendMessage(EventQueue event, Object message) {
        rabbitTemplate.convertAndSend(event.exchange, event.routingKey, message);
    }
}
