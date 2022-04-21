package com.callbuslab.community.config.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // 보안 설정을 하는 메서드
        http.csrf().disable()
                .headers().frameOptions().disable()
                .and()
                .authorizeRequests()
                .antMatchers("/", "/**", "/css/**", "/images/**",
                        "/js/**", "/h2-console/**").permitAll() // 인증없이 호출 되는 부분
                .anyRequest().authenticated()
                .and()
                .logout().logoutSuccessUrl("/");
    }
}
