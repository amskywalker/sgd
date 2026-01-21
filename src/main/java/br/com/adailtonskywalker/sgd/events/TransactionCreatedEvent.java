package br.com.adailtonskywalker.sgd.events;

import java.util.UUID;

public record TransactionCreatedEvent(UUID transactionId, UUID accountId, Float amount) {}