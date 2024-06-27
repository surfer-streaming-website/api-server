package com.surfer.apiserver.api.playlist.controller;

import com.surfer.apiserver.api.album.service.AlbumService;
import com.surfer.apiserver.api.playlist.dto.PlaylistDTO;
import com.surfer.apiserver.api.playlist.service.PlaylistService;
import com.surfer.apiserver.common.response.ApiResponseCode;
import com.surfer.apiserver.common.response.BaseResponse;
import com.surfer.apiserver.common.response.RestApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URL;
import java.util.List;

@RestController
@RequestMapping("/api/v1/playlist")
@RequiredArgsConstructor
public class PlaylistController {
    private final PlaylistService playlistService;
    private final AlbumService albumService;

    /**
     * 존재하는 플레이리스트 X
     * 플레이리스트 새로 생성
     */
    @PostMapping("/{songSeq}/newPlaylist")
    public ResponseEntity<?> createNewPlaylist(@RequestBody PlaylistDTO.PlaylistGroupRequestDTO request, @PathVariable Long songSeq) {
        int result = playlistService.createNewPlaylist(request, songSeq);

        RestApiResponse restApiResponse = new RestApiResponse();
        restApiResponse.setResult(new BaseResponse(ApiResponseCode.CREATED));

        return ResponseEntity.ok().body(restApiResponse);
    }

    /**
     * 존재하는 플레이리스트 O
     * 해당 플레이리스트에 노래 추가
     */
    @PostMapping("/{songSeq}/{playlistSeq}/addSong")
    public ResponseEntity<?> insertSongIntoPlaylist(@PathVariable Long songSeq, @PathVariable Long playlistSeq) {
        int result = playlistService.insertSongIntoPlaylist(songSeq, playlistSeq);

        RestApiResponse restApiResponse = new RestApiResponse();
        restApiResponse.setResult(new BaseResponse(ApiResponseCode.CREATED));

        return ResponseEntity.ok().body(restApiResponse);
    }

    /**
     * 현재 로그인중인 사용자가 가진 모든 플레이리스트 조회
     */
    @GetMapping("/myPlaylists")
    public ResponseEntity<?> getAllPlaylists() {
        List<PlaylistDTO.PlaylistGroupResponseDTO> playlists = playlistService.getAllPlaylists();
        playlists.forEach(playlist -> {
            String imgName = playlist.getPlaylistImage();
            URL url = albumService.generateAlbumImgFileUrl(imgName);
            playlist.setPlaylistImage(url.toString());
        });

        RestApiResponse restApiResponse = new RestApiResponse();
        restApiResponse.setResult(new BaseResponse(ApiResponseCode.SUCCESS), playlists);

        return ResponseEntity.ok().body(restApiResponse);
    }

    /**
     * 해당 플레이리스트의 세부 내용 조회
     */
    @GetMapping("/myPlaylists/{playlistSeq}")
    public ResponseEntity<?> getPlaylistById(@PathVariable Long playlistSeq) {
        PlaylistDTO.PlaylistGroupResponseDTO playlist = playlistService.getPlaylistById(playlistSeq);

        String imgName = playlist.getPlaylistImage();
        URL url = albumService.generateAlbumImgFileUrl(imgName);
        playlist.setPlaylistImage(url.toString());

        for(PlaylistDTO.PlaylistTrackResponseDTO track : playlist.getTrack()) {
            String songImg = track.getSong().getAlbumImage();
            URL imgUrl = albumService.generateAlbumImgFileUrl(songImg);
            track.getSong().setAlbumImage(imgUrl.toString());
        }

        RestApiResponse restApiResponse = new RestApiResponse();
        restApiResponse.setResult(new BaseResponse(ApiResponseCode.SUCCESS), playlist);

        return ResponseEntity.ok().body(restApiResponse);
    }

    /**
     * 플레이리스트의 이름, 공개여부 변경, 태그 추가/수정/삭제
     */
    @PutMapping("/myPlaylists/{playlistSeq}")
    public ResponseEntity<?> changePlaylistNameAndIsOpen(@PathVariable Long playlistSeq, @RequestBody PlaylistDTO.PlaylistGroupRequestDTO request) {
        int result = playlistService.changePlaylist(playlistSeq, request);

        RestApiResponse restApiResponse = new RestApiResponse();
        restApiResponse.setResult(new BaseResponse(ApiResponseCode.ACCEPTED));

        return ResponseEntity.ok().body(restApiResponse);
    }

    /**
     * 플레이리스트 삭제
     */
    @DeleteMapping("/myPlaylists/{playlistSeq}")
    public ResponseEntity<?> deletePlaylistById(@PathVariable Long playlistSeq) {
        int result = playlistService.deletePlaylistById(playlistSeq);

        RestApiResponse restApiResponse = new RestApiResponse();
        restApiResponse.setResult(new BaseResponse(ApiResponseCode.ACCEPTED));

        return ResponseEntity.ok().body(restApiResponse);
    }

    /**
     * 플레이리스트에서 노래 삭제
     */
    @DeleteMapping("/myPlaylists/{playlistSeq}/{songSeq}")
    public ResponseEntity<?> deleteSongFromPlaylist(@PathVariable Long playlistSeq, @PathVariable Long songSeq) {
        int result = playlistService.deleteSongFromPlaylistById(playlistSeq, songSeq);

        RestApiResponse restApiResponse = new RestApiResponse();
        restApiResponse.setResult(new BaseResponse(ApiResponseCode.ACCEPTED));

        return ResponseEntity.ok().body(restApiResponse);
    }

    /**
     * 플레이리스트에 좋아요가 존재하는지 확인
     */
    @GetMapping("/likedPlaylists")
    public ResponseEntity<?> getLikedPlaylists() {
        List<PlaylistDTO.PlaylistLikeResponseDTO> likeLists = playlistService.likedPlaylists();

        RestApiResponse restApiResponse = new RestApiResponse();
        restApiResponse.setResult(new BaseResponse(ApiResponseCode.SUCCESS), likeLists);

        return ResponseEntity.ok().body(restApiResponse);
    }

    /**
     * 좋아요한 플레이리스트에 추가
     */
    @PostMapping("/{playlistSeq}/newLike")
    public ResponseEntity<?> insertPlaylistLike(@PathVariable Long playlistSeq) {
        int result = playlistService.insertPlaylistLikeById(playlistSeq);

        RestApiResponse restApiResponse = new RestApiResponse();
        restApiResponse.setResult(new BaseResponse(ApiResponseCode.ACCEPTED));

        return ResponseEntity.ok().body(restApiResponse);
    }

    /**
     * 좋아요한 플레이리스트에서 삭제
     */
    @DeleteMapping("/likedPlaylists/{playlistSeq}")
    public ResponseEntity<?> deletePlaylistLike(@PathVariable Long playlistSeq) {
        int result = playlistService.deletePlaylistLikeById(playlistSeq);

        RestApiResponse restApiResponse = new RestApiResponse();
        restApiResponse.setResult(new BaseResponse(ApiResponseCode.ACCEPTED));

        return ResponseEntity.ok().body(restApiResponse);
    }
}
