package com.surfer.apiserver.api.auth.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.surfer.apiserver.api.auth.dto.AuthDTO.CreateArtistApplicationRequest;
import com.surfer.apiserver.api.auth.dto.AuthDTO.SignInRequest;
import com.surfer.apiserver.api.auth.service.AuthService;
import com.surfer.apiserver.common.response.ApiResponseCode;
import com.surfer.apiserver.common.response.BaseResponse;
import com.surfer.apiserver.common.response.RestApiResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.*;

import static com.surfer.apiserver.api.auth.dto.AuthDTO.*;
import static com.surfer.apiserver.api.auth.dto.AuthDTO.SignUpRequest;
import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @InjectMocks
    AuthController authController;

    @Mock
    AuthService authService;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(authController)
                .addFilter(new CharacterEncodingFilter("UTF-8", true))
                .build();
    }

    private static void validByRestApiResponse(ResultActions actual, ResponseEntity<RestApiResponse> expect) throws Exception {
        MockHttpServletResponse actualResponse = actual.andReturn().getResponse();
        RestApiResponse actualRestApiResponse =
                objectMapper.readValue(actualResponse.getContentAsString(), RestApiResponse.class);
        RestApiResponse expectRestApiResponse =
                objectMapper.readValue(objectMapper.writeValueAsString(expect.getBody()), RestApiResponse.class);
        assertThat(HttpStatus.resolve(actualResponse.getStatus())).isEqualTo(expect.getStatusCode());
        assertThat(actualRestApiResponse.getCode()).isEqualTo(expectRestApiResponse.getCode());
        assertThat(actualRestApiResponse.getMessage()).isEqualTo(expectRestApiResponse.getMessage());
        assertThat(actualRestApiResponse.getDetail()).isEqualTo(expectRestApiResponse.getDetail());
        assertThat(objectMapper.writeValueAsString(actualRestApiResponse.getData()))
                .isEqualTo(objectMapper.writeValueAsString(expectRestApiResponse.getData()));
        assertThat(actualRestApiResponse.getTimestamp()).isNotEqualTo(expectRestApiResponse.getTimestamp());
    }

    @Nested
    @DisplayName("회원가입")
    class SignUp {
        private final String url = "/api/v1/auth/sign-up";
        private final String email = "test@test.com";
        private final String password = "123456";
        private final String nickname = "test";
        private final String name = "test";

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
            SignUpRequest defaultSignInRequest = setDefaultSignUpRequest();
            ResponseEntity<RestApiResponse> expectResponse = new ResponseEntity<>(
                    new RestApiResponse(
                            new BaseResponse(ApiResponseCode.REGISTER_SUCCESS), null), HttpStatus.CREATED);
            //when
            ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post(url)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(defaultSignInRequest)));

            //then
            validByRestApiResponse(resultActions, expectResponse);
        }

        @Test
        @DisplayName("비정상 케이스 - 인자값 null 제공")
        void signUpTest2() throws Exception {
            //given
            SignUpRequest notImportEmailRequest = setDefaultSignUpRequest();
            notImportEmailRequest.setEmail(null);
            //when
            ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post(url)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(notImportEmailRequest)));
            //then
            resultActions.andExpect(MockMvcResultMatchers.status().isBadRequest());
        }
    }

    @Nested
    @DisplayName("로그인")
    class SignIn {
        private final String url = "/api/v1/auth/sign-in";
        private final String email = "test@test.com";
        private final String password = "123456";
        private final String mockAccessToken = "access";
        private final String mockRefreshToken = "refresh";

        private SignInRequest setDefaultSignInRequest() {
            SignInRequest signInRequest = new SignInRequest();
            signInRequest.setEmail(email);
            signInRequest.setPassword(password);
            return signInRequest;
        }

        @Test
        @DisplayName("정상 케이스-email/password로 로그인")
        void signInTest1() throws Exception {
            //given
            SignInRequest defaultSignInRequest = setDefaultSignInRequest();
            TokenInfo expectTokenInfo = TokenInfo.builder()
                    .accessToken(mockAccessToken)
                    .refreshToken(mockRefreshToken)
                    .build();
            ResponseEntity<RestApiResponse> expectResponse = new ResponseEntity<>(
                    new RestApiResponse(
                            new BaseResponse(ApiResponseCode.SUCCESS), expectTokenInfo), HttpStatus.OK);
            //stub
            Mockito.when(authService.signInByEmailAndPassword(defaultSignInRequest)).thenReturn(expectTokenInfo);
            //when
            ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post(url)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(defaultSignInRequest))
            );
            //then
            validByRestApiResponse(resultActions, expectResponse);
            Mockito.verify(authService, Mockito.only()).signInByEmailAndPassword(defaultSignInRequest);
        }

        @Test
        @DisplayName("비정상 케이스 - email, password 인자값 null 제공")
        void signInTest2() throws Exception {
            //given
            SignInRequest notImportEmailRequest = setDefaultSignInRequest();
            notImportEmailRequest.setEmail(null);
            //when
            ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post(url)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(notImportEmailRequest)));
            //then
            resultActions.andExpect(MockMvcResultMatchers.status().isBadRequest());
        }

        @Test
        @DisplayName("정상 케이스 - refreshToken으로 로그인")
        void signInTest3() throws Exception {
            //given
            TokenInfo expectTokenInfo = TokenInfo.builder()
                    .accessToken(mockAccessToken)
                    .refreshToken(mockRefreshToken)
                    .build();
            ResponseEntity<RestApiResponse> expectResponse = new ResponseEntity<>(new RestApiResponse(
                    new BaseResponse(ApiResponseCode.SUCCESS), expectTokenInfo), HttpStatus.OK);
            //stub
            Mockito.when(authService.signInByRefreshToken(mockRefreshToken)).thenReturn(expectTokenInfo);
            //when
            ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post(url)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .header("refresh-token", mockRefreshToken));
            //then
            validByRestApiResponse(resultActions, expectResponse);
            Mockito.verify(authService, Mockito.only()).signInByRefreshToken(mockRefreshToken);
        }

        @Test
        @DisplayName("비정상 케이스 - refreshToken 미제공")
        void signInTest4() throws Exception {
            //given
            //when
            mockMvc.perform(MockMvcRequestBuilders.post(url)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON));
            //then
            Mockito.verify(authService, Mockito.only()).signInByEmailAndPassword(null);
        }
    }


    @Nested
    @DisplayName("가수 신청")
    class CreateArtistApplication {
        private final String url = "/api/v1/auth/artist-application";
        private final String locationType = "국내";
        private final String sector = "대중";
        private final String copyrightName = "저작물명";
        private final String albumName = "앨범명";
        private final String artistName = "가수명";
        private final String authorName = "저작자명";

        private final List<String> locationTypeList = List.of("국내", "국외");
        private final List<String> sectorList = List.of("대중", "클래식", "순수", "국악", "동요", "종교");

        private CreateArtistApplicationRequest setDefaultCreateArtistApplicationRequest() {
            CreateArtistApplicationRequest createArtistApplicationRequest = new CreateArtistApplicationRequest();
            createArtistApplicationRequest.setLocationType(locationType);
            createArtistApplicationRequest.setSector(sector);
            createArtistApplicationRequest.setCopyrightName(copyrightName);
            createArtistApplicationRequest.setAlbumName(albumName);
            createArtistApplicationRequest.setArtistName(artistName);
            createArtistApplicationRequest.setAuthorName(authorName);
            return createArtistApplicationRequest;
        }

        private List<CreateArtistApplicationRequest> setAllCreateArtistApplicationRequest() {
            List<CreateArtistApplicationRequest> createArtistApplicationRequestList = new ArrayList<>();
            for (String locationType : locationTypeList) {
                for (String sector : sectorList) {
                    CreateArtistApplicationRequest createArtistApplicationRequest = new CreateArtistApplicationRequest();
                    createArtistApplicationRequest.setLocationType(locationType);
                    createArtistApplicationRequest.setSector(sector);
                    createArtistApplicationRequest.setCopyrightName(copyrightName);
                    createArtistApplicationRequest.setAlbumName(albumName);
                    createArtistApplicationRequest.setArtistName(artistName);
                    createArtistApplicationRequest.setAuthorName(authorName);
                    createArtistApplicationRequestList.add(createArtistApplicationRequest);
                }
            }
            return createArtistApplicationRequestList;
        }

        @Test
        @DisplayName("정상 케이스 - 가수 신청")
        void createArtistApplicationTest1() throws Exception {
            //given
            List<CreateArtistApplicationRequest> createArtistApplicationRequestList = setAllCreateArtistApplicationRequest();
            for (CreateArtistApplicationRequest createArtistApplicationRequest : createArtistApplicationRequestList) {
                ResponseEntity<RestApiResponse> expectResponse = new ResponseEntity<>(
                        new RestApiResponse(
                                new BaseResponse(ApiResponseCode.CREATED), null), HttpStatus.CREATED);
                //when
                ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createArtistApplicationRequest)));

                //then
                validByRestApiResponse(resultActions, expectResponse);
            }
            Mockito.verify(authService, Mockito.times(createArtistApplicationRequestList.size())).createArtistApplication(Mockito.any());
        }

        @Test
        @DisplayName("비정상 케이스 - 가수 신청시 요청되는 요소에 null값")
        void createArtistApplicationTest2() throws Exception {
            //given
            CreateArtistApplicationRequest createArtistApplicationRequest = setDefaultCreateArtistApplicationRequest();
            createArtistApplicationRequest.setArtistName(null);
            //when
            ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post(url)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(createArtistApplicationRequest)));
            //then
            resultActions.andExpect(MockMvcResultMatchers.status().isBadRequest());
        }
    }

    @Nested
    @DisplayName("가수 신청내역 페이지로 조회")
    class GetArtistApplicationsAsPage {
        private final String url = "/api/v1/auth/artist-application";

        @Test
        @DisplayName("정상 케이스 - 가수 신청 내역 페이지로 조회")
        void getArtistApplicationsAsPageTest1() throws Exception {
            //given
            final int page = 8;
            final int size = 10;
            List<GetArtistApplicationsResponse> getArtistApplicationsResponseList = new ArrayList<>();
            for (int i = 0; i < 100; i++) {
                getArtistApplicationsResponseList.add(GetArtistApplicationsResponse.builder()
                        .artistApplicationId((long) i)
                        .createAt(new Date())
                        .build());
            }
            Page<GetArtistApplicationsResponse> getArtistApplicationsResponses
                    = new PageImpl<>(getArtistApplicationsResponseList.subList(page * size, page * size + size), PageRequest.of(page, size), getArtistApplicationsResponseList.size());
            ResponseEntity<RestApiResponse> expectResponseEntity =
                    new ResponseEntity<>(new RestApiResponse(
                            new BaseResponse(ApiResponseCode.SUCCESS), getArtistApplicationsResponses), HttpStatus.OK);
            //stub
            Mockito.when(authService.getArtistApplicationsAsPage(PageRequest.of(page, size)))
                    .thenReturn(new PageImpl<>(getArtistApplicationsResponseList.subList(page * size, page * size + size), PageRequest.of(page, size), getArtistApplicationsResponseList.size()));
            //when
            ResponseEntity<RestApiResponse> actualResponseEntity =
                    (ResponseEntity<RestApiResponse>) authController.getArtistApplicationsAsPage(PageRequest.of(page, size));
            //then
            Assertions.assertThat(actualResponseEntity.getStatusCode()).isEqualTo(expectResponseEntity.getStatusCode());
            Assertions.assertThat(objectMapper.writeValueAsString(actualResponseEntity.getBody().getData()))
                    .isEqualTo(objectMapper.writeValueAsString(expectResponseEntity.getBody().getData()));
        }


    }

    @Nested
    @DisplayName("가수 신청")
    class sample {
        @Test
        @DisplayName("비정상 케이스 - 가수 신청시 요청되는 요소에 null값")
        void sampleTest() throws Exception {
            
        }

    }

}


//            Assertions.assertThatThrownBy(() -> {
//                /*mockMvc.perform(MockMvcRequestBuilders.post(url)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(notImportEmailRequest)));*/
//                authController.signUp(notImportEmailRequest);
//            }).isInstanceOf(MethodArgumentNotValidException.class);




