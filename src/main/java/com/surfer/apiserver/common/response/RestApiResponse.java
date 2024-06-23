package com.surfer.apiserver.common.response;

import lombok.Data;

@Data
public class RestApiResponse {

    private String code;
    private String message;
    private String detail;
    private Object data;
    private Long timestamp;

    public RestApiResponse() {
        this.timestamp = System.currentTimeMillis();
    }

    public RestApiResponse(BaseResponse baseResponse) {
        this.code = baseResponse.getCode();
        this.message = baseResponse.getMessage();
        this.detail = baseResponse.getDetail();
        this.timestamp = System.currentTimeMillis();
    }

    public RestApiResponse(BaseResponse baseResponse, Object data) {
        this(baseResponse);
        this.data = data;
    }

    public RestApiResponse(String code, String message) {
        this.code = code;
        this.message = message;
        this.timestamp = System.currentTimeMillis();
    }

    public void setResult(BaseResponse baseResponse) {
        this.code = baseResponse.getCode();
        this.detail = baseResponse.getDetail();
        this.message = baseResponse.getMessage();
        this.timestamp = System.currentTimeMillis();
    }

    public void setResult(BaseResponse baseResponse, Object data) {
        this.code = baseResponse.getCode();
        this.message = baseResponse.getMessage();
        this.data = data;
        this.detail = baseResponse.getDetail();
        this.timestamp = System.currentTimeMillis();
    }
}
