package org.noses.urlshortener.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfigurer {

    @Autowired
    AuthenticationManagerBuilder authenticationManagerBuilder;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf().disable()
                .authorizeHttpRequests(authCustomizer ->
                        authCustomizer.requestMatchers(HttpMethod.POST, "/api/add", "/*/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "/", "", "/api/add", "/*/**").permitAll()
                )
                .build();
    }
}
