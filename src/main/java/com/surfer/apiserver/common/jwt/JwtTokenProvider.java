package com.surfer.apiserver.common.jwt;

import com.surfer.apiserver.api.auth.dto.AuthDTO;
import com.surfer.apiserver.api.auth.dto.AuthDTO.TokenInfo;
import com.surfer.apiserver.common.exception.BusinessException;
import com.surfer.apiserver.common.response.ApiResponseCode;
import com.surfer.apiserver.domain.database.entity.MemberEntity;
import com.surfer.apiserver.domain.database.repository.MemberRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.io.Decoders;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Slf4j
public class JwtTokenProvider implements InitializingBean {

    private final MemberRepository memberRepository;

    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.access-token-validity-in-second}")
    private long accessTokenValidityInSecond;
    @Value("${jwt.refresh-token-validity-in-second}")
    private long refreshTokenValidityInSecond;
    @Value("${jwt.issuer}")
    private String issuer;
    private SecretKey key;

    @Override
    public void afterPropertiesSet() throws Exception {
        key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public TokenInfo createToken(Authentication authentication) {
        String authorities = authentication.getAuthorities().stream().findFirst().get().toString();

        String accessToken = Jwts.builder()
                .issuer(issuer)
                .subject("authentication.getName()")
                .claim("username", authentication.getName())
                .claim("authorities", authorities)
                .issuedAt(new Date())
                .expiration(new Date(new Date().getTime() + accessTokenValidityInSecond))
                .signWith(key)
                .compact();
        String refreshToken = getRefreshToken(authentication.getName());
        return TokenInfo.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public TokenInfo createToken(MemberEntity member) {
        String accessToken = Jwts.builder()
                .issuer(issuer)
                .subject("authentication.getName()")
                .claim("username", member.getEmail())
                .claim("authorities", member.getRole())
                .issuedAt(new Date())
                .expiration(new Date(new Date().getTime() + accessTokenValidityInSecond))
                .signWith(key)
                .compact();
        String refreshToken = getRefreshToken(member.getEmail());
        return TokenInfo.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public String getRefreshToken(String userName) {
        MemberEntity memberEntity = memberRepository.findByEmail(userName).orElseThrow(
                () -> new BusinessException(ApiResponseCode.INVALID_USER_ID, HttpStatus.BAD_REQUEST));
        if (memberEntity.getRefreshTokenExpiredAt() == null
                || memberEntity.getRefreshTokenExpiredAt() >= new Date().getTime() + refreshTokenValidityInSecond
                || memberEntity.getRefreshToken() == null) {
            memberEntity.setRefreshTokenExpiredAt(System.currentTimeMillis() + refreshTokenValidityInSecond);
            memberEntity.setRefreshToken(UUID.randomUUID().toString());
            memberEntity = memberRepository.save(memberEntity);
        }
        return memberEntity.getRefreshToken();
    }

}
