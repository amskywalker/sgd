package br.com.adailtonskywalker.sgd.service;

import br.com.adailtonskywalker.sgd.dto.AccountRequestData;
import br.com.adailtonskywalker.sgd.dto.MeRequestData;
import br.com.adailtonskywalker.sgd.dto.UserRequestData;
import br.com.adailtonskywalker.sgd.dto.UserResponseData;
import br.com.adailtonskywalker.sgd.mapper.UserMapper;
import br.com.adailtonskywalker.sgd.model.User;
import br.com.adailtonskywalker.sgd.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final AccountService accountService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Autowired
    public UserService(UserRepository userRepository, UserMapper userMapper, AccountService accountService, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.accountService = accountService;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public UserResponseData save(UserRequestData userRequestData) {
        User user = userMapper.toEntity(userRequestData);
        User createdUser = userRepository.save(user);
        accountService.save(AccountRequestData.builder().user(user).build());
        return userMapper.toDto(createdUser);
    }

//    @TODO Return an exception
    public UserResponseData findByUsername(String username) {
        User foundedUser = userRepository.findByUsername(username).orElse(null);
        assert foundedUser != null;
        return userMapper.toDto(foundedUser);
    }

    public UserResponseData findByToken(MeRequestData meRequestData) {
        String username = jwtService.extractUsername(meRequestData.getToken());
        return findByUsername(username);
    }
}
