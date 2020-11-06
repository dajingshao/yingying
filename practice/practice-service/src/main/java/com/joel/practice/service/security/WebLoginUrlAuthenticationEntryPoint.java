package com.joel.practice.service.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

public class WebLoginUrlAuthenticationEntryPoint extends LoginUrlAuthenticationEntryPoint {

    public WebLoginUrlAuthenticationEntryPoint(String loginFormUrl) {
        super(loginFormUrl);
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        if (!getLoginFormUrl().equals(request.getRequestURI())) {
            String redirectUrl = request.getRequestURI();
            if (request.getQueryString() != null) {
                redirectUrl += "?" + request.getQueryString();
            }
            redirectUrl = URLEncoder.encode(redirectUrl, "utf-8");
            RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
            redirectStrategy.sendRedirect(request, response, getLoginFormUrl() + "?redirect=" + redirectUrl);
        }
    }
}
