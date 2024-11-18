package br.com.adailtonskywalker.sgd.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "installments")
@Data
public class Installment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private Integer number;

    @Column(nullable = false)
    private Float amount;

    @Column(nullable = false)
    private LocalDateTime date;

    @Column(nullable = false)
    private LocalDateTime dateCreated;

    @ManyToOne(optional = false)
    @JoinColumn(name = "installment_plan_id", nullable = false)
    private InstallmentPlan installmentPlan;

    @PrePersist
    protected void onCreate() {
        dateCreated = LocalDateTime.now();
    }
}
