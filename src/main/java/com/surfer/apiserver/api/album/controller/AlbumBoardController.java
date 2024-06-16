package com.surfer.apiserver.api.album.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.surfer.apiserver.api.album.dto.AlbumRes;
import com.surfer.apiserver.api.album.dto.AlbumReplyReq;
import com.surfer.apiserver.api.album.dto.AlbumReplyRes;
import com.surfer.apiserver.api.album.service.AlbumBoardService;
import com.surfer.apiserver.api.album.service.AlbumService;
import com.surfer.apiserver.common.response.ApiResponseCode;
import com.surfer.apiserver.common.response.BaseResponse;
import com.surfer.apiserver.common.response.RestApiResponse;
import com.surfer.apiserver.common.util.AES256Util;
import com.surfer.apiserver.domain.database.entity.AlbumEntity;
import com.surfer.apiserver.domain.database.entity.AlbumSingerEntity;
import com.surfer.apiserver.file.service.S3Service;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/album")
public class AlbumBoardController {

    @Autowired
    private AlbumBoardService albumBoardService;

    //페이지 처리를 위해 추가
    private final static int BLOCK_COUNT=5;

    private S3Service s3Service;

    private ObjectMapper objectMapper;

    private AlbumService albumService;


    /**
     * 앨범 상세정보 조회
     */
    @GetMapping("/detail/{seq}")
    @Operation(summary = "앨범 상세페이지", description = "앨범 상세페이지 조회 시 요청되는 api")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청이 성공적으로 처리되었습니다.", content = @Content(mediaType = "application/json"))
    })
    @Parameters({
            @Parameter(name = "nowPage", description = "현재 댓글 페이지", example = "1"),
            @Parameter(name = "sort", description = "정렬 기준(추천순/최신순)", example = "Like or RegDate")
    })
    public ResponseEntity<?> read(@PathVariable Long seq, @RequestParam(defaultValue = "1") int nowPage,
                                  @RequestParam(defaultValue = "regDate") String sort){
        //seq에 해당하는 album 찾기
        AlbumEntity album = albumBoardService.selectById(seq);

        //댓글 페이징 처리
        Page<AlbumReplyRes> replyEntityPage;
        if("Like".equals(sort)){
            replyEntityPage = albumBoardService.getAlbumReplyLikeList(album, nowPage);
        }else{
            replyEntityPage = albumBoardService.getAlbumReplyRegList(album, nowPage);
        }

        //앨범 가수 목록 불러오기
        List<AlbumSingerEntity> albumSingerList = albumBoardService.getAlbumSingerList(album);

        AlbumRes albumDTO = new AlbumRes(album, replyEntityPage, albumSingerList);

        //view에서 페이지 처리를 하기 위한 준비
        //int temp = (nowPage-1)%BLOCK_COUNT;
        //int startPage = nowPage-temp;

        //데이터 저장
        /*model.addAttribute("blockCount", BLOCK_COUNT);
        model.addAttribute("startPage", startPage);
        model.addAttribute("nowPage", nowPage);*/

        RestApiResponse restApiResponse = new RestApiResponse();
        restApiResponse.setResult(new BaseResponse(ApiResponseCode.SUCCESS), albumDTO);
        return new ResponseEntity<>(restApiResponse, HttpStatus.OK);
    }

    @PostMapping("/{seq}/reply")
    @Operation(summary = "앨범 상세페이지 댓글 작성", description = "댓글 작성 시 요청되는 api")
    public ResponseEntity<?> insertAlbumReply(@PathVariable Long seq, @RequestBody AlbumReplyReq albumReply){
        //security에 저장된 member 정보 조회
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String memberSeq = AES256Util.decrypt(authentication.getName());

        albumBoardService.albumReplyInsert(albumReply, Long.parseLong(memberSeq),seq);

        RestApiResponse restApiResponse = new RestApiResponse();
        restApiResponse.setResult(new BaseResponse(ApiResponseCode.SUCCESS));
        return new ResponseEntity<>(restApiResponse, HttpStatus.CREATED);
    }

    @PutMapping("/{seq}/reply/{replySeq}")
    @Operation(summary = "앨범 상세페이지 댓글 수정", description = "댓글 수정 시 요청되는 api")
    public ResponseEntity<?> updateAlbumReply(@PathVariable Long seq, @RequestBody AlbumReplyReq albumReplyReq,
                                   @PathVariable Long replySeq){
        //sercurity에 저장된 member 정보 조회
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String memberSeq = AES256Util.decrypt(authentication.getName());

        albumBoardService.albumReplyUpdate(seq, albumReplyReq, Long.parseLong(memberSeq), replySeq);

        RestApiResponse restApiResponse = new RestApiResponse();
        restApiResponse.setResult(new BaseResponse(ApiResponseCode.SUCCESS));
        return new ResponseEntity<>(restApiResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{seq}/reply/{replySeq}")
    @Operation(summary = "앨범 상세페이지 댓글 삭제", description = "댓글 삭제 시 요청되는 api")
    public ResponseEntity<?> deleteAlbumreply(@PathVariable Long seq, @PathVariable Long replySeq){
        //sercurity에 저장된 member 정보 조회
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String memberSeq = AES256Util.decrypt(authentication.getName());

        albumBoardService.albumReplyDelete(seq, Long.parseLong(memberSeq), replySeq);

        RestApiResponse restApiResponse = new RestApiResponse();
        restApiResponse.setResult(new BaseResponse(ApiResponseCode.SUCCESS));
        return new ResponseEntity<>(restApiResponse, HttpStatus.OK);
    }





}
