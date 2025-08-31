package br.com.adailtonskywalker.sgd.consumers;

import br.com.adailtonskywalker.sgd.dto.AccountRequestData;
import br.com.adailtonskywalker.sgd.messaging.Queues;
import br.com.adailtonskywalker.sgd.model.User;
import br.com.adailtonskywalker.sgd.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserCreatedConsumer {

    private final AccountService accountService;

    public UserCreatedConsumer(AccountService accountService) {
        this.accountService = accountService;
    }

    @RabbitListener(queues = Queues.USER_CREATED)
    public void consume(User user) {
        log.info("User created: {}", user);
        AccountRequestData request = AccountRequestData.builder()
                .user(user)
                .build();

        accountService.save(request);
    }
}