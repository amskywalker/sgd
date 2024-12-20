package br.com.adailtonskywalker.sgd.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "transactions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private TransactionType type;

    @Column(length = 100)
    private String description;

    @Column(nullable = false)
    private LocalDateTime date;

    @Column(nullable = false)
    private Float amount;

    @Column(nullable = false)
    private LocalDateTime dateCreated;

    private Boolean onBalance = false;

    @ManyToOne(optional = false)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @OneToOne(mappedBy = "transaction", cascade = CascadeType.ALL, orphanRemoval = true)
    private InstallmentPlan installmentPlan;

    @PrePersist
    protected void onCreate() {
        date = date == null ? LocalDateTime.now() : date;
        dateCreated = LocalDateTime.now();
    }
}
