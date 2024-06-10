package com.surfer.apiserver.common.filter;

import com.surfer.apiserver.common.exception.BusinessException;
import com.surfer.apiserver.common.jwt.JwtTokenProvider;
import com.surfer.apiserver.common.response.ApiResponseCode;
import com.surfer.apiserver.common.response.BaseResponse;
import com.surfer.apiserver.common.response.RestApiResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class JwtTokenValidatorFilter extends OncePerRequestFilter {

    private static final List<String> EXCLUDE_URL =
            List.of("/auth/sign-up", "/auth/sign-in", "/api-docs");

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
        boolean excludeUri = EXCLUDE_URL.stream().anyMatch(exclude -> exclude.equalsIgnoreCase(request.getServletPath()));
        if (request.getServletPath().contains("actuator") || request.getServletPath().contains("swagger") || request.getServletPath().contains("docs")) {
            return true;
        }
        return excludeUri;
    }
}
