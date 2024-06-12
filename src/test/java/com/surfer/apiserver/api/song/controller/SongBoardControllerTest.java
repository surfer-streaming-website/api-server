package com.surfer.apiserver.api.song.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.surfer.apiserver.api.auth.service.impl.AuthServiceImpl;
import com.surfer.apiserver.domain.database.entity.MemberEntity;
import com.surfer.apiserver.domain.database.repository.AlbumRepository;
import com.surfer.apiserver.domain.database.repository.MemberRepository;
import com.surfer.apiserver.domain.database.repository.SongRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@AutoConfigureMockMvc
class SongBoardControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    WebApplicationContext webApplicationContext;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    AlbumRepository albumRepository;
    @Autowired
    SongRepository songRepository;



    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }



    @Nested
    @DisplayName("곡 상세보기")
    class Read {

        private void init(){
            memberRepository.save(MemberEntity.builder()
                            
                    .build());
        }


        @Test
        @DisplayName("정상 케이스")
        @Transactional
        @Rollback
        void ReadTest1() throws Exception {
            //given


            //when
            //then
        }

    }

    @Test
    void insertSongReply() {
    }

    @Test
    void updateSongReply() {
    }

    @Test
    void deleteSongReply() {
    }
}