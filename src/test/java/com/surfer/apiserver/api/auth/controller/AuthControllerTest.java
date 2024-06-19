/*
package com.surfer.apiserver.api.auth.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.surfer.apiserver.common.constant.CommonCode;
import com.surfer.apiserver.domain.database.entity.MemberEntity;
import com.surfer.apiserver.domain.database.repository.MemberRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import static com.surfer.apiserver.api.auth.dto.AuthDTO.SignUpRequest;
import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    WebApplicationContext webApplicationContext;
    @Autowired
    MemberRepository memberRepository;
    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();


    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(this.webApplicationContext)
                .addFilter(new CharacterEncodingFilter("UTF-8", true))
                .build();

    }


    @Nested
    @DisplayName("회원가입")
    class SignUp {
        private static final String url = "/api/v1/auth/sign-up";
        private static final String email = "test@test.com";
        private static final String password = "123456";
        private static final String nickname = "test";
        private static final String name = "test";

        private SignUpRequest setDefaultSignUpRequest() {
            SignUpRequest signUpRequest = new SignUpRequest();
            signUpRequest.setEmail(email);
            signUpRequest.setPassword(password);
            signUpRequest.setNickname(nickname);
            signUpRequest.setName(name);
            return signUpRequest;
        }

        @Test
        @DisplayName("정상 케이스")
        @Transactional
        @Rollback
        void signUpTest1() throws Exception {
            //given
            SignUpRequest signUpRequest = setDefaultSignUpRequest();

            //when
            final ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                    .post(url)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(signUpRequest)));
            MemberEntity memberEntity = memberRepository.findByEmail(email).get();

            //then
            resultActions
                    .andExpect(MockMvcResultMatchers.status().isCreated())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("register success"));

            assertThat(bCryptPasswordEncoder.matches(password, memberEntity.getPassword())).isTrue();
            assertThat(email).isEqualTo(memberEntity.getEmail());
            assertThat(nickname).isEqualTo(memberEntity.getNickname());
            assertThat(name).isEqualTo(memberEntity.getName());

        }


        @Test
        @DisplayName("비정상 케이스 - request param 부족")
        @Transactional
        @Rollback
        void signUpTest2() throws Exception {
            //given
            SignUpRequest signUpRequest = setDefaultSignUpRequest();
            signUpRequest.setEmail(null);

            //when
            final ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                    .post(url)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(signUpRequest)));
            MemberEntity memberEntity = memberRepository.findByEmail(email).orElse(null);

            //then
            resultActions
                    .andExpect(MockMvcResultMatchers.status().isBadRequest())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("INVALID_PARAMETER_ERR"))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("invalid parameter error"));

            assertThat(memberEntity).isNull();
        }





        @Test
        @DisplayName("비정상 케이스 - email 중복")
        @Transactional
        @Rollback
        void signUpTest3() throws Exception {
            //given
            SignUpRequest defaultSignUpRequest = setDefaultSignUpRequest();
            SignUpRequest emailDuplicatedRequest = setDefaultSignUpRequest();
            emailDuplicatedRequest.setNickname("not duplicated nick name");

            //when
            mockMvc.perform(MockMvcRequestBuilders
                    .post(url)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(defaultSignUpRequest)));

            MemberEntity memberEntity = memberRepository.findByEmail(defaultSignUpRequest.getEmail()).get();

            final ResultActions emailDuplicatedResult = mockMvc.perform(MockMvcRequestBuilders
                    .post(url)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(emailDuplicatedRequest)));


            //then
            emailDuplicatedResult
                    .andExpect(MockMvcResultMatchers.status().isBadRequest())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("UNIQUE_CONSTRAINT_VIOLATED"))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("unique value is duplicated"));


            MemberEntity actualMember = memberRepository.findById(memberEntity.getMemberId()).orElse(null);
            assertThat(actualMember).isNotNull();
            assertThat(actualMember.getEmail()).isEqualTo(memberEntity.getEmail());
        }

        @Test
        @DisplayName("비정상 케이스 - nickname 중복")
        @Transactional
        @Rollback
        void signUpTest4() throws Exception {
            //given
            SignUpRequest defaultSignUpRequest = setDefaultSignUpRequest();
            SignUpRequest nicknameDuplicatedRequest = setDefaultSignUpRequest();
            nicknameDuplicatedRequest.setEmail("not duplicated email");


            //when
            MemberEntity memberEntity = memberRepository.save(MemberEntity.builder()
                    .email(defaultSignUpRequest.getEmail())
                    .password(bCryptPasswordEncoder.encode(defaultSignUpRequest.getPassword()))
                    .nickname(defaultSignUpRequest.getNickname())
                    .name(defaultSignUpRequest.getName())
                    .status(CommonCode.MemberStatus.USE)
                    .build());

            final ResultActions nicknameDuplicatedResult = mockMvc.perform(MockMvcRequestBuilders
                    .post(url)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(nicknameDuplicatedRequest)));


            //then
            nicknameDuplicatedResult
                    .andExpect(MockMvcResultMatchers.status().isBadRequest())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("UNIQUE_CONSTRAINT_VIOLATED"))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("unique value is duplicated"));

            MemberEntity actualMember = memberRepository.findById(memberEntity.getMemberId()).orElse(null);
            assertThat(actualMember).isNotNull();
            assertThat(actualMember.getNickname()).isEqualTo(memberEntity.getNickname());
        }
    }


    @Test
    void signIn() {

    }

}







*/
