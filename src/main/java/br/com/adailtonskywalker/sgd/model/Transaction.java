package br.com.adailtonskywalker.sgd.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "transactions")
@Data
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private TransactionType type;

    @Column(length = 100)
    private String description;

    @Column
    private LocalDateTime date;

    @Column(nullable = false)
    private LocalDateTime dateCreated;
}
