package com.surfer.apiserver.api.auth.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.surfer.apiserver.api.auth.service.impl.AuthServiceImpl;
import com.surfer.apiserver.domain.database.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static com.surfer.apiserver.api.auth.dto.AuthDTO.SignUpRequest;


//@ActiveProfiles("local")

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
    @Autowired
    AuthServiceImpl authService;


    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
        memberRepository.deleteAll();
    }



    @Nested
    @DisplayName("회원가입")
    class SignUp {

        @Test
        @DisplayName("정상 케이스")
        @Rollback
        void signUpTest1() throws Exception{
            //given
            String url = "/auth/sign-up";
            String email = "test@test.com";
            String password = "123456";
            String nickname = "test";
            String name = "test";
            SignUpRequest signUpRequest = new SignUpRequest();
            signUpRequest.setEmail(email);
            signUpRequest.setPassword(password);
            signUpRequest.setNickname(nickname);
            signUpRequest.setName(name);

            //when
            final ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                    .post(url)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(signUpRequest)));

            //then
            resultActions
                    .andExpect(MockMvcResultMatchers.status().isCreated())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("register success"));
        }


        @Test
        @DisplayName("비정상 케이스 - request 인수 부족")
        void signUpTest2() {
            //given
            //when
            //then
        }

    }


    @Test
    void signIn() {
    }

    @Test
    void test1() {
    }
}