package com.surfer.apiserver.domain.database.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMemberAuthorityEntity is a Querydsl query type for MemberAuthorityEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMemberAuthorityEntity extends EntityPathBase<MemberAuthorityEntity> {

    private static final long serialVersionUID = -1347875623L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMemberAuthorityEntity memberAuthorityEntity = new QMemberAuthorityEntity("memberAuthorityEntity");

    public final EnumPath<com.surfer.apiserver.common.constant.CommonCode.MemberAuthority> authority = createEnum("authority", com.surfer.apiserver.common.constant.CommonCode.MemberAuthority.class);

    public final NumberPath<Long> authorityId = createNumber("authorityId", Long.class);

    public final DateTimePath<java.util.Date> createAt = createDateTime("createAt", java.util.Date.class);

    public final QMemberEntity member;

    public final DateTimePath<java.util.Date> updateAt = createDateTime("updateAt", java.util.Date.class);

    public QMemberAuthorityEntity(String variable) {
        this(MemberAuthorityEntity.class, forVariable(variable), INITS);
    }

    public QMemberAuthorityEntity(Path<? extends MemberAuthorityEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMemberAuthorityEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMemberAuthorityEntity(PathMetadata metadata, PathInits inits) {
        this(MemberAuthorityEntity.class, metadata, inits);
    }

    public QMemberAuthorityEntity(Class<? extends MemberAuthorityEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new QMemberEntity(forProperty("member")) : null;
    }

}

