package com.blaxsior.board.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class AuthConfig {
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(config ->
                config.requestMatchers("/**").authenticated()
        );
        http.formLogin(config -> config
                .loginPage("/auth/login")
                .loginProcessingUrl("/auth/login")
                .permitAll()
        );

        return http.build();
    }

    @Bean
    UserDetailsService detailsService() {
        UserDetails user1 = User.builder()
                .username("user")
                .password("{noop}password")
                .build();

        return new InMemoryUserDetailsManager(user1);
    }
    @Bean
    PasswordEncoder passwordEncoder() {
        // encode는 bscrypt, matches는 모든 타입 가능. => 더 많은 상황에 대응 가능
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
