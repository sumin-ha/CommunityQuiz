package com.callbuslab.community.config.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    //private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // 보안 설정을 하는 메서드
        http.csrf().disable()
                .headers().frameOptions().disable()
                .and()
                .authorizeRequests()
                .antMatchers("/", "/**", "/css/**", "/images/**",
                        "/js/**", "/h2-console/**").permitAll() // 인증없이 호출 되는 부분
                //.antMatchers("/api/v1/**").hasRole(Role.USER.name()) // 인증이 되어야 열리는 부분의 권한 설정
                .anyRequest().authenticated()
                .and()
                .logout().logoutSuccessUrl("/");
//                .and()
//                .oauth2Login().userInfoEndpoint().userService(customOAuth2UserService);
    }
}
