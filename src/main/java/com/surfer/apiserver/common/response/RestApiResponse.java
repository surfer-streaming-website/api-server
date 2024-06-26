package com.surfer.apiserver.common.response;

import lombok.Data;
import java.util.HashMap;
import java.util.Map;

@Data
public class RestApiResponse {

    private String code;
    private String message;
    private String detail;
    private Object data;
    private Long timestamp;
    private Map<String, Object> additionalData;

    public RestApiResponse() {
        this.timestamp = System.currentTimeMillis();
        this.additionalData = new HashMap<>();
    }

    public RestApiResponse(BaseResponse baseResponse) {
        this.code = baseResponse.getCode();
        this.message = baseResponse.getMessage();
        this.detail = baseResponse.getDetail();
        this.timestamp = System.currentTimeMillis();
        this.additionalData = new HashMap<>();
    }

    public RestApiResponse(BaseResponse baseResponse, Object data) {
        this(baseResponse);
        this.data = data;
    }

    public RestApiResponse(String code, String message) {
        this.code = code;
        this.message = message;
        this.timestamp = System.currentTimeMillis();
        this.additionalData = new HashMap<>();
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

    public void addData(String key, Object value) {
        this.additionalData.put(key, value);
    }
}
