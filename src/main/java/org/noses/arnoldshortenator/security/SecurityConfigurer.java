package org.noses.arnoldshortenator.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.noses.arnoldshortenator.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

@Configuration
@EnableWebSecurity
public class SecurityConfigurer {

    @Autowired
    CustomOAuth2UserService oauthUserService;

    @Autowired
    UserService userService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        return http
                .csrf().disable()
                .authorizeHttpRequests(authCustomizer -> {
                            try {
                                authCustomizer
                                        .requestMatchers(HttpMethod.GET, "/**", "/api/**", "/login.html", "/login/**", "/oauth/**").permitAll()
                                        .anyRequest().authenticated()
                                        .and().formLogin().loginPage("/login.html")
                                        .permitAll()
                                        .and().oauth2Login().loginPage("/login.html")
                                        .userInfoEndpoint()
                                        .userService(oauthUserService)
                                        .and()
                                        .successHandler(new AuthenticationSuccessHandler() {

                                            @Override
                                            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                                                                Authentication authentication) throws IOException, ServletException {

                                                CustomOAuth2User oauthUser = (CustomOAuth2User) authentication.getPrincipal();

                                                userService.processOAuthPostLogin(oauthUser.getEmail());

                                                response.sendRedirect("/");
                                            }
                                        })
                                        .and()
                                        .anonymous().disable().exceptionHandling().authenticationEntryPoint(new AuthenticationEntryPoint() {
                                            @Override
                                            public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
                                                response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                                            }
                                        });
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                ).build();

    }
}