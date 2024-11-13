package br.com.adailtonskywalker.sgd.repository;

import br.com.adailtonskywalker.sgd.model.InstallmentPlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface InstallmentPlanRepository extends JpaRepository<InstallmentPlan, UUID> {
}
