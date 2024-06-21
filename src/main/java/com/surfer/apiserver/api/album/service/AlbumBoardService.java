package com.surfer.apiserver.api.album.service;

import com.surfer.apiserver.api.album.dto.AlbumReplyReq;
import com.surfer.apiserver.api.album.dto.AlbumReplyRes;
import com.surfer.apiserver.domain.database.entity.AlbumEntity;
import com.surfer.apiserver.domain.database.entity.AlbumReplyEntity;
import com.surfer.apiserver.domain.database.entity.AlbumSingerEntity;
import org.springframework.data.domain.Page;

import java.util.List;

public interface AlbumBoardService {
    /**
     * 앨범 상세보기 페이지 전체 조회
     */
    AlbumEntity selectById(Long seq);

    /**
     * 앨범 댓글 등록
     */
    void albumReplyInsert(AlbumReplyReq albumReplyReq, Long memberSeq, Long albumSeq);

    /**
     * 앨범 댓글 수정
     */
    void albumReplyUpdate(Long albumSeq, AlbumReplyReq albumReplyReq, Long memberSeq, Long replySeq);

    /**
     * 앨범 댓글 삭제
     */
    void albumReplyDelete(Long albumSeq, Long memberSeq, Long replySeq);

    /**
     * 앨범 댓글 페이징 처리(최신순)
     */
    public Page<AlbumReplyRes> getAlbumReplyRegList(AlbumEntity albumEntity, int nowPage);

    /**
     * 앨범 댓글 페이징 처리(추천순)
     */
    public Page<AlbumReplyRes> getAlbumReplyLikeList(AlbumEntity albumEntity, int nowPage);

    /**
     * 앨범 가수 찾기
     */
    public List<AlbumSingerEntity> getAlbumSingerList(AlbumEntity albumEntity);

    /**
     * 곡 댓글 좋아요 확인
     */
    Boolean albumReplyLike(Long songSeq, Long memberSeq, Long replySeq);

    /**
     * 앨범 댓글 좋아요 등록
     */
    void albumReplyLikeInsert(Long albumSeq, Long memberSeq, Long replySeq);

    /**
     * 앨범 댓글 추천수 업데이트
     */
    void albumReplyLikeUpdate(Long replySeq, Boolean isLike);

    /**
     * 앨범 댓글 좋아요 삭제
     */
    void albumReplyLikeDelete(Long albumSeq, Long memberSeq, Long replySeq);


}
