package com.surfer.apiserver.domain.database.repository.custom.impl;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.surfer.apiserver.api.album.dto.GetAlbumListAllAdminResponse;
import com.surfer.apiserver.api.album.dto.GetLatestAlbumResponse;
import com.surfer.apiserver.common.exception.BusinessException;
import com.surfer.apiserver.common.response.ApiResponseCode;
import com.surfer.apiserver.domain.database.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CustomAlbumRepositoryImpl {
    private final JPAQueryFactory jpaQueryFactory;

    public List<GetLatestAlbumResponse> getLatestAlbum() {

        List<AlbumEntity> fetch = jpaQueryFactory
                .selectFrom(QAlbumEntity.albumEntity)
                .orderBy(QAlbumEntity.albumEntity.albumRegDate.desc())
                .limit(30)
                .fetch();
        List<GetLatestAlbumResponse> responses = new ArrayList<>();
        fetch.forEach(albumEntity -> {
            String singer = jpaQueryFactory.select(QAlbumSingerEntity.albumSingerEntity.albumSingerName)
                    .from(QAlbumSingerEntity.albumSingerEntity)
                    .where(QAlbumSingerEntity.albumSingerEntity.albumEntity.albumSeq.eq(albumEntity.getAlbumSeq()))
                    .fetchOne();
            responses.add(new GetLatestAlbumResponse(albumEntity.getAlbumImage(), albumEntity.getAlbumTitle(), albumEntity.getAlbumSeq(), singer));
        });
        return responses;
    }

    public List<GetAlbumListAllAdminResponse> getAlbumListAllAdmin() {
        List<AlbumEntity> fetch = jpaQueryFactory.selectFrom(QAlbumEntity.albumEntity)
                .where(QAlbumEntity.albumEntity.albumState.ne(1))
                .fetch();
        if(fetch.isEmpty()) throw new BusinessException(ApiResponseCode.NOT_FOUND, HttpStatus.OK);
        List<GetAlbumListAllAdminResponse> responses = new ArrayList<>();
        fetch.forEach(albumEntity -> {
            AlbumSingerEntity albumSingerEntity = jpaQueryFactory.selectFrom(QAlbumSingerEntity.albumSingerEntity)
                    .where(QAlbumSingerEntity.albumSingerEntity.albumEntity.albumSeq.eq(albumEntity.getAlbumSeq())
                            .and(QAlbumSingerEntity.albumSingerEntity.albumSingerName.isNotNull()))
                    .fetchOne();


            responses.add(new GetAlbumListAllAdminResponse(albumEntity.getAlbumSeq(), albumEntity.getAlbumTitle(),
                    albumSingerEntity.getAlbumSingerName(), albumEntity.getAlbumRegDate(), albumEntity.getAlbumState()));
        });
        return responses;
    }

    public void upDateAlbumAdmin(Long album, int albumState){
        jpaQueryFactory.update(QAlbumEntity.albumEntity)
                .set(QAlbumEntity.albumEntity.albumState, albumState)
                .where(QAlbumEntity.albumEntity.albumSeq.eq(album))
                .execute();
    }
}
