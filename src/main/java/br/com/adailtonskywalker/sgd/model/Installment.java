package br.com.adailtonskywalker.sgd.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Table(name = "installments")
@Data
public class Installment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
}
