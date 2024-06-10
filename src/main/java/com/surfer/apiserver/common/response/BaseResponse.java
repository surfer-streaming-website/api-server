package com.surfer.apiserver.common.response;

import lombok.Data;

@Data
public class BaseResponse {
    private String code;
    private String message;
    private String detail = "";

    public BaseResponse() {

    }

    public BaseResponse(ApiResponseCode response) {
        this.code = response.getCode();
        this.message = response.getMessage();
    }

    public BaseResponse(ApiResponseCode response, String detail) {
        this.code = response.getCode();
        this.message = response.getMessage();
        this.detail = detail;
    }
}