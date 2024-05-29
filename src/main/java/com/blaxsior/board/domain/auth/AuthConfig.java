package com.blaxsior.board.domain.auth;

import com.blaxsior.board.domain.auth.service.MemberSecurityService;
import com.blaxsior.board.domain.member.repository.MemberRepository;
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
                // 메인 페이지
                .requestMatchers("/").permitAll()
                // 유저 생성 / 아이디 찾기, 비밀번호 찾기
                .requestMatchers(
                        "/auth/signup",
                        "/auth/find-id",
                        "/auth/find-password",
                        "/auth/notice"
                ).permitAll()
                // 리소스 접근 허용. 로그인 안해도 기본적으로 접근 되야 함.
                .requestMatchers("/css/**","/icons/**", "/js/**").permitAll()
                .anyRequest().authenticated()
        );
        http.formLogin(config -> config
                .loginPage("/auth/login")
                .loginProcessingUrl("/auth/login")
                .permitAll()
        );
        // 로그아웃 경로 지정
        http.logout(logout -> logout
                .logoutUrl("/auth/logout")
                .logoutSuccessUrl("/")
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
