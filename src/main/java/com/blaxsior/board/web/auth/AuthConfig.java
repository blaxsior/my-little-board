package com.blaxsior.board.web.auth;

import com.blaxsior.board.domain.member.repository.MemberRepository;
import com.blaxsior.board.web.auth.service.MemberSecurityService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class AuthConfig {
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(config -> config
                // allow create user
                .requestMatchers("/auth/signup", "/").permitAll()
                // 리소스 접근 허용. 로그인 안해도 기본적으로 접근 되야 함.
                .requestMatchers("/css/**","/icons/**", "/js/**").permitAll()
                .anyRequest().authenticated()
        );
        http.formLogin(config -> config
                .loginPage("/auth/login")
                .loginProcessingUrl("/auth/login")
                .permitAll()
        );

        return http.build();
    }

//    @Bean
//    UserDetailsService detailsService() {
//        UserDetails user1 = User.builder()
//                .username("user")
//                .password("{noop}password")
//                .build();
//
//        return new InMemoryUserDetailsManager(user1);
//    }

    @Bean
    UserDetailsService customBeanService(MemberRepository repository) {
        return new MemberSecurityService(repository);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        // encode는 bscrypt, matches는 모든 타입 가능. => 더 많은 상황에 대응 가능
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
