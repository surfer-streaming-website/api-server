package com.surfer.apiserver.api.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

public class AuthDTO {
    @Data
    @Builder
    public static class TokenInfo{
        private String accessToken;
        private String refreshToken;
        private Long refreshTokenExpireAt;
    }

    @Data
    public static class SignUpRequest{
        @NotBlank(message = "email 필수값입니다.")
        private String email;
        @NotBlank(message = "password 필수값입니다.")
        private String password;
    }

    @Data
    public static class SignInRequest{
        @NotBlank(message = "email 필수값입니다.")
        private String email;
        @NotBlank(message = "password 필수값입니다.")
        private String password;
    }
}
