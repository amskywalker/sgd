package br.com.adailtonskywalker.sgd.repository;

import br.com.adailtonskywalker.sgd.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, UUID> {
    List<Account> findByUsersId(Long userId);

    @Query("""
                SELECT COUNT(a) > 0
                FROM Account a
                JOIN a.users u
                WHERE a.id = :accountId AND u.id = :userId
            """)
    boolean userHasAccess(UUID accountId, Long userId);
}
