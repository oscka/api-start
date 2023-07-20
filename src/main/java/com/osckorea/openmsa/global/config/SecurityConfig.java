package com.osckorea.openmsa.global.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;

import com.osckorea.openmsa.global.exception.Exception401;
import com.osckorea.openmsa.global.exception.Exception403;
import com.osckorea.openmsa.global.security.FilterResponseUtil;
import com.osckorea.openmsa.global.security.JwtRequestFilter;

@Slf4j
@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig {

    private final CorsConfig corsConfig;

    private static final String[] PERMIT_URL_ARRAY = {
            "/h2-console/**",
            "/favicon.ico",
            "/error",
            "/swagger-ui/**",
            "/swagger-resources/**",
            "/v3/api-docs/**",
            "/v1/users/**"
    };


    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers(PERMIT_URL_ARRAY);
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    // JWT 필터 등록이 필요함
    public class CustomSecurityFilterManager extends AbstractHttpConfigurer<CustomSecurityFilterManager, HttpSecurity> {
        @Override
        public void configure(HttpSecurity builder) throws Exception {
            AuthenticationManager authenticationManager = builder.getSharedObject(AuthenticationManager.class);
            // 9. CORS 설정
            builder.addFilter(corsConfig.corsFilter());
            // 시큐리티 관련 필터
            builder.addFilterBefore(new JwtRequestFilter(authenticationManager),BasicAuthenticationFilter.class);
            //builder.addFilterAfter(new JwtRequestFilter(authenticationManager),BasicAuthenticationFilter.class);

            super.configure(builder);
        }
    }

    /*
        BCryptPasswordEncoder: Spring Security 에서 제공하는 비밀번호 암호화 객체
        service 에서 비밀번호를 암호화,Match  할수 있도록 bean 으로 등록한후 CommonEncoder class 를 util 로 사용
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // 1. CSRF 해제
        http.csrf().disable();

        // 2. jSessionId 사용 거부 (STATELESS 로 설정하면 쿠키에 세션키를 저장하지 않는다.)
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // 3. form 로그인 해제 (UsernamePasswordAuthenticationFilter 비활성화)
        http.formLogin().disable();

        // 4. 로그인 인증창이 뜨지 않게 비활성화
        http.httpBasic().disable();

        // 5. 커스텀 필터 등록 (security filter 교환)
        http.apply(new CustomSecurityFilterManager());

        /*
         SecurityConfig 에서 지정한 authenticationEntryPoint 와  accessDeniedHandler 는 GlobalExceptionHandler 에서 처리하지 않음
         */
        // 6. 인증 실패 처리
        http.exceptionHandling().authenticationEntryPoint((request, response, authException) -> {
            log.warn("인증되지 않은 사용자가 resource 접근 : {}",authException.getMessage());
            FilterResponseUtil.unAuthorized(response, new Exception401("인증되지 않았습니다"));
        });

        // 7. 권한 실패 처리
        http.exceptionHandling().accessDeniedHandler((request, response, accessDeniedException) -> {
            log.warn("권한이 없는 사용자가 resource 접근 : {}",accessDeniedException.getMessage());
            FilterResponseUtil.forbidden(response, new Exception403("권한이 없습니다"));
        });


        // 8. 인증, 권한 필터 설정
        http
            // img , css 과 같은 static resources 는 허용
            .authorizeRequests().requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
            .and()
            .authorizeRequests().antMatchers(PERMIT_URL_ARRAY).permitAll()
            .and()
            .authorizeRequests(
                    authorize -> authorize
                            .antMatchers("/v1/order/**").authenticated()
                            .antMatchers("/v1/admin/**").hasRole("ADMIN")
                            .antMatchers("/v1/customer/**").hasRole("ADMIN")
                            .antMatchers("/v1/member/**").access("hasRole('CUSTOMER') or hasRole('ADMIN')")
                            .antMatchers("/v1/product/**").access("hasRole('CUSTOMER') or hasRole('MEMBER')")
                            .anyRequest().permitAll()
            );

        // h2-console 접속을 위해 설정
        http.headers().addHeaderWriter(new XFrameOptionsHeaderWriter(XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN));
        http.headers().frameOptions().sameOrigin();

        return http.build();
    }


}
