package br.com.adailtonskywalker.sgd.events;

import br.com.adailtonskywalker.sgd.model.Transaction;

public record TransactionOnBalanceEvent(Transaction transaction) {}