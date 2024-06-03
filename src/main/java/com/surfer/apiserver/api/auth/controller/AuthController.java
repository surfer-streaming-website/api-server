package com.surfer.apiserver.api.auth.controller;

import com.surfer.apiserver.api.auth.service.AuthService;
import com.surfer.apiserver.common.response.ApiResponseCode;
import com.surfer.apiserver.common.response.BaseResponse;
import com.surfer.apiserver.common.response.RestApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.surfer.apiserver.api.auth.dto.AuthDTO.*;

@RestController
@RequestMapping("/api/v3/auth")
@Slf4j
@RequiredArgsConstructor
@Tag(name = "Auth", description = "Auth API")
public class AuthController {
    private final AuthService authService;
    
    @PostMapping(value = "/sign-up", consumes = "application/json", produces = "application/json")
    @ResponseBody
    @Operation(summary = "회원 가입", description = "회원 가입 시 요청되는 api")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "1000", description = "요청에 성공하였습니다.", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "2002", description = "이미 가입된 계정입니다.", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "4000", description = "데이터베이스 연결에 실패하였습니다.", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "4011", description = "비밀번호 암호화에 실패하였습니다.", content = @Content(mediaType = "application/json"))
    })
    @Parameters({
            @Parameter(name = "email", description = "이메일", example = "chrome123@naver.com"),
            @Parameter(name = "password", description = "6자~12자 이내", example = "abcd1234"),
    })
    public ResponseEntity<?> signUp(@Valid @RequestBody SignUpRequest signUpRequest) {
        try {
            log.debug("Request data : " + signUpRequest.toString());
            authService.signUp(signUpRequest);
            RestApiResponse restApiResponse = new RestApiResponse();
            restApiResponse.setResult(new BaseResponse(ApiResponseCode.SUCCESS));
            return new ResponseEntity<>(restApiResponse, HttpStatus.OK);
        }catch(Exception e){
            log.error(e.getMessage());
            throw e;
        }
    }

    @PostMapping(value = "/sign-in", consumes = "application/json", produces = "application/json")
    @ResponseBody
    @Operation(summary = "로그인", description = "로그인 시 요청되는 api")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "OK", description = "요청에 성공하였습니다.", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "4000", description = "데이터베이스 연결에 실패하였습니다.", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "4011", description = "비밀번호 암호화에 실패하였습니다.", content = @Content(mediaType = "application/json"))
    })
    @Parameters({
            @Parameter(name = "email", description = "이메일", example = "chrome123@naver.com"),
            @Parameter(name = "password", description = "6자~12자 이내", example = "abcd1234"),
    })
    public ResponseEntity<?> signIn(@Valid @RequestBody SignInRequest signInRequest) {
        try {
            TokenInfo token = authService.signIn(signInRequest);
            RestApiResponse restApiResponse = new RestApiResponse();
            restApiResponse.setResult(new BaseResponse(ApiResponseCode.SUCCESS), token);
            return new ResponseEntity<>(restApiResponse, HttpStatus.OK);
        }catch(Exception e){
            log.error(e.getMessage());
            throw e;
        }
    }

    /*@GetMapping(value = "/test")
    @ResponseBody
    //@PreAuthorize("hasAnyRole('ROLE_STAFF', 'ROLE_ADMIN')")
    public ResponseEntity<?> tokentest(Pageable pageable) {
        RestApiResponse restApiResponse = new RestApiResponse();
        Page<UserResponse> response = authService.getUsers(pageable);
        restApiResponse.setResult(new BaseResponse(ApiResponseCode.SUCCESS), response);
        return new ResponseEntity<>(restApiResponse, HttpStatus.OK);
    }*/
}