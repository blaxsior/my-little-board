package com.blaxsior.board.domain.auth.service;

import com.blaxsior.board.domain.auth.dto.ResetPasswordDto;
import com.blaxsior.board.domain.auth.dto.SignupDto;
import com.blaxsior.board.domain.auth.exception.PasswordConfirmNotMatchException;
import com.blaxsior.board.domain.auth.exception.InvalidResetPasswordTokenException;
import com.blaxsior.board.domain.mail.MailService;
import com.blaxsior.board.domain.member.entity.Member;
import com.blaxsior.board.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final PasswordEncoder passwordEncoder;
    private final MemberService memberService;
    private final MailService mailService;
    private final StringRedisTemplate redis;

    private Integer urlValidSeconds;

    @Value("${current.resetpassword.valid-second}")
    public void setUrlValidSeconds(Integer urlValidSeconds) {
        this.urlValidSeconds = urlValidSeconds;
    }

    @Override
    public void mailToFindUsername(String email, String indexUrl) {
        Optional<Member> optMember = memberService.findByEmail(email);

        // 대상 이메일 찾을 수 없으면 아무것도 안함.
        if(optMember.isEmpty()) return;

        Member member = optMember.get();

        // email / username
        Map<String, Object> params = new HashMap<>();
        params.put("email", member.getEmail());
        params.put("username", member.getUsername());
        params.put("link", indexUrl);

        mailService.sendMail(email, "아이디 찾기", "mail/auth/findUserIdMail", params);
    }

    /**
     * 패스워드 재설정을 위해 메일을 보낸다. 만약 이메일에 대응되는 유저가 없다면 아무것도 하지 않는다.
     * @param email 유저의 이메일 정보
     * @return redis에 기록된 패스워드 세션을 얻기 위한 키
     */
    @Override
    public String mailToResetPassword(String email, String link) {
        boolean memberExist = memberService.checkExistByEmail(email);

        // 멤버 없다면 예외 발생
        if(!memberExist) throw new RuntimeException("대응되는 유저가 없음");

        // redis에 키 설정. 최대 urlValidSeconds까지 링크가 유지된다.
        String token = UUID.randomUUID().toString();
        String key = getResetTokenKey(token);
        redis.opsForValue().set(key, email, Duration.ofSeconds(urlValidSeconds));

        Map<String, Object> params = new HashMap<>();
        params.put("email", email);
        params.put("link", link + token);
        params.put("urlValidSeconds", urlValidSeconds);

        mailService.sendMail(email, "비밀번호 재설정", "mail/auth/resetPasswordEmail", params);

        return token;
    }

    /**
     * 멤버의 패스워드를 재설정한다.
     * @param token redis에 기록된 패스워드 세션을 얻기 위한 키
     */
    @Transactional
    @Override
    public void resetPassword(String token, ResetPasswordDto dto) throws InvalidResetPasswordTokenException, PasswordConfirmNotMatchException {
        String key = getResetTokenKey(token);
        String email = redis.opsForValue().get(key);
        log.info("token = {}", token);
        log.info("key = {}", key);
        log.info("email = {}", email);
        if(email == null) throw new InvalidResetPasswordTokenException("유효하지 않은 패스워드 재설정 세션");

        Optional<Member> optMember = memberService.findByEmail(email);
        // 유저 없으면 아무것도 안함
        if(optMember.isEmpty()) return;

        String password = dto.getPassword();
        String passwordConfirm = dto.getPasswordConfirm();
        checkPasswordMatch(password, passwordConfirm);

        var encoded_password = passwordEncoder.encode(password);

        Member member = optMember.get();
        member.setPassword(encoded_password);
        memberService.save(member);

        // 키를 제거, 이미 사용된 세션을 통해 다시 비밀번호를 변경할 수 없게 한다.
        redis.delete(key);
    }

    @Override
    public void signup(SignupDto signupDto) {
        var password = signupDto.getPassword();
        var passwordConfirm = signupDto.getPasswordConfirm();
        checkPasswordMatch(password, passwordConfirm);

        var encoded_password = passwordEncoder.encode(password);

        memberService.create(
                signupDto.getUsername(),
                encoded_password,
                signupDto.getNickname(),
                signupDto.getEmail()
        );
    }

    private String getResetTokenKey(String token) {
        return "reset-password:" + token;
    }

    private void checkPasswordMatch(String password, String passwordConfirm) throws PasswordConfirmNotMatchException {
        if(!password.equals(passwordConfirm)) throw new PasswordConfirmNotMatchException("패스워드 확인이 일치하지 않습니다.");
    }
}
