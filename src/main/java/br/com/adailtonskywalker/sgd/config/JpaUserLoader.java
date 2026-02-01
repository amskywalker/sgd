package br.com.adailtonskywalker.sgd.config;

import br.com.adailtonskywalker.sgd.exception.EntityNotFoundException;
import br.com.adailtonskywalker.sgd.model.User;
import br.com.adailtonskywalker.sgd.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JpaUserLoader {

    private final UserRepository userRepository;

    public User loadById(Long userId) {
        return userRepository.findById(userId)
            .orElseThrow(() -> new EntityNotFoundException("User"));
    }
}