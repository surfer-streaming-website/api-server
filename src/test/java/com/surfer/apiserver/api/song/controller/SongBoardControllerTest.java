package com.surfer.apiserver.api.song.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.surfer.apiserver.api.auth.dto.AuthDTO;
import com.surfer.apiserver.api.song.dto.SongReplyReq;
import com.surfer.apiserver.common.constant.CommonCode;
import com.surfer.apiserver.domain.database.entity.*;
import com.surfer.apiserver.domain.database.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
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
    @Autowired
    MemberAuthorityRepository memberAuthorityRepository;

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    @Autowired
    SongReplyRepository songReplyRepository;
    @Autowired
    SongSingerRepository songSingerRepository;


    private String createAccessToken(MemberEntity member) throws Exception {
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/sign-in")
                .content(objectMapper.writeValueAsString(AuthDTO.SignInRequest.builder().email(member.getEmail()).password("1234").build()))
                .contentType(MediaType.APPLICATION_JSON)
        );

        MvcResult mvcResult = resultActions.andReturn();
        Map<String, Object> stringObjectMap = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                new TypeReference<Map<String, Object>>() {
                });
        Map<String, String> data = (Map<String, String>) stringObjectMap.get("data");
        return data.get("accessToken");
    }


    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(this.webApplicationContext)
                .addFilter(new CharacterEncodingFilter("UTF-8", true))
                .build();
    }



    @Nested
    @DisplayName("곡 상세보기")
    class Read {
        private SongEntity songEntity = null;
        private MemberEntity memberEntity = null;
        private AlbumEntity albumEntity = null;
        private SongReplyEntity songReplyEntity = null;
        private SongSingerEntity songSingerEntity;
        private List<SongSingerEntity> songSingerEntityList = new ArrayList<>();
        private List<SongReplyEntity> songReplyEntityList= new ArrayList<>();


        private void init(){
            memberEntity = memberRepository.save(MemberEntity.builder()
                    .email("test1234@test.com")
                    .password(passwordEncoder.encode("1234"))
                    .nickname("test nickname")
                    .name("test name")
                    .status(CommonCode.MemberStatus.USE)
                    .build());

            memberAuthorityRepository.save(
                    MemberAuthorityEntity.builder()
                            .member(memberEntity)
                            .authority(CommonCode.MemberAuthority.ROLE_GENERAL)
                            .build());

            albumEntity = albumRepository.save(
                    AlbumEntity.builder()
                            .albumTitle("test title")
                            .agency("agency")
                            .albumContent("content")
                            .albumImage("image")
                            .albumState(1)
                            .memberEntity(memberEntity)
                            .build());

            songEntity = songRepository.save(
                    SongEntity.builder()
                            .songTitle("song title")
                            .songNumber(1)
                            .lyrics("lyrics")
                            .totalPlayedCount(0)
                            .recentlyPlayedCount(0)
                            .genre("genre")
                            .songState(true)
                            .soundSourceName("sound src")
                            .producer("작곡가/작사가/편곡자")
                            .albumEntity(albumEntity)
                            .songReplies(songReplyEntityList)
                            .songSingerEntityList(songSingerEntityList)
                            .build()
            );

            songReplyEntity = songReplyRepository.save(
                    SongReplyEntity.builder()
                            .songReplyContent("replyContent")
                            .songReplyLike(0)
                            .memberEntity(memberEntity)
                            .songEntity(songEntity)
                            .build()
            );

            songReplyEntityList.add(songReplyEntity);

            songSingerEntity = songSingerRepository.save(
                    SongSingerEntity.builder()
                            .songSingerName("singerName")
                            .songEntity(songEntity)
                            .build()
            );

            songSingerEntityList.add(songSingerEntity);

        }


        @Test
        @DisplayName("정상 케이스")
        @Transactional
        @Rollback
        void ReadTest1() throws Exception {
            //given
            init();
            final String songSeq = songEntity.getSongSeq().toString();
            final int nowPage = 1;
            final String sort = "regDate";
            final String url = "/song/detail/"+songSeq+"?nowPage="+nowPage+"&sort="+sort;
            List<SongReplyEntity> list= songReplyRepository.findAll();
            System.out.println(list);

            //when
            final ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                    .get(url)
                    .accept(MediaType.APPLICATION_JSON));

//            {"code":"OK","message":"SUCCESS","detail":null,"data":{"songSeq":1,"songTitle":"song title","songNumber":1,"songState":true,"lyrics":"lyrics","totalPlayedCount":0,"recentlyPlayedCount":0,"genre":"genre","soundSourceName":"sound src","replies":{"content":[],"pageable":{"pageNumber":0,"pageSize":10,"sort":{"empty":false,"sorted":true,"unsorted":false},"offset":0,"paged":true,"unpaged":false},"last":true,"totalPages":0,"totalElements":0,"size":10,"number":0,"sort":{"empty":false,"sorted":true,"unsorted":false},"first":true,"numberOfElements":0,"empty":true},"singers":[],"albumSeq":1,"albumTitle":"test title","producerDTO":{"composerList":["작곡가"],"lyricistList":["작사가"],"arrangerList":["편곡자"]}},"timestamp":1718246689245}

//            {"code":"OK","message":"SUCCESS","detail":null,"data":{"songSeq":1,"songTitle":"song title","songNumber":1,"songState":true,"lyrics":"lyrics","totalPlayedCount":0,"recentlyPlayedCount":0,"genre":"genre","soundSourceName":"sound src","replies":{"content":[],"pageable":{"pageNumber":0,"pageSize":10,"sort":{"empty":false,"sorted":true,"unsorted":false},"offset":0,"paged":true,"unpaged":false},"last":true,"totalPages":0,"totalElements":0,"size":10,"number":0,"sort":{"empty":false,"sorted":true,"unsorted":false},"first":true,"numberOfElements":0,"empty":true},"singers":[],"albumSeq":1,"albumTitle":"test title","producerDTO":{"composerList":["작곡가"],"lyricistList":["작사가"],"arrangerList":["편곡자"]}},"timestamp":1718257573844}

            //then
            resultActions
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("OK"))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("SUCCESS"))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.data.songSeq").value(songSeq))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.data.songTitle").value(songEntity.getSongTitle()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.data.songNumber").value(songEntity.getSongNumber()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.data.songState").value(songEntity.getSongState()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.data.lyrics").value(songEntity.getLyrics()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.data.totalPlayedCount").value(songEntity.getTotalPlayedCount()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.data.recentlyPlayedCount").value(songEntity.getRecentlyPlayedCount()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.data.soundSourceName").value(songEntity.getSoundSourceName()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.data.genre").value(songEntity.getGenre()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.data.replies.pageable.pageNumber").value(nowPage-1))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.data.replies.pageable.pageSize").value(10))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.data.replies.pageable.sort.empty").value(false))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.data.replies.pageable.sort.sorted").value(true))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.data.replies.pageable.sort.unsorted").value(false))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.data.replies.pageable.offset").value((nowPage-1)*10))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.data.replies.pageable.paged").value(true))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.data.replies.pageable.unpaged").value(false))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.data.replies.size").value(10))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.data.replies.number").value(nowPage-1))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.data.replies.sort.empty").value(false))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.data.replies.sort.sorted").value(true))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.data.replies.sort.unsorted").value(false))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.data.replies.first").value(nowPage==1))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.data.albumSeq").value(songEntity.getAlbumEntity().getAlbumSeq()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.data.albumTitle").value(songEntity.getAlbumEntity().getAlbumTitle()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.data.producerDTO.composerList").value(songEntity.getProducer().split("/")[0]))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.data.producerDTO.lyricistList").value(songEntity.getProducer().split("/")[1]))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.data.producerDTO.arrangerList").value(songEntity.getProducer().split("/")[2]))
            ;


            // 댓글 목록이 비어있지 않은 경우에만 테스트
            if (songEntity.getSongReplyEntities() != null && !songEntity.getSongReplyEntities().isEmpty()) {

                List<SongReplyEntity> replyList = songEntity.getSongReplyEntities();

                resultActions
                        .andExpect(MockMvcResultMatchers.jsonPath("$.data.replies.last").value(nowPage*10>=songEntity.getSongReplyEntities().toArray().length))
                        .andExpect(MockMvcResultMatchers.jsonPath("$.data.replies.totalPages").value((int)Math.ceil((double) songEntity.getSongReplyEntities().toArray().length/10)))
                        .andExpect(MockMvcResultMatchers.jsonPath("$.data.replies.numberOfElements").value(songEntity.getSongReplyEntities().toArray().length))
                        .andExpect(MockMvcResultMatchers.jsonPath("$.data.replies.empty").value(false))
                ;

                for (int i=0; i<replyList.size(); i++){
                    SongReplyEntity reply = replyList.get(i);
                    System.out.println("reply.getSongReplyRegdate()"+reply.getSongReplyRegdate());

                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
                    OffsetDateTime offsetFormattedDateTime = OffsetDateTime.ofInstant(
                            reply.getSongReplyRegdate().toInstant(), ZoneOffset.UTC);
                    // Format OffsetDateTime to ISO 8601 format with +00:00
                    String formattedDateTime = offsetFormattedDateTime.format(formatter);
                    // Replace Z with +00:00
                    formattedDateTime = formattedDateTime.substring(0, formattedDateTime.length() - 1) + "+00:00";

                    //corDate 비교 위해 추가
                    OffsetDateTime offsetFormattedDateTime2 = OffsetDateTime.ofInstant(
                            reply.getSongReplyCordate().toInstant(), ZoneOffset.UTC);
                    // Format OffsetDateTime to ISO 8601 format with +00:00
                    String formattedDateTime2 = offsetFormattedDateTime.format(formatter);
                    // Replace Z with +00:00
                    formattedDateTime2 = formattedDateTime2.substring(0, formattedDateTime2.length() - 1) + "+00:00";

                    System.out.println("offsetFormattedDateTime"+offsetFormattedDateTime);

                    resultActions
                            .andExpect(MockMvcResultMatchers.jsonPath("$.data.replies.content[" + i + "].songReplySeq").value(reply.getSongReplySeq()))
                            .andExpect(MockMvcResultMatchers.jsonPath("$.data.replies.content[" + i + "].songReplyRegDate").value(formattedDateTime))
                            .andExpect(MockMvcResultMatchers.jsonPath("$.data.replies.content[" + i + "].songReplyContent").value(reply.getSongReplyContent()))
                            .andExpect(MockMvcResultMatchers.jsonPath("$.data.replies.content[" + i + "].songReplyLike").value(reply.getSongReplyLike()))
                            .andExpect(MockMvcResultMatchers.jsonPath("$.data.replies.content[" + i + "].songReplyCordate").value((formattedDateTime2)))
                            .andExpect(MockMvcResultMatchers.jsonPath("$.data.replies.content[" + i + "].songReplyCorrect").value(reply.getSongReplyCorrect()))
                            .andExpect(MockMvcResultMatchers.jsonPath("$.data.replies.content[" + i + "].memberSeq").value(reply.getMemberEntity().getMemberId()))
                            .andExpect(MockMvcResultMatchers.jsonPath("$.data.replies.content[" + i + "].nickname").value(reply.getMemberEntity().getNickname()));
                }
            }

            //singers목록이 비어있지 않은 경우에만 테스트
            if(songEntity.getSongSingerEntities() != null && !songEntity.getSongSingerEntities().isEmpty()){
                List<SongSingerEntity> songSingerEntityList = songEntity.getSongSingerEntities();
                for(int i=0; i<songSingerEntityList.size();i++){
                    SongSingerEntity singer = songSingerEntityList.get(i);
                    resultActions
                            .andExpect(MockMvcResultMatchers.jsonPath("$.data.singers["+i+"].songSingerSeq").value(singer.getSongSingerSeq()))
                            .andExpect(MockMvcResultMatchers.jsonPath("$.data.singers["+i+"].songSingerName").value(singer.getSongSingerName()));
                }

            }

        }

    }

    @Nested
    @DisplayName("곡 댓글 작성")
    class insertSongReply{

        private SongEntity songEntity = null;
        private MemberEntity memberEntity = null;
        private AlbumEntity albumEntity = null;
        private SongReplyEntity songReplyEntity = null;
        private SongSingerEntity songSingerEntity;
        private List<SongSingerEntity> songSingerEntityList = new ArrayList<>();
        private List<SongReplyEntity> songReplyEntityList= new ArrayList<>();

        public void init(){

            memberEntity = memberRepository.save(MemberEntity.builder()
                    .email("test1234@test.com")
                    .password(passwordEncoder.encode("1234"))
                    .nickname("test nickname")
                    .name("test name")
                    .status(CommonCode.MemberStatus.USE)
                    .build());

            memberAuthorityRepository.save(
                    MemberAuthorityEntity.builder()
                            .member(memberEntity)
                            .authority(CommonCode.MemberAuthority.ROLE_GENERAL)
                            .build());

            albumEntity = albumRepository.save(
                    AlbumEntity.builder()
                            .albumTitle("test title")
                            .agency("agency")
                            .albumContent("content")
                            .albumImage("image")
                            .albumState(1)
                            .memberEntity(memberEntity)
                            .build());

            songEntity = songRepository.save(
                    SongEntity.builder()
                            .songTitle("song title")
                            .songNumber(1)
                            .lyrics("lyrics")
                            .totalPlayedCount(0)
                            .recentlyPlayedCount(0)
                            .genre("genre")
                            .songState(true)
                            .soundSourceName("sound src")
                            .producer("작곡가/작사가/편곡자")
                            .albumEntity(albumEntity)
                            .songReplies(songReplyEntityList)
                            .songSingerEntityList(songSingerEntityList)
                            .build()
            );


        }

        @Test
        @DisplayName("정상 케이스")
        @Transactional
        @Rollback
        void insertSongReplyTest() throws Exception{

            //given
            init();
            final String songSeq = songEntity.getSongSeq().toString();
            final String url = "/song/"+songSeq+"/reply";

            songReplyEntity = SongReplyEntity.builder()
                    .songReplyContent("replyContent")
                    .songReplyLike(0)
                    .memberEntity(memberEntity)
                    .songEntity(songEntity)
                    .build();

            String accessToken = createAccessToken(memberEntity);
            SongReplyReq songReplyReq = SongReplyReq.builder()
                    .songReplyContent("content")
                    .build();
            //when
            final ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                    .post(url)
                            .content(objectMapper.writeValueAsString(songReplyReq))
                            .header("Authorization", accessToken)
                            .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON));

            resultActions
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("OK"))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("SUCCESS"));

        }

    }



    @Test
    void updateSongReply() {
    }

    @Test
    void deleteSongReply() {
    }
}