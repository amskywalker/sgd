package br.com.adailtonskywalker.sgd.service;

import br.com.adailtonskywalker.sgd.dto.LoginRequestData;
import br.com.adailtonskywalker.sgd.dto.LoginResponseData;
import br.com.adailtonskywalker.sgd.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Value("${application.security.jwt.expiration-time}")
    private long jwtExpiration;

    @Autowired
    public AuthService(AuthenticationManager authenticationManager, JwtService jwtService, UserDetailsService userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    public LoginResponseData login(LoginRequestData loginRequestData) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequestData.getUsername(), loginRequestData.getPassword()));
        final User user = (User) userDetailsService.loadUserByUsername(loginRequestData.getUsername());
        String token = jwtService.generateToken(user);
        return new LoginResponseData(token, "Bearer", jwtExpiration + "ms");
    }
}
