package com.blaxsior.board.domain.auth.service;

import com.blaxsior.board.domain.auth.dto.SignupDto;
import com.blaxsior.board.domain.auth.exception.PasswordConfirmNotMatchException;
import com.blaxsior.board.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final PasswordEncoder passwordEncoder;
    private final MemberService memberService;
    @Override
    public void signup(SignupDto signupDto) {
        var password = signupDto.getPassword();
        var repeatPassword = signupDto.getPasswordConfirm();

        if(!password.equals(repeatPassword)) throw new PasswordConfirmNotMatchException("패스워드 확인이 일치하지 않습니다.");

        var encoded_password = passwordEncoder.encode(password);

        memberService.create(
                signupDto.getUsername(),
                encoded_password,
                signupDto.getNickname(),
                signupDto.getEmail()
        );
    }
}
