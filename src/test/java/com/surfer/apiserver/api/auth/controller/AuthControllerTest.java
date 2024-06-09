package com.surfer.apiserver.api.auth.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.surfer.apiserver.api.auth.service.impl.AuthServiceImpl;
import com.surfer.apiserver.common.response.ApiResponseCode;
import com.surfer.apiserver.common.response.RestApiResponse;
import com.surfer.apiserver.domain.database.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import static com.surfer.apiserver.api.auth.dto.AuthDTO.SignUpRequest;
import static org.assertj.core.api.Assertions.assertThat;

/*@SpringBootTest
@AutoConfigureMockMvc*/
@ActiveProfiles("local")
class AuthControllerTest {

    /*@Autowired
    protected MockMvc mockMvc;
    @Autowired
    protected ObjectMapper objectMapper;
    @Autowired
    protected WebApplicationContext webApplicationContext;
    @Autowired
    protected MemberRepository memberRepository;*/
    @Mock
    AuthServiceImpl authService;

    @Mock
    MemberRepository memberRepository;

    @InjectMocks
    AuthController authController;

    ObjectMapper objectMapper = new ObjectMapper();

    AuthControllerTest() {
        MockitoAnnotations.openMocks(this);
    }


//    @BeforeEach
//    void setUp() {
//        /*this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
//        memberRepository.deleteAll();*/
//    }

    @Nested
    @DisplayName("회원가입")
    class SignUp{
        @Test
        @DisplayName("정상 케이스")
        void signUpTest1() {
            //given
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
            ResponseEntity<RestApiResponse> response = (ResponseEntity<RestApiResponse>) authController.signUp(signUpRequest);
            //then
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
            assertThat(response.getBody().getCode()).isEqualTo(ApiResponseCode.REGISTER_SUCCESS.getCode());
            assertThat(response.getBody().getMessage()).isEqualTo(ApiResponseCode.REGISTER_SUCCESS.getMessage());
        }

        @Test
        @DisplayName("비정상 케이스 - request 인수 부족")
        void signUpTest2() {
            //given
            String email = "test@test.com";
            String password = "123456";
            String nickname = "test";
            SignUpRequest signUpRequest = new SignUpRequest();
            signUpRequest.setEmail(email);
            signUpRequest.setPassword(password);
            signUpRequest.setNickname(nickname);

            //when
            ResponseEntity<RestApiResponse> response = (ResponseEntity<RestApiResponse>) authController.signUp(signUpRequest);
            //then
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
            assertThat(response.getBody().getCode()).isEqualTo(ApiResponseCode.REGISTER_SUCCESS.getCode());
            assertThat(response.getBody().getMessage()).isEqualTo(ApiResponseCode.REGISTER_SUCCESS.getMessage());
        }

        /*@DisplayName("회원가입 실패 - 이메일 중복")
        @Test
        void signUpTest2() {
            //given
            String email = "test@test.com";
            String password = "123456";
            String nickname = "test";
            String name = "test";
            SignUpRequest signUpRequest = new SignUpRequest();
            signUpRequest.setEmail(email);
            signUpRequest.setPassword(password);
            signUpRequest.setNickname(nickname);
            signUpRequest.setName(name);

            SignUpRequest duplicatedSignUpRequest = new SignUpRequest();
            duplicatedSignUpRequest.setEmail(email);
            duplicatedSignUpRequest.setPassword(password);
            duplicatedSignUpRequest.setNickname(nickname + "1");
            duplicatedSignUpRequest.setName(name);
            //when
            ResponseEntity<RestApiResponse> response = (ResponseEntity<RestApiResponse>) authController.signUp(signUpRequest);
            ResponseEntity<RestApiResponse> duplicatedResponse = (ResponseEntity<RestApiResponse>) authController.signUp(duplicatedSignUpRequest);

            //then
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
            assertThat(response.getBody().getCode()).isEqualTo(ApiResponseCode.REGISTER_SUCCESS.getCode());
            assertThat(response.getBody().getMessage()).isEqualTo(ApiResponseCode.REGISTER_SUCCESS.getMessage());

            assertThat(duplicatedResponse.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
            assertThat(duplicatedResponse.getBody().getCode()).isEqualTo(ApiResponseCode.UNIQUE_PARAMETER_VIOLATION_EMAIL.getCode());
            assertThat(duplicatedResponse.getBody().getMessage()).isEqualTo(ApiResponseCode.UNIQUE_PARAMETER_VIOLATION_EMAIL.getMessage());
        }*/
    }






    @Test
    void signIn() {
    }

    @Test
    void test1() {
    }
}