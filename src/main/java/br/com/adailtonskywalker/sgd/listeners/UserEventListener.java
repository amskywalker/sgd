package br.com.adailtonskywalker.sgd.listeners;

import br.com.adailtonskywalker.sgd.events.UserCreatedEvent;
import br.com.adailtonskywalker.sgd.messaging.EventQueue;
import br.com.adailtonskywalker.sgd.sender.Sender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@RequiredArgsConstructor
@Component
public class UserEventListener {

    private final Sender sender;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onUserCreated(UserCreatedEvent event) {
        sender.sendMessage(EventQueue.USER_CREATED, event);
    }
}
