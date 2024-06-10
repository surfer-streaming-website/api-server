package com.surfer.apiserver.common.exception;

import com.surfer.apiserver.common.response.ApiResponseCode;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

@Data
@EqualsAndHashCode(callSuper = false)
public class BusinessException extends RuntimeException {
    private static final long serialVersionUID = -362265179561207306L;

    private ApiResponseCode responseCode;
    private HttpStatus status;
    private String detail = "";

    public BusinessException(ApiResponseCode responseCode, HttpStatus status) {
        super(responseCode.getMessage());
        this.responseCode = responseCode;
        this.status = status;
    }

    public BusinessException(ApiResponseCode responseCode, HttpStatus status, String detail) {
        super(responseCode.getMessage());
        this.responseCode = responseCode;
        this.status = status;
        this.detail = detail;
    }
}