package com.surfer.apiserver.domain.database.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAlbumReplyLikeEntity is a Querydsl query type for AlbumReplyLikeEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAlbumReplyLikeEntity extends EntityPathBase<AlbumReplyLikeEntity> {

    private static final long serialVersionUID = 1308473096L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAlbumReplyLikeEntity albumReplyLikeEntity = new QAlbumReplyLikeEntity("albumReplyLikeEntity");

    public final QAlbumReplyEntity albumReplyEntity;

    public final NumberPath<Long> albumReplyLikeSeq = createNumber("albumReplyLikeSeq", Long.class);

    public final QMemberEntity memberEntity;

    public QAlbumReplyLikeEntity(String variable) {
        this(AlbumReplyLikeEntity.class, forVariable(variable), INITS);
    }

    public QAlbumReplyLikeEntity(Path<? extends AlbumReplyLikeEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAlbumReplyLikeEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAlbumReplyLikeEntity(PathMetadata metadata, PathInits inits) {
        this(AlbumReplyLikeEntity.class, metadata, inits);
    }

    public QAlbumReplyLikeEntity(Class<? extends AlbumReplyLikeEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.albumReplyEntity = inits.isInitialized("albumReplyEntity") ? new QAlbumReplyEntity(forProperty("albumReplyEntity"), inits.get("albumReplyEntity")) : null;
        this.memberEntity = inits.isInitialized("memberEntity") ? new QMemberEntity(forProperty("memberEntity")) : null;
    }

}

