package br.com.adailtonskywalker.sgd.repository;

import br.com.adailtonskywalker.sgd.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
