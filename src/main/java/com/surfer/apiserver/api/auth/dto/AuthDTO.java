package com.surfer.apiserver.api.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

public class AuthDTO {
    @Data
    @Builder
    public static class TokenInfo {
        private String accessToken;
        private String refreshToken;
    }

    @Data
    public static class SignUpRequest {
        @NotEmpty(message = "email 필수값입니다.")
        @NotBlank(message = "email 공백을 허용하지 않습니다.")
        private String email;
        @NotEmpty(message = "password 필수값입니다.")
        @NotBlank(message = "password 공백을 허용하지 않습니다.")
        private String password;
        @NotEmpty(message = "nickname 필수값입니다.")
        private String nickname;
        @NotEmpty(message = "name 필수값입니다.")
        private String name;
    }

    @Data
    @Builder
    public static class SignInRequest {
        @NotBlank(message = "email 필수값입니다.")
        private String email;
        @NotBlank(message = "password 필수값입니다.")
        private String password;
    }
}
