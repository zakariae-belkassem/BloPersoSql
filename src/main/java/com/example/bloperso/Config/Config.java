package com.example.bloperso.Config;


import com.example.bloperso.Service.BloggerService;
import com.example.bloperso.Service.MyUserDetailsService;
import com.example.bloperso.Service.SessionService;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class Config{
    private final MyUserDetailsService userDetailsService;

    public Config(MyUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .authorizeHttpRequests(auth->{
                    auth.requestMatchers("/registration","/registration", "/login", "/logout")
                            .permitAll()
                            .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                            .permitAll().requestMatchers("/")
                            .authenticated();

                })
                .sessionManagement(
                        s ->
                                s.invalidSessionUrl("/login?expired")
                                        .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                                        .maximumSessions(2)
                                        .maxSessionsPreventsLogin(true))
                .formLogin(
                       form ->
                               form.loginPage("/login").defaultSuccessUrl("/",true)
                                       .permitAll()
                )

                .logout(
                        logout ->
                                logout
                                        .deleteCookies("JSESSIONID")
                                        .logoutUrl("/logout")
                                        .logoutSuccessHandler(
                                                ((request, response, authentication) -> {
                                                    String redirectUrl = request.getHeader("Referer");
                                                    response.sendRedirect(redirectUrl == null ? "/" : redirectUrl);
                                                })))
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
    @Bean
   public SessionService sessionService(){
        return new SessionService();
    }
}