package com.example.bloperso.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@EnableWebSecurity
public class Security {


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests((requests) -> {
                    try {
                        requests
                                // Allow access to Swagger UI
                                .anyRequest().permitAll().and()
                                // Enable CSRF protection
                                .csrf((csrf) -> csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())).formLogin((form) -> form
                                        .loginPage("/login")
                                        .loginProcessingUrl("/login")
                                        .defaultSuccessUrl("/loggedIn")
                                        .permitAll()
                                )
                                // Logout configuration
                                .logout((logout) -> logout
                                        .logoutUrl("/logout")
                                        .logoutSuccessUrl("/login")
                                        .invalidateHttpSession(true)
                                        .deleteCookies("JSESSIONID")
                                );
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
