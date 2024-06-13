package com.surfer.apiserver.api.playlist.controller;

import java.util.regex.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.surfer.apiserver.api.auth.dto.AuthDTO;
import com.surfer.apiserver.api.playlist.dto.PlaylistDTO;
import com.surfer.apiserver.api.playlist.service.PlaylistService;
import com.surfer.apiserver.common.constant.CommonCode;
import com.surfer.apiserver.domain.database.entity.MemberAuthorityEntity;
import com.surfer.apiserver.domain.database.entity.MemberEntity;
import com.surfer.apiserver.domain.database.entity.PlaylistGroupEntity;
import com.surfer.apiserver.domain.database.repository.MemberAuthorityRepository;
import com.surfer.apiserver.domain.database.repository.MemberRepository;
import com.surfer.apiserver.domain.database.repository.PlaylistGroupRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@TestPropertySource(properties = {"spring.config.location=classpath:application-local.yml"})
//@TestPropertySource(locations = "classpath:application.yml")
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@AutoConfigureMockMvc
class PlaylistContollerTest {
    @Autowired
    MemberAuthorityRepository memberAuthorityRepository;
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    WebApplicationContext webApplicationContext;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    PlaylistService playlistService;

    public static String extractToken(String input) {
        // 정규표현식 패턴
        String patternString = "accessToken=(.*?), refreshToken";
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(input);

        // 패턴 일치하는 부분 추출
        if (matcher.find()) {
            return matcher.group(1).trim();
        } else {
            return null; // 일치하는 부분이 없을 경우 null 반환
        }
    }
    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(this.webApplicationContext)
                .addFilter(new CharacterEncodingFilter("UTF-8", true))
                .build();
    }

    private void signUpMember() {
        MemberEntity memberEntity = memberRepository.save(MemberEntity.builder()
                .email("aaa@gmail.com")
                .password(passwordEncoder.encode("123456"))
                .nickname("aaa")
                .name("a")
                .status(CommonCode.MemberStatus.USE)
                .build());

        memberAuthorityRepository.save(
                MemberAuthorityEntity.builder()
                        .member(memberEntity)
                        .authority(CommonCode.MemberAuthority.ROLE_GENERAL)
                        .build());
    }

    @Test
    void createNewPlaylist() throws Exception {
        //given
        final String url = "/api/v1/playlist/newPlaylist";
        final PlaylistDTO.PlaylistGroupRequestDTO req = new PlaylistDTO.PlaylistGroupRequestDTO();
        final String loginUrl = "/api/v1/auth/sign-in";

        AuthDTO.SignInRequest request = AuthDTO.SignInRequest.builder()
                .email("aaa@gmail.com")
                .password("123456")
                .build();

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .post(loginUrl)
                .content(objectMapper.writeValueAsString(request))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
        );
        MvcResult mvcResult = resultActions.andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        Map<String, Object> stringObjectMap = objectMapper.readValue(contentAsString, new TypeReference<Map<String, Object>>() {});
        String accessToken = extractToken(stringObjectMap.get("data").toString());
        System.out.println("!!!");
        System.out.println(accessToken);


        //when
        final ResultActions resultAction = mockMvc.perform(
                MockMvcRequestBuilders
                        .post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + accessToken)
                        .content(objectMapper.writeValueAsString(req)));
        Integer result = playlistService.createNewPlaylist(req);

        //then
        resultAction
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

}