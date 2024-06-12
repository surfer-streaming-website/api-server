package com.surfer.apiserver.common.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.surfer.apiserver.common.response.ApiResponseCode;
import com.surfer.apiserver.common.response.BaseResponse;
import com.surfer.apiserver.common.response.RestApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;

@Component
@RequiredArgsConstructor
public class JwtAccessDeniedHandler implements AccessDeniedHandler {
    private final ObjectMapper mapper;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        System.out.println(accessDeniedException);
        RestApiResponse apiResponse = new RestApiResponse();
        apiResponse.setResult(new BaseResponse(ApiResponseCode.UNAUTHORIZED_ACCESS));
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json;charset=UTF8");
        PrintWriter writer = response.getWriter();
        String reponseString = mapper.writeValueAsString(apiResponse);
        writer.write(reponseString);
    }
}
