package com.blaxsior.board.domain.auth;

import com.blaxsior.board.domain.auth.service.MemberSecurityService;
import com.blaxsior.board.domain.member.repository.MemberRepository;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
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
                // 유저 생성 / 아이디 찾기, 비밀번호 찾기 등 auth는 기본 접근 가능
                .requestMatchers("/auth/**").permitAll()
                .anyRequest().authenticated()
        );
        http.formLogin(config -> config
                .loginPage("/auth/login")
                .loginProcessingUrl("/auth/login")
                // 인증 전에 보안
                .defaultSuccessUrl("/", true)
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

    // filter chain 거치지 않고 static 경로 무시 가능
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                .requestMatchers("/icons/**");
    }

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
