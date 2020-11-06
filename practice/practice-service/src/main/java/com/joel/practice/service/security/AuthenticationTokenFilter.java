package com.joel.practice.service.security;

import com.joel.practice.common.exception.UserFriendlyException;
import com.joel.practice.service.api.UserService;
import com.joel.practice.service.dto.user.UserDetailsDTO;
import io.jsonwebtoken.Claims;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class AuthenticationTokenFilter extends OncePerRequestFilter {
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String ACCESS_TOKEN_COOKIE = "ACCESS_TOKEN";
    public static final String REFRESH_TOKEN_COOKIE = "REFRESH_TOKEN";

    private JwtTokenProvider tokenProvider;
    private UserService userService;

    public AuthenticationTokenFilter(JwtTokenProvider tokenProvider,
                                     UserService userService) {
        this.tokenProvider = tokenProvider;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = resolveToken(request);
        String requestURI = request.getRequestURI();
        if(StringUtils.hasText(token) && tokenProvider.validateToken(token)){
            Claims claims = tokenProvider.getClaimsFromToken(token);
            long userId = Long.parseLong(claims.getSubject());
            if(userId != 0L){
                UserDetailsDTO userDetailsDTO = userService.getUserById(userId);
                List<GrantedAuthority> authorities = userDetailsDTO.getAuthorities()
                        .stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetailsDTO, token, authorities
                );

                if(!userDetailsDTO.getEnabled()){
                    throw new UserFriendlyException("用户已被禁用");
                }
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request,response);
    }

    private String resolveToken(HttpServletRequest request){
        /**
         * 请求头有token
         */
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7);
        }

        /**
         * 请求参数里面有token
         */
        String accessToken = request.getParameter("access_token");
        if(StringUtils.hasText(accessToken)){
            return accessToken;
        }

        /**
         * 请求附带的cookie里面有token
         */
        HashMap<String, Cookie> cookies = new HashMap<>();
        if(request.getCookies() != null){
            for (Cookie cookie :request.getCookies()) {
                cookies.put(cookie.getName(),cookie);
            }
        }

        if(cookies.containsKey(ACCESS_TOKEN_COOKIE)){
            Cookie cookie = cookies.get(ACCESS_TOKEN_COOKIE);
            if(tokenProvider.validateToken(cookie.getValue())){
                return cookie.getValue();
            }
        }

        /**
         * cookie里面有刷新用的token
         */
        if(cookies.containsKey(REFRESH_TOKEN_COOKIE)){
            Cookie cookie = cookies.get(REFRESH_TOKEN_COOKIE);
            return tokenProvider.refreshToken(cookie.getValue());
        }
        return null;
    }
}
