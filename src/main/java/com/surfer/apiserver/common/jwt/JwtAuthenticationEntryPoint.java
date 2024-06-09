package com.surfer.apiserver.common.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.surfer.apiserver.common.response.ApiResponseCode;
import com.surfer.apiserver.common.response.BaseResponse;
import com.surfer.apiserver.common.response.RestApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private final ObjectMapper mapper;
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json;charset=UTF8");
        RestApiResponse apiResponse = new RestApiResponse();
        String exception = (String) request.getAttribute("exception");
        if(exception == null) {
            apiResponse.setResult(new BaseResponse(ApiResponseCode.INVALID_CLIENT_ID_OR_CLIENT_SECRET));
        }else if( exception.equals("invalid")){
            apiResponse.setResult(new BaseResponse(ApiResponseCode.INVALID_API_ACCESS_TOKEN));
        }else if(exception.equals("expire")){
            apiResponse.setResult(new BaseResponse(ApiResponseCode.ACCESS_TOKEN_EXPIRED));
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }else{
            apiResponse.setResult(new BaseResponse(ApiResponseCode.UNKNWON));
        }
        PrintWriter writer = response.getWriter();
        String reponseString = mapper.writeValueAsString(apiResponse);
        writer.write(reponseString);
    }
}
