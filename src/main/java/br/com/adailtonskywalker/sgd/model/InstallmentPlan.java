package br.com.adailtonskywalker.sgd.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "installment_plan")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InstallmentPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private Float amount;

    private Float fees = 0.F;

    @Column(nullable = false)
    private LocalDateTime dateCreated;

    @OneToOne(optional = false)
    @JoinColumn(name = "transaction_id", nullable = false, unique = true)
    private Transaction transaction;

    @OneToMany(mappedBy = "installmentPlan", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Installment> installments;

    @PrePersist
    protected void onCreate() {
        dateCreated = LocalDateTime.now();
    }
}
