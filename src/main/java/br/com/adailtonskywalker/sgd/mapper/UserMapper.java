package br.com.adailtonskywalker.sgd.mapper;

import br.com.adailtonskywalker.sgd.interfaces.Mapper;
import br.com.adailtonskywalker.sgd.dto.UserRequestData;
import br.com.adailtonskywalker.sgd.dto.UserResponseData;
import br.com.adailtonskywalker.sgd.model.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserMapper implements Mapper<User, UserRequestData, UserResponseData> {
    private final PasswordEncoder passwordEncoder;

    public UserMapper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User toEntity(UserRequestData inputDto) {
        User user = new User();
        user.setName(inputDto.getName());
        user.setPassword(passwordEncoder.encode(inputDto.getPassword()));
        user.setUsername(inputDto.getUsername());
        return user;
    }

    @Override
    public UserResponseData toDto(User entity) {
        return UserResponseData.builder()
                .id(entity.getId())
                .username(entity.getUsername())
                .name(entity.getName())
                .dateCreated(entity.getDateCreated())
                .build();
    }
}
