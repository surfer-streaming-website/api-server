package com.surfer.apiserver.domain.database.repository.custom.impl;

import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.surfer.apiserver.api.admin.dto.AdminDTO;
import com.surfer.apiserver.api.auth.dto.AuthDTO;
import com.surfer.apiserver.common.constant.CommonCode;
import com.surfer.apiserver.domain.database.entity.MemberEntity;
import com.surfer.apiserver.domain.database.entity.QArtistApplicationEntity;
import com.surfer.apiserver.domain.database.repository.custom.CustomArtistApplicationRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class CustomArtistApplicationRepositoryImpl implements CustomArtistApplicationRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<AuthDTO.GetArtistApplicationsResponse> findNotDeleteArtistApplicationByMember(Pageable pageable, MemberEntity member) {
        QArtistApplicationEntity qArtistApplicationEntity = QArtistApplicationEntity.artistApplicationEntity;
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

        return new PageImpl<>(result, pageable, count.fetchCount());
    }

    @Override
    public Page<AdminDTO.GetArtistApplicationsResponse> findPendingArtistApplication(Pageable pageable) {
        QArtistApplicationEntity qArtistApplicationEntity = QArtistApplicationEntity.artistApplicationEntity;
        List<AdminDTO.GetArtistApplicationsResponse> result = jpaQueryFactory.from(qArtistApplicationEntity)
                .where(qArtistApplicationEntity.status.eq(CommonCode.ArtistApplicationStatus.Pending))
                .limit(pageable.getPageSize())
                .offset((long) pageable.getPageSize() * pageable.getPageNumber())
                .orderBy(qArtistApplicationEntity.updateAt.desc(), qArtistApplicationEntity.createAt.desc(), qArtistApplicationEntity.authorName.asc())
                .transform(GroupBy.groupBy(qArtistApplicationEntity.application_id)
                        .list(Projections.constructor(AdminDTO.GetArtistApplicationsResponse.class
                                , qArtistApplicationEntity.application_id
                                , qArtistApplicationEntity.createAt
                                , qArtistApplicationEntity.status.stringValue()
                        )));
        JPQLQuery<Long> count = jpaQueryFactory
                .select(qArtistApplicationEntity.count())
                .from(qArtistApplicationEntity)
                .where(qArtistApplicationEntity.status.eq(CommonCode.ArtistApplicationStatus.Pending));

        return new PageImpl<>(result, pageable, count.fetchCount());
    }

}
