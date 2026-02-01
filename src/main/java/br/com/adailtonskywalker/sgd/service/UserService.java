package br.com.adailtonskywalker.sgd.service;

import br.com.adailtonskywalker.sgd.dto.UserRequestData;
import br.com.adailtonskywalker.sgd.dto.UserResponseData;
import br.com.adailtonskywalker.sgd.events.UserCreatedEvent;
import br.com.adailtonskywalker.sgd.exception.EntityExistsException;
import br.com.adailtonskywalker.sgd.exception.EntityNotFoundException;
import br.com.adailtonskywalker.sgd.mapper.UserMapper;
import br.com.adailtonskywalker.sgd.model.User;
import br.com.adailtonskywalker.sgd.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final JwtService jwtService;
    private final ApplicationEventPublisher publisher;

    public UserResponseData save(UserRequestData userRequestData) {
        User user = userMapper.toEntity(userRequestData);

        if (userRepository.existsByUsername(user.getUsername())) {
            throw new EntityExistsException("User");
        }

        User createdUser = userRepository.save(user);

        publisher.publishEvent(new UserCreatedEvent(user.getId()));

        return userMapper.toDto(createdUser);
    }

    public UserResponseData findByUsername(String username) {
        User foundedUser = userRepository.findByUsername(username).orElseThrow(() -> new EntityNotFoundException("User"));
        assert foundedUser != null;
        return userMapper.toDto(foundedUser);
    }

    public UserResponseData findByToken(String token) {
        String username = jwtService.extractUsername(token);
        return findByUsername(username);
    }
}
