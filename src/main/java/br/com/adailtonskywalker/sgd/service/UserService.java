package br.com.adailtonskywalker.sgd.service;

import br.com.adailtonskywalker.sgd.dto.AccountRequestData;
import br.com.adailtonskywalker.sgd.dto.UserRequestData;
import br.com.adailtonskywalker.sgd.model.User;
import br.com.adailtonskywalker.sgd.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final AccountService accountService;

    @Autowired
    public UserService(UserRepository userRepository, AccountService accountService) {
        this.userRepository = userRepository;
        this.accountService = accountService;
    }

    @Transactional
    public User save(UserRequestData userRequestData) {
        User user = new User();
        user.setName(userRequestData.getName());
        user.setPassword(userRequestData.getPassword());
        user.setUsername(userRequestData.getUsername());
        accountService.save(AccountRequestData.builder().user(user).build());
        return userRepository.save(user);
    }
}
