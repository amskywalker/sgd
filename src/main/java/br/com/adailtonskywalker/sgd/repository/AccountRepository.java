package br.com.adailtonskywalker.sgd.repository;

import br.com.adailtonskywalker.sgd.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, UUID> {
}
