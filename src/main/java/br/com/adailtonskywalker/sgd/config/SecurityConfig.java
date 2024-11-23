package br.com.adailtonskywalker.sgd.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;

    public SecurityConfig(AuthenticationProvider authenticationProvider, JwtAuthenticationFilter jwtAuthenticationFilter, CustomAuthenticationEntryPoint customAuthenticationEntryPoint, CustomAccessDeniedHandler customAccessDeniedHandler) {
        this.authenticationProvider = authenticationProvider;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.customAuthenticationEntryPoint = customAuthenticationEntryPoint;
        this.customAccessDeniedHandler = customAccessDeniedHandler;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(customizer -> {
            customizer.requestMatchers("/auth/login", "/users/").permitAll();
            customizer.anyRequest().authenticated();
        });
        http.exceptionHandling(customizer -> customizer.accessDeniedHandler(customAccessDeniedHandler));
        http.exceptionHandling(customizer -> customizer.authenticationEntryPoint(customAuthenticationEntryPoint));
        http.cors(withDefaults());
        http.csrf(AbstractHttpConfigurer::disable);
        http.authenticationProvider(authenticationProvider);
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
