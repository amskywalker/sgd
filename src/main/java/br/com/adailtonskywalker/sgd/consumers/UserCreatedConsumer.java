package br.com.adailtonskywalker.sgd.consumers;

import br.com.adailtonskywalker.sgd.events.UserCreatedEvent;
import br.com.adailtonskywalker.sgd.exception.EntityNotFoundException;
import br.com.adailtonskywalker.sgd.messaging.Queues;
import br.com.adailtonskywalker.sgd.model.Account;
import br.com.adailtonskywalker.sgd.model.User;
import br.com.adailtonskywalker.sgd.repository.AccountRepository;
import br.com.adailtonskywalker.sgd.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserCreatedConsumer {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    @RabbitListener(queues = Queues.USER_CREATED)
    public void consume(UserCreatedEvent event) {
        log.info("User created: {}", event);

        User user = userRepository.findById(event.userId())
                .orElseThrow(() -> new EntityNotFoundException("User"));

        Account account = new Account();
        account.addUser(user);

        accountRepository.save(account);
    }
}