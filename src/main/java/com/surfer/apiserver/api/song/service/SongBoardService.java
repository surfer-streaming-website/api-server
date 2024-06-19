package com.surfer.apiserver.api.song.service;

import com.surfer.apiserver.api.song.dto.ProducerDTO;
import com.surfer.apiserver.api.song.dto.SongReplyReq;
import com.surfer.apiserver.api.song.dto.SongReplyRes;
import com.surfer.apiserver.domain.database.entity.SongEntity;
import com.surfer.apiserver.domain.database.entity.SongReplyEntity;
import com.surfer.apiserver.domain.database.entity.SongSingerEntity;
import org.springframework.data.domain.Page;

import java.util.List;

public interface SongBoardService {
    /**
     * 앨범 상세페이지 전체 조회
     */
    SongEntity selectById(Long seq);

    /**
     * 곡 댓글 등록
     */
    void songReplyInsert(SongReplyReq replyEntity, Long memberSeq, Long songSeq);

    /**
     * 곡 댓글 수정
     */
    void songReplyUpdate(Long songSeq, SongReplyReq songReplyReq, Long memberSeq, Long replySeq);

    /**
     * 곡 댓글 삭제
     */
    void songReplyDelete(Long songSeq, Long memberSeq, Long replySeq);

    /**
     * 곡 댓글 페이징 처리(최신순)
     */
    public Page<SongReplyRes> getSongReplyRegList(SongEntity songEntity, int nowPage);

    /**
     * 곡 댓글 페이징 처리(추천순)
     */
    public Page<SongReplyRes> getSongReplyLikeList(SongEntity songEntity, int nowPage);

    /**
     * 곡_가수 찾기
     */
    public List<SongSingerEntity> getSongSingerList(SongEntity songEntity);

    /**
     * 작사가, 작곡가, 편곡자 분리하기
     */
    public ProducerDTO getProducer(String producer);

    /**
     * 곡 댓글 좋아요 확인
     */
    Boolean songReplyLike(Long songSeq, Long memberSeq, Long replySeq);

    /**
     * 곡 댓글 좋아요 등록
     */
    void songReplyLikeInsert(Long songSeq, Long memberSeq, Long replySeq);

    /**
     * 곡 댓글 추천수 업데이트
     */
    void songReplyLikeUpdate(Long replySeq, Boolean isLike);

    /**
     * 곡 댓글 좋아요 삭제
     */
    void songReplyLikeDelete(Long songSeq, Long memberSeq, Long replySeq);
}
