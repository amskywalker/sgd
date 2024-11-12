package br.com.adailtonskywalker.sgd.repository;

import br.com.adailtonskywalker.sgd.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
}
