package br.com.adailtonskywalker.sgd.config.security;

import java.util.UUID;

public final class AccountContext {

    private static final ThreadLocal<UUID> CURRENT = new ThreadLocal<>();

    public static void set(UUID id) { CURRENT.set(id); }
    public static UUID get() { return CURRENT.get(); }
    public static void clear() { CURRENT.remove(); }
}
