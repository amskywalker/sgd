package br.com.adailtonskywalker.sgd.mapper;

import br.com.adailtonskywalker.sgd.interfaces.Mapper;
import br.com.adailtonskywalker.sgd.dto.AccountResponseData;
import br.com.adailtonskywalker.sgd.model.Account;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AccountMapper implements Mapper<Account, AccountResponseData, AccountResponseData> {
    @Override
    public Account toEntity(AccountResponseData inputDto) {
        return new Account();
    }

    @Override
    public AccountResponseData toDto(Account entity) {
        return AccountResponseData.builder()
                .id(entity.getId())
                .active(entity.getActive())
                .balance(entity.getBalance())
                .dateCreated(entity.getDateCreated())
                .build();
    }

    public List<AccountResponseData> toDtoList(List<Account> dtoList) {
        if (dtoList == null) {
            return List.of();
        }

        return dtoList.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}
