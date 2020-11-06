package com.joel.practice.service.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.joel.practice.service.api.UserService;
import com.joel.practice.service.security.AuthenticationTokenFilter;
import com.joel.practice.service.security.JwtTokenProvider;
import com.joel.practice.service.security.RestAuthenticationEntryPoint;
import com.joel.practice.service.security.RestfulAccessDeniedHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${jwt.secret-key}")
    private String jwtSecretKey;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    protected UserService userService;

    @Bean
    public IgnoreUrlsProperties ignoreUrlsProperties(){
        return new IgnoreUrlsProperties();
    }

    @Bean
    public RestfulAccessDeniedHandler restfulAccessDeniedHandler(){
        return new RestfulAccessDeniedHandler(objectMapper);
    }

    @Bean
    public RestAuthenticationEntryPoint restAuthenticationEntryPoint(){
        return new RestAuthenticationEntryPoint(objectMapper);
    }

    @Bean
    public JwtTokenProvider tokenProvider(){
        return new JwtTokenProvider(jwtSecretKey);
    }

    /**
     * 用于验证账号密码是否匹配，注入到bean方便调用
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    public AuthenticationTokenFilter authenticationTokenFilter(){
        return new AuthenticationTokenFilter(tokenProvider(),userService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()//关闭打开的csrf保护
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)//Spring Security不会创建session，或使用session
            .and()
                .authorizeRequests()
                .antMatchers(ignoreUrlsProperties().getUrls().toArray(new String[0]))//在yml配置的资源或路径不做校验，都放行
                .permitAll()
                .anyRequest()
                .authenticated()//其他请求或资源都做校验
            .and()
                .exceptionHandling()
                .accessDeniedHandler(restfulAccessDeniedHandler())//设置自定义授权异常处理类
                .authenticationEntryPoint(restAuthenticationEntryPoint())//设置自定义认证异常类
            .and()
                .addFilterBefore(authenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);//添加自定义jwt过滤器
    }
}
