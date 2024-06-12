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
            log.error("만료된 JWT 토큰입니다.");
            request.setAttribute("exception", "expire");
        } catch (Exception e) {
            log.error("잘못된 JWT 토큰입니다.");
            request.setAttribute("exception", "invalid");
        }
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        boolean excludeUri = switch (request.getMethod().toUpperCase()) {
            case "GET" -> checkUrlPatternMatch(permitGetMethodUrl, permitGetMethodUrlPattern, request);
            case "POST" -> checkUrlPatternMatch(permitPostMethodUrl, permitPostMethodUrlPattern, request);
            case "DELETE" -> checkUrlPatternMatch(permitDeleteMethodUrl, permitDeleteMethodUrlPattern, request);
            case "PUT" -> checkUrlPatternMatch(permitPutMethodUrl, permitPutMethodUrlPattern, request);
            case "HEAD" -> checkUrlPatternMatch(permitHeadMethodUrl, permitHeadMethodUrlPattern, request);
            case "PATCH" -> checkUrlPatternMatch(permitPatchMethodUrl, permitPatchMethodUrlPattern, request);
            case "OPTIONS" -> checkUrlPatternMatch(permitOptionsMethodUrl, permitOptionsMethodUrlPattern, request);
            default -> false;
        };
        return excludeUri;
    }

    private boolean checkUrlPatternMatch(String[] urls, String[] patterns, HttpServletRequest request) {
        return (boolean) Arrays.stream(urls).anyMatch(exclude -> exclude.equalsIgnoreCase(request.getServletPath()))
                || (boolean) Arrays.stream(patterns).anyMatch(urlPattern -> Pattern.matches(urlPattern, request.getServletPath()));
    }
}
