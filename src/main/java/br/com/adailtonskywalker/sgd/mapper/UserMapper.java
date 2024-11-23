package br.com.adailtonskywalker.sgd.mapper;

import br.com.adailtonskywalker.sgd.Mapper;
import br.com.adailtonskywalker.sgd.dto.UserRequestData;
import br.com.adailtonskywalker.sgd.dto.UserResponseData;
import br.com.adailtonskywalker.sgd.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper implements Mapper<User, UserRequestData, UserResponseData> {
    private final AccountMapper accountMapper;

    public UserMapper(AccountMapper accountMapper) {
        this.accountMapper = accountMapper;
    }

    @Override
    public User toEntity(UserRequestData inputDto) {
        User user = new User();
        user.setName(inputDto.getName());
        user.setPassword(inputDto.getPassword());
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
                .account(entity.getAccount() != null ? accountMapper.toDto(entity.getAccount()) : null)
                .build();
    }
}
