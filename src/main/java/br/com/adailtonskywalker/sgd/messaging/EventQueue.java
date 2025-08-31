package br.com.adailtonskywalker.sgd.messaging;

public enum EventQueue {

    USER_CREATED(Queues.USER_CREATED, "user.created", "direct.exchange"),
    USER_DELETED(Queues.USER_DELETED, "user.deleted", "direct.exchange"),
    ACCOUNT_CREATED(Queues.ACCOUNT_CREATED, "account.created", "direct.exchange"),
    TRANSACTION_CREATED(Queues.TRANSACTION_CREATED, "transaction.created", "direct.exchange"),
    TRANSACTION_ON_BALANCE(Queues.TRANSACTION_ON_BALANCE, "transaction.on.balance", "direct.exchange");

    public final String queue;
    public final String routingKey;
    public final String exchange;

    EventQueue(String queue, String routingKey, String exchange) {
        this.queue = queue;
        this.routingKey = routingKey;
        this.exchange = exchange;
    }
}