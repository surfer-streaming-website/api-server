package com.surfer.apiserver.domain.database.repository.custom.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.surfer.apiserver.api.album.dto.GetLatestAlbumResponse;
import com.surfer.apiserver.domain.database.entity.AlbumEntity;
import com.surfer.apiserver.domain.database.entity.QAlbumEntity;
import com.surfer.apiserver.domain.database.entity.QAlbumSingerEntity;
import lombok.RequiredArgsConstructor;
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
}
