package br.com.adailtonskywalker.sgd.repository;

import br.com.adailtonskywalker.sgd.model.Installment;
import br.com.adailtonskywalker.sgd.model.InstallmentPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface InstallmentRepository extends JpaRepository<Installment, UUID> {
    List<Installment> getAllByInstallmentPlan(InstallmentPlan installmentPlan);

    @Query("SELECT i FROM Installment i WHERE i.installmentPlan = :installmentPlan AND MONTH(i.date) = :month AND YEAR(i.date) = :year")
    Installment findFirstByInstallmentPlanAndMonthAndYear(
            @Param("installmentPlan") InstallmentPlan installmentPlan,
            @Param("month") int month,
            @Param("year") int year
    );

    Optional<List<Installment>> findByInstallmentPlanId(UUID installmentPlanId);
}
