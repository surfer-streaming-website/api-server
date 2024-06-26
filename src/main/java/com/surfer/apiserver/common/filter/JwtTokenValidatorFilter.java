package com.surfer.apiserver.common.filter;

import com.surfer.apiserver.common.jwt.JwtTokenProvider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.regex.Pattern;


import static com.surfer.apiserver.common.constant.Constant.*;

@Slf4j
@RequiredArgsConstructor
public class JwtTokenValidatorFilter extends OncePerRequestFilter {
    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    public AntPathMatcher antPathMatcher(){ return new AntPathMatcher();}

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = jwtTokenProvider.getAccessTokenByRequestHeader(request);
            Claims claims = jwtTokenProvider.validateToken(jwt);
            Authentication auth = new UsernamePasswordAuthenticationToken(claims.get("user"), null,
                    AuthorityUtils.commaSeparatedStringToAuthorityList((String) claims.get("authorities")));
            SecurityContextHolder.getContext().setAuthentication(auth);
        } catch (ExpiredJwtException e) {
            request.setAttribute("exception", "expire");
        } catch (Exception e) {
            request.setAttribute("exception", "invalid");
        }
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        boolean excludeUri = switch (request.getMethod().toUpperCase()) {
            case "GET" -> checkUrlPatternMatch(permitGetMethodUrl, permitGetMethodUrlAntPattern, request);
            case "POST" -> checkUrlPatternMatch(permitPostMethodUrl, permitPostMethodUrlAntPattern, request);
            case "DELETE" -> checkUrlPatternMatch(permitDeleteMethodUrl, permitDeleteMethodUrlAntPattern, request);
            case "PUT" -> checkUrlPatternMatch(permitPutMethodUrl, permitPutMethodUrlAntPattern, request);
            case "HEAD" -> checkUrlPatternMatch(permitHeadMethodUrl, permitHeadMethodUrlAntPattern, request);
            case "PATCH" -> checkUrlPatternMatch(permitPatchMethodUrl, permitPatchMethodUrlAntPattern, request);
            case "OPTIONS" -> checkUrlPatternMatch(permitOptionsMethodUrl, permitOptionsMethodUrlAntPattern, request);
            default -> false;
        };
        return excludeUri;
    }

    private boolean checkUrlPatternMatch(String[] urls, String[] patterns, HttpServletRequest request) {
        return Arrays.stream(urls).anyMatch(exclude -> exclude.equalsIgnoreCase(request.getServletPath()))
                || Arrays.stream(patterns).anyMatch(pattern -> antPathMatcher().match(pattern, request.getServletPath()));
    }
}