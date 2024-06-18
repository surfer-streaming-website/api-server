package com.surfer.apiserver.api.auth.integration;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.surfer.apiserver.api.auth.dto.AuthDTO;
import com.surfer.apiserver.common.response.ApiResponseCode;
import com.surfer.apiserver.common.response.BaseResponse;
import com.surfer.apiserver.common.response.RestApiResponse;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.regex.*;

import java.util.Map;

public class Note {
    @Test
    void test12313() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        /*AuthDTO.TokenInfo tokenInfo = AuthDTO.TokenInfo.builder()
                .refreshToken("qwerkjnzxcjkfnsadufhwqjer")
                .accessToken("asdkfjwiqeohrioqwejrinsadklnfujsaldfkwnqerjknasdkf")
                .build();

        ResponseEntity<RestApiResponse> restApiResponseResponseEntity = new ResponseEntity<>(new RestApiResponse(new BaseResponse(ApiResponseCode.CREATED),
                tokenInfo), HttpStatus.OK);*/
        String testString = "{\n" +
                "    \"code\": \"OK\",\n" +
                "    \"message\": \"SUCCESS\",\n" +
                "    \"detail\": null,\n" +
                "    \"data\": {\n" +
                "        \"accessToken\": \"eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJzdXJmZXIuY29tIiwic3ViIjoiYXV0aGVudGljYXRpb24uZ2V0TmFtZSgpIiwidXNlciI6IkZaOGIzQ2RFVFdHUngwZHFSMXF2Y2c9PSIsImF1dGhvcml0aWVzIjoiUk9MRV9HRU5FUkFMIiwiaWF0IjoxNzE4MjY5ODQxLCJleHAiOjE3MTgyNzM0NDF9.ZKE9Vj06agdcxIJCsyxm4oS7jx_QQencJIjo4EQrunM\",\n" +
                "        \"refreshToken\": \"33753b16-7e94-40f4-a93b-75047261d630\"\n" +
                "    },\n" +
                "    \"timestamp\": 1718269841732\n" +
                "}";

        Map<String, Object> stringObjectMap =
                objectMapper.readValue(testString, new TypeReference<Map<String, Object>>() {});
        Map<String, String> data = (Map<String, String>) stringObjectMap.get("data");
        System.out.println(data.get("accessToken"));

    }
}
