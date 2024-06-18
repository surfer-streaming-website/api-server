package com.surfer.apiserver.api.auth.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.surfer.apiserver.api.auth.dto.AuthDTO;
import com.surfer.apiserver.api.auth.dto.AuthDTO.SignUpRequest;
import com.surfer.apiserver.common.response.ApiResponseCode;
import com.surfer.apiserver.common.response.BaseResponse;
import com.surfer.apiserver.common.response.RestApiResponse;
import com.surfer.apiserver.domain.database.entity.MemberEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(properties = {"spring.config.location = classpath:application-test.yml"})
public class AuthIntegrationTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    WebApplicationContext webApplicationContext;


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
        void signUpTest1() throws Exception {
            //given
            SignUpRequest signUpRequest = setDefaultSignUpRequest();

            //when
            final ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                    .post(url)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(signUpRequest)));

            MvcResult mvcResult = resultActions.andReturn();
            String contentAsString = mvcResult.getResponse().getContentAsString();

            Map<String, Object> person = objectMapper.readValue(contentAsString, new TypeReference<Map<String, Object>>() {
            });
            System.out.println("!!!!!");
            System.out.println(person.get("data"));


            //then
            resultActions
                    .andExpect(MockMvcResultMatchers.status().isCreated())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("register success"));



            /*assertThat(bCryptPasswordEncoder.matches(password, memberEntity.getPassword())).isTrue();
            assertThat(email).isEqualTo(memberEntity.getEmail());
            assertThat(nickname).isEqualTo(memberEntity.getNickname());
            assertThat(name).isEqualTo(memberEntity.getName());*/

        }

        @Test
        @DisplayName("비정상 케이스 - 중복된 유니크값")
        void signUpTest2() throws Exception {
            //given
            SignUpRequest signUpRequest = setDefaultSignUpRequest();
//            signUpRequest.setEmail("test@gmail.com");
            //when
            mockMvc.perform(MockMvcRequestBuilders
                    .post(url)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(signUpRequest)));


            final ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                    .post(url)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(signUpRequest)));
            //then
            resultActions
                    .andExpect(MockMvcResultMatchers.status().isBadRequest())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("UNIQUE_CONSTRAINT_VIOLATED"))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("unique value is duplicated"));

            /*assertThat(bCryptPasswordEncoder.matches(password, memberEntity.getPassword())).isTrue();
            assertThat(email).isEqualTo(memberEntity.getEmail());
            assertThat(nickname).isEqualTo(memberEntity.getNickname());
            assertThat(name).isEqualTo(memberEntity.getName());*/

        }


    }

}
