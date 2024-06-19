package com.surfer.apiserver.api.admin.controller;

import com.surfer.apiserver.api.admin.dto.AdminDTO.ManageArtistApplicationRequest;
import com.surfer.apiserver.api.admin.service.AdminService;
import com.surfer.apiserver.common.response.ApiResponseCode;
import com.surfer.apiserver.common.response.BaseResponse;
import com.surfer.apiserver.common.response.RestApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin")
@Slf4j
@RequiredArgsConstructor
@Tag(name = "Admin", description = "Admin API 관리자 관련 API")
public class AdminController {
    private final AdminService adminService;

    @GetMapping(value = "/artist-application", produces = "application/json")
    @Operation(summary = "가수 신청 내역 페이지로 조회", description = "사용자가 작성한 가수 신청서를 관리자가 조회하는 api")
    public ResponseEntity<?> getArtistApplicationsAsPage(Pageable pageable) {
        RestApiResponse restApiResponse =
                new RestApiResponse(
                        new BaseResponse(ApiResponseCode.SUCCESS),
                        adminService.getArtistApplicationsAsPage(pageable));
        return new ResponseEntity<>(restApiResponse, HttpStatus.OK);
    }

    @GetMapping(value = "/artist-application/{id}", produces = "application/json")
    @Operation(summary = "가수 신청내역 상세 조회", description = "사용자가 가수 신청한 내역을 상세 조회하는 api")
    public ResponseEntity<?> getArtistApplication(@PathVariable Long id) {
        RestApiResponse restApiResponse =
                new RestApiResponse(new BaseResponse(ApiResponseCode.SUCCESS), adminService.getArtistApplication(id));
        return new ResponseEntity<>(restApiResponse, HttpStatus.OK);
    }


    @PutMapping(value = "/artist-application/{id}", consumes = "application/json", produces = "application/json")
    @Operation(summary = "가수 신청 처리", description = "사용자가 작성한 가수 신청서를 관리자가 처리하는 api")
    public ResponseEntity<?> manageArtistApplication(@Valid @RequestBody ManageArtistApplicationRequest manageArtistApplicationRequest,
                                                     @PathVariable Long id) {
        adminService.manageArtistApplication(manageArtistApplicationRequest, id);
        RestApiResponse restApiResponse = new RestApiResponse(new BaseResponse(ApiResponseCode.SUCCESS), null);
        return new ResponseEntity<>(restApiResponse, HttpStatus.OK);
    }

}
