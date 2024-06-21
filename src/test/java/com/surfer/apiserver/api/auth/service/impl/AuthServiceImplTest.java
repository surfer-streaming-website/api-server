package com.surfer.apiserver.api.auth.service.impl;

import com.surfer.apiserver.api.auth.dto.AuthDTO;
import com.surfer.apiserver.common.jwt.JwtTokenProvider;
import com.surfer.apiserver.domain.database.entity.MemberEntity;
import com.surfer.apiserver.domain.database.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static com.surfer.apiserver.api.auth.dto.AuthDTO.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

    @InjectMocks
    AuthServiceImpl authServiceImpl;

    @Mock
    MemberRepository memberRepository;
    @Mock
    PasswordEncoder passwordEncoder;
    @Mock
    AuthenticationManagerBuilder authenticationManagerBuilder;
    @Mock
    JwtTokenProvider jwtTokenProvider;


    @Nested
    @DisplayName("회원가입 테스트")
    class signUp{
        SignUpRequest signUpRequest = new SignUpRequest();

        @BeforeEach
        void setUp() {
            String email = "test@test.com";
            String password = "123456";
            String nickname = "test";
            String name = "test";
            signUpRequest.setEmail(email);
            signUpRequest.setPassword(password);
            signUpRequest.setNickname(nickname);
            signUpRequest.setName(name);
        }

        @Test
        @DisplayName("정상 케이스")
        void signUpTest001(){
            //given
            MemberEntity memberEntity = MemberEntity.builder()
                    .name(signUpRequest.getName())
                    .email(signUpRequest.getEmail())
                    .password(signUpRequest.getPassword())
                    .nickname(signUpRequest.getNickname())
                    .build();
            //stub
            when(memberRepository.save(memberEntity)).thenReturn(memberEntity);

            //when
            authServiceImpl.signUp(signUpRequest);
            //then
//            assertThat()
        }

    }

    @Test
    void signIn() {
    }

    @Test
    void testSignIn() {
    }

    @Test
    void loadUserByUsername() {
    }
}
