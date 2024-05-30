package com.example.bloperso.Config;


import com.example.bloperso.Service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.CompositeLogoutHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.AntPathMatcher;


@Configuration
@EnableWebSecurity
public class Config {
    private final MyUserDetailsService userDetailsService;

    public Config(MyUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity

                .authorizeHttpRequests(auth->{
                    auth
                            .requestMatchers("/registration","/registration", "/login","like","bookMark")
                            .permitAll()
                            .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                            .permitAll()
                            .anyRequest()
                            .authenticated();

                })
                .sessionManagement(
                        s ->s
                                          .invalidSessionUrl("/login?expired")
                                        .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                                        .maximumSessions(1)
                                        .maxSessionsPreventsLogin(false))
                .formLogin(
                       form ->
                               form
                                       .loginPage("/login").defaultSuccessUrl("/",true)
                                       .permitAll()
                )

                .logout(logout ->
                        logout
                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                .logoutSuccessUrl("/login?logout")
                                .invalidateHttpSession(true)
                                .deleteCookies("JSESSIONID")
                                .permitAll()
                )
                .logout(Customizer.withDefaults())

                .build();
    }
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());

        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        return daoAuthenticationProvider;
    }

}