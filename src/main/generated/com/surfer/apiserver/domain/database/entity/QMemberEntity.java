package com.surfer.apiserver.domain.database.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMemberEntity is a Querydsl query type for MemberEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMemberEntity extends EntityPathBase<MemberEntity> {

    private static final long serialVersionUID = -1160132944L;

    public static final QMemberEntity memberEntity = new QMemberEntity("memberEntity");

    public final StringPath email = createString("email");

    public final ListPath<MemberAuthorityEntity, QMemberAuthorityEntity> memberAuthorityEntities = this.<MemberAuthorityEntity, QMemberAuthorityEntity>createList("memberAuthorityEntities", MemberAuthorityEntity.class, QMemberAuthorityEntity.class, PathInits.DIRECT2);

    public final NumberPath<Long> memberId = createNumber("memberId", Long.class);

    public final StringPath name = createString("name");

    public final StringPath nickname = createString("nickname");

    public final StringPath password = createString("password");

    public final StringPath refreshToken = createString("refreshToken");

    public final NumberPath<Long> refreshTokenExpiredAt = createNumber("refreshTokenExpiredAt", Long.class);

    public final DateTimePath<java.util.Date> registerAt = createDateTime("registerAt", java.util.Date.class);

    public final EnumPath<com.surfer.apiserver.common.constant.CommonCode.MemberStatus> status = createEnum("status", com.surfer.apiserver.common.constant.CommonCode.MemberStatus.class);

    public final DateTimePath<java.util.Date> updateAt = createDateTime("updateAt", java.util.Date.class);

    public QMemberEntity(String variable) {
        super(MemberEntity.class, forVariable(variable));
    }

    public QMemberEntity(Path<? extends MemberEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMemberEntity(PathMetadata metadata) {
        super(MemberEntity.class, metadata);
    }

}

