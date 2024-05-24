package com.blaxsior.board.domain.auth.service;

import com.blaxsior.board.domain.member.entity.Member;
import com.blaxsior.board.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// 적용 범위 파악이 어려워서 @Bean으로 AuthConfig에 등록
//@Service
@RequiredArgsConstructor
public class MemberSecurityService implements UserDetailsService {
    private final MemberRepository memberRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Member> memberOptional = memberRepository.findByUsername(username);
        if(memberOptional.isEmpty()) {
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다");
        }

        Member member = memberOptional.get();
        List<GrantedAuthority> roles = new ArrayList<>();

        return new User(member.getUsername(), member.getPassword(), roles);
    }
}
