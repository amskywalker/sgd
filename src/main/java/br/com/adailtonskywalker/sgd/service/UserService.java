package br.com.adailtonskywalker.sgd.service;

import br.com.adailtonskywalker.sgd.dto.AccountRequestData;
import br.com.adailtonskywalker.sgd.dto.UserRequestData;
import br.com.adailtonskywalker.sgd.model.User;
import br.com.adailtonskywalker.sgd.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final AccountService accountService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Autowired
    public UserService(UserRepository userRepository, AccountService accountService, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.accountService = accountService;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Transactional
    public User save(UserRequestData userRequestData) {
        User user = new User();
        user.setName(userRequestData.getName());
        user.setPassword(passwordEncoder.encode(userRequestData.getPassword()));
        user.setUsername(userRequestData.getUsername());
        User createdUser = userRepository.save(user);
        accountService.save(AccountRequestData.builder().user(user).build());
        return createdUser;
    }

//    @TODO Return an exception
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    public User findByToken(String token) {
        String username = jwtService.extractUsername(token);
        return findByUsername(username);
    }
}
