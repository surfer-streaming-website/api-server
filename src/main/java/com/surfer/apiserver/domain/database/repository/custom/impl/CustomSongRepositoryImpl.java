package com.surfer.apiserver.domain.database.repository.custom.impl;
import java.util.ArrayList;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.surfer.apiserver.api.song.dto.GetSongRankResponse;
import com.surfer.apiserver.domain.database.repository.custom.CustomSongRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static com.surfer.apiserver.domain.database.entity.QSongEntity.songEntity;
import static com.surfer.apiserver.domain.database.entity.QSongSingerEntity.songSingerEntity;

@Repository
@RequiredArgsConstructor
public class CustomSongRepositoryImpl implements CustomSongRepository {
    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public List<GetSongRankResponse> getSongRank() {
//        List<Tuple> fetch = jpaQueryFactory
//                .select(songEntity.songTitle, songSingerEntity.songSingerName,
//                        songEntity.songSeq, songEntity.recentlyPlayedCount)
//                .from(songEntity)
//                .innerJoin(songSingerEntity).on(songEntity.songSeq.eq(songSingerEntity.songEntity.songSeq))
//                .groupBy(songEntity.songTitle, songSingerEntity.songSingerName,
//                        songEntity.songSeq, songEntity.recentlyPlayedCount) // 모든 SELECT 절에 포함된 필드들을 GROUP BY 절에 추가
//                .orderBy(songEntity.recentlyPlayedCount.desc())
//                .limit(10)
//                .fetch();
        List<Tuple> fetch = jpaQueryFactory
                .select(songEntity.songTitle, songEntity.songSeq, songEntity.albumEntity.albumSeq)
                .from(songEntity)
                .orderBy(songEntity.recentlyPlayedCount.desc())
                .limit(10)
                .fetch();
        List<GetSongRankResponse> getSongRankResponses = new ArrayList<>();
        AtomicInteger rank = new AtomicInteger();
        fetch.forEach(tuple -> {
            Long songSeq = tuple.get(songEntity.songSeq);
            String songTitle = tuple.get(songEntity.songTitle);
            List<String> singerNames = jpaQueryFactory
                    .select(songSingerEntity.songSingerName)
                    .from(songSingerEntity)
                    .where(songSingerEntity.songEntity.songSeq.eq(songSeq))
                    .limit(1)
                    .fetch();
            Long albumSeq = tuple.get(songEntity.albumEntity.albumSeq);
            String songSingerName = singerNames.isEmpty() ? null : singerNames.get(0);
            getSongRankResponses.add(new GetSongRankResponse(songTitle, songSingerName, songSeq, rank.get(), null, albumSeq));
            rank.addAndGet(1);
        });

        return getSongRankResponses;
    }
}

/*
* QArtistApplicationEntity qArtistApplicationEntity = QArtistApplicationEntity.artistApplicationEntity;
        List<AuthDTO.GetArtistApplicationsResponse> result = jpaQueryFactory.from(qArtistApplicationEntity)
                .where(qArtistApplicationEntity.status.ne(CommonCode.ArtistApplicationStatus.Deleted)
                        .and(qArtistApplicationEntity.member.eq(member)))
                .limit(pageable.getPageSize())
                .offset((long) pageable.getPageSize() * pageable.getPageNumber())
                .orderBy(qArtistApplicationEntity.updateAt.desc(), qArtistApplicationEntity.createAt.desc(), qArtistApplicationEntity.authorName.asc())
                .transform(GroupBy.groupBy(qArtistApplicationEntity.application_id)
                        .list(Projections.constructor(AuthDTO.GetArtistApplicationsResponse.class
                                , qArtistApplicationEntity.application_id
                                , qArtistApplicationEntity.createAt
                                , qArtistApplicationEntity.status.stringValue()
                        )));
        JPQLQuery<Long> count = jpaQueryFactory
                .select(qArtistApplicationEntity.count())
                .from(qArtistApplicationEntity)
                .where(qArtistApplicationEntity.member.eq(member));
        result.forEach(entity ->
            entity.setStatus(CommonCode.ArtistApplicationStatus.valueOf(entity.getStatus()).getDesc()));
        return new PageImpl<>(result, pageable, count.fetchCount());
*
* */