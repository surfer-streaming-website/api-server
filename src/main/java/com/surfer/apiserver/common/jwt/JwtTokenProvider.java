package com.surfer.apiserver.common.jwt;

import com.surfer.apiserver.api.auth.dto.AuthDTO;
import com.surfer.apiserver.common.constant.CommonCode;
import com.surfer.apiserver.common.exception.BusinessException;
import com.surfer.apiserver.common.response.ApiResponseCode;
import com.surfer.apiserver.common.util.AES256Util;
import com.surfer.apiserver.domain.database.entity.MemberEntity;
import com.surfer.apiserver.domain.database.repository.MemberRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.surfer.apiserver.api.auth.dto.AuthDTO.*;

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
    @Value("${jwt.access-token-header}")
    private String accessTokenHeader;
    private SecretKey key;

    @Override
    public void afterPropertiesSet() throws Exception {
        key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public TokenInfo createToken(MemberEntity member) {
        String authorities = member.getMemberAuthorityEntities().stream()
                .map(memberAuthorityEntity -> memberAuthorityEntity.getAuthority().toString())
                .collect(Collectors.joining(","));  // 콤마로 구분된 문자열로 수집;

        String accessToken = Jwts.builder()
                .issuer(issuer)
                .subject("authentication.getName()")
                .claim("user", AES256Util.encrypt(String.valueOf(member.getMemberId())))
                .claim("nickname", member.getNickname())
                .claim("authorities", authorities)
                .issuedAt(new Date())
                .expiration(new Date(new Date().getTime() + accessTokenValidityInSecond))
                .signWith(key)
                .compact();

        String refreshToken = getRefreshToken(member.getMemberId());
        return TokenInfo.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public Claims validateToken(String token) throws Exception {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String getAccessTokenByRequestHeader(HttpServletRequest request) throws Exception {
        return request.getHeader(accessTokenHeader).split(" ")[1];
    }

    private String getRefreshToken(Long memberSeq) {
        MemberEntity memberEntity = memberRepository.findByMemberId(memberSeq).orElseThrow(
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
