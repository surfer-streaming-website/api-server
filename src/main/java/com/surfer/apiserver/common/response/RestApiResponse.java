package com.surfer.apiserver.common.response;

import lombok.Data;

@Data
public class RestApiResponse {

    //BaseResponse result;
    String code;
    String message;
    String detail;
    Object data;
    private Long timestamp;

    public RestApiResponse() {
    }

    public RestApiResponse(BaseResponse baseResponse, Object data) {
        //this.result = baseResponse;
        this.code = baseResponse.getCode();
        this.message = baseResponse.getMessage();
        this.data = data;
        this.timestamp = System.currentTimeMillis();
    }

    public void setResult(BaseResponse baseResponse) {
        this.code = baseResponse.getCode();
        this.message = baseResponse.getMessage();
        this.timestamp = System.currentTimeMillis();
    }

    public void setResult(BaseResponse baseResponse, Object data) {
        this.code = baseResponse.getCode();
        this.message = baseResponse.getMessage();
        this.data = data;
        this.timestamp = System.currentTimeMillis();
    }
}