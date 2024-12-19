package com.osckorea.openmsa.global.security;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.StringUtils;

import com.osckorea.openmsa.global.enums.UserRole;
import com.osckorea.openmsa.starter.user.domain.User;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;


/**
 * @author jinseul
 * JWT 토큰 유효성 검사 및 SecurityContextHolder 생성
 */
@Slf4j
public class JwtRequestFilter extends BasicAuthenticationFilter {

    public JwtRequestFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filter) throws ServletException, IOException {

        String header = "";

        try {

            header = request.getHeader(SecurityConstants.TOKEN_HEADER);

            if (!StringUtils.hasLength(header)){
                filter.doFilter(request, response);
                return;
            }

            byte[] signingkey = SecurityConstants.JWT_SECRET.getBytes();

            Jws<Claims> parsedToken = Jwts.parser().setSigningKey(signingkey)
                    .parseClaimsJws(header.replace(SecurityConstants.TOKEN_PREFIX, ""));

            Claims claims = parsedToken.getBody();

            String loginId = (String) claims.get("loginId");
            String role = (String) claims.get("role");
            String sub = (String) claims.get("sub"); // user primary key (id)

            // CustomUser 에 id 만들어서 user id 값을 SecurityContext 에 넣을 예정
            log.info("loginId : {}, role : {}, sub: {}",loginId,role,sub);

            if (StringUtils.hasLength(loginId) && StringUtils.hasLength(role)) {

                // ROLE 이 List 형태로 들어올경우 아래 code 사용
                //List<String> roleList = (List<String>) claims.get("role");
                //List<SimpleGrantedAuthority> authorities = ((List<?>) claims.get("role")).stream().map(authority -> new SimpleGrantedAuthority((String) authority)).collect(Collectors.toList());

                User user = User.builder()
                        .id(UUID.fromString(sub))
                        .loginId(loginId)
                        .password("") // password 빈값으로 넣어야함!
                        .role(UserRole.valueOf(role))
                        .build();

                CustomUser myUserDetails = new CustomUser(user);

                Authentication authentication =
                        new UsernamePasswordAuthenticationToken(
                                myUserDetails,
                                myUserDetails.getPassword(),
                                myUserDetails.getAuthorities()
                        );

                SecurityContextHolder.getContext().setAuthentication(authentication);
                filter.doFilter(request, response);

            }

        } catch (ExpiredJwtException exception) {
            log.warn("Request to parse expired JWT : {} failed : {}", header, exception.getMessage());
        } catch (UnsupportedJwtException exception) {
            log.warn("Request to parse unsupported JWT : {} failed : {}", header, exception.getMessage());
        } catch (MalformedJwtException exception) {
            log.warn("Request to parse invalid JWT : {} failed : {}", header, exception.getMessage());
        } catch (IllegalArgumentException exception) {
            log.warn("Request to parse empty or null JWT : {} failed : {}", header, exception.getMessage());
        }
    }


}