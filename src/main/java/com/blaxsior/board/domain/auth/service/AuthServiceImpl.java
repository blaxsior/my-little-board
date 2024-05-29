package com.blaxsior.board.domain.auth.service;

import com.blaxsior.board.domain.auth.dto.SignupDto;
import com.blaxsior.board.domain.auth.exception.PasswordConfirmNotMatchException;
import com.blaxsior.board.domain.mail.MailService;
import com.blaxsior.board.domain.member.entity.Member;
import com.blaxsior.board.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final PasswordEncoder passwordEncoder;
    private final MemberService memberService;
    private final MailService mailService;

    @Override
    public void findUsername(String email) {
        Optional<Member> optMember = memberService.findByEmail(email);

        // 대상 이메일 찾을 수 없으면 아무것도 안함.
        if(optMember.isEmpty()) return;

        Member member = optMember.get();

        // email / username
        Map<String, Object> params = new HashMap<>();
        params.put("email", member.getEmail());
        params.put("username", member.getUsername());

        mailService.sendMail(email, "아이디 찾기", "mail/auth/findUserIdMail", params);
    }

    @Override
    public void resetPassword(String email) {

    }

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
