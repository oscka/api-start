package com.osckorea.openmsa.global.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Spring Security 에서 제공하는 PasswordEncoder 구현후,
 * encode(암호화) , matches(비밀번호 동일 check) 메서드 제공
 */
@Component
public class CommonEncoder implements PasswordEncoder {
    private final PasswordEncoder passwordEncoder;

    public CommonEncoder() {
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public CommonEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String encode(CharSequence rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}

