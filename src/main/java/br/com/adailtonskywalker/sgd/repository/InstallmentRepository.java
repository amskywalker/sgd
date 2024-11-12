package br.com.adailtonskywalker.sgd.repository;

import br.com.adailtonskywalker.sgd.model.Installment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface InstallmentRepository extends JpaRepository<Installment, UUID> {
}
