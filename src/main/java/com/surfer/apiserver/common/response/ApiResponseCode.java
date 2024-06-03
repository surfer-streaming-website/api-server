package com.surfer.apiserver.common.response;

public enum ApiResponseCode {
    SUCCESS("OK", "SUCCESS"),
    CREATED("OK", "CREATED"),
    ACCEPTED("OK", "ACCEPTED"),
    UNKNWON("UNKNWON", "occured unknown error"),
    INVALID_API_ACCESS_TOKEN("INVALID_API_ACCESS_TOKEN", "Invalid access_token"),
    ACCESS_TOKEN_EXPIRED("ACCESS_TOKEN_EXPIRED", "access token expired"),
    INVALID_CLIENT_ID_OR_CLIENT_SECRET("INVALID_CLIENT_ID_OR_CLIENT_SECRET", "invalid client-id or client-secret"),
    INVALID_PARAMETER_ERR("INVALID_PARAMETER_ERR", "invalid parameter error"),
    //COMMON
    FAILED_SIGN_UP_USER("ERR_SIGN_UP", "failed to add new user"),
    FAILED_SIGN_IN_USER("ERR_SIGN_IN", "failed to login because userid not exist"),
    INVALID_USER_ID("INVALID_USER_ID", "failed to load user because email is not exist")
    ;

    private String code;
    private String message;


    public String getCode() {
        return this.code;
    }
    public String getMessage() {
        return this.message;
    }

    ApiResponseCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
