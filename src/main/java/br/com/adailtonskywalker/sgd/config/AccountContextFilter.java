package br.com.adailtonskywalker.sgd.config;

import br.com.adailtonskywalker.sgd.config.security.AccountContext;
import br.com.adailtonskywalker.sgd.model.User;
import br.com.adailtonskywalker.sgd.service.AccountService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AccountContextFilter extends OncePerRequestFilter {

    private final AccountService accountService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain)
            throws ServletException, IOException {

        String accountId = request.getHeader("X-Account-Id");

        if (accountId != null) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();

            if (auth != null && auth.getPrincipal() instanceof User user) {
                if (!accountService.userHasAccount(UUID.fromString(accountId), user.getId())) {
                    throw new AccessDeniedException("Account not allowed");
                }

                AccountContext.set(UUID.fromString(accountId));
            }
        }

        try {
            chain.doFilter(request, response);
        } finally {
            AccountContext.clear();
        }
    }
}
