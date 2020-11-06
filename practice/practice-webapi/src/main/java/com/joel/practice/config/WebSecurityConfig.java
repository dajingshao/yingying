package com.joel.practice.config;

import com.joel.practice.service.config.SecurityConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebSecurityConfig extends SecurityConfig {
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(
                ignoreUrlsProperties().getUrls().toArray(new String[0])
        );
    }
}
