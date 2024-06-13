package com.surfer.apiserver.api.playlist.controller;

import com.surfer.apiserver.api.playlist.dto.PlaylistDTO;
import com.surfer.apiserver.api.playlist.service.PlaylistService;
import com.surfer.apiserver.common.response.ApiResponseCode;
import com.surfer.apiserver.common.response.BaseResponse;
import com.surfer.apiserver.common.response.RestApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/playlist")
@RequiredArgsConstructor
public class PlaylistContoller {
    private final PlaylistService playlistService;

    /**
     * 존재하는 플레이리스트 X
     * 플레이리스트 새로 생성
     * 생성된 플레이리스트에 노래 추가
     */
    @PostMapping("/newPlaylist")
    public ResponseEntity<?> createNewPlaylist(@RequestBody PlaylistDTO.PlaylistGroupRequestDTO request) {
        int result = playlistService.createNewPlaylist(request);

        RestApiResponse restApiResponse = new RestApiResponse();
        restApiResponse.setResult(new BaseResponse(ApiResponseCode.CREATED));

        return ResponseEntity.ok().body(restApiResponse);
    }
}
