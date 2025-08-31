package br.com.adailtonskywalker.sgd.service;

import br.com.adailtonskywalker.sgd.dto.AccountRequestData;
import br.com.adailtonskywalker.sgd.dto.MeRequestData;
import br.com.adailtonskywalker.sgd.dto.UserRequestData;
import br.com.adailtonskywalker.sgd.dto.UserResponseData;
import br.com.adailtonskywalker.sgd.mapper.UserMapper;
import br.com.adailtonskywalker.sgd.messaging.EventQueue;
import br.com.adailtonskywalker.sgd.model.User;
import br.com.adailtonskywalker.sgd.repository.UserRepository;
import br.com.adailtonskywalker.sgd.sender.Sender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final Sender sender;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final JwtService jwtService;

    public UserResponseData save(UserRequestData userRequestData) {
        User user = userMapper.toEntity(userRequestData);
        User createdUser = userRepository.save(user);
        sender.sendMessage(EventQueue.USER_CREATED, createdUser);
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
