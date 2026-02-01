package br.com.adailtonskywalker.sgd.config;

import br.com.adailtonskywalker.sgd.model.User;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class CurrentUserProvider {

    public User getUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !(auth.getPrincipal() instanceof User user)) {
            throw new AccessDeniedException("Access Denied");
        }

        return user;
    }

    public Long getUserId() {
        return getUser().getId();
    }
}
