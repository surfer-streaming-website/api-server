package com.surfer.apiserver.domain.database.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSongReplyLikeEntity is a Querydsl query type for SongReplyLikeEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSongReplyLikeEntity extends EntityPathBase<SongReplyLikeEntity> {

    private static final long serialVersionUID = 1099823132L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSongReplyLikeEntity songReplyLikeEntity = new QSongReplyLikeEntity("songReplyLikeEntity");

    public final QMemberEntity memberEntity;

    public final QSongReplyEntity songReplyEntity;

    public final NumberPath<Long> songReplyLikeSeq = createNumber("songReplyLikeSeq", Long.class);

    public QSongReplyLikeEntity(String variable) {
        this(SongReplyLikeEntity.class, forVariable(variable), INITS);
    }

    public QSongReplyLikeEntity(Path<? extends SongReplyLikeEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSongReplyLikeEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSongReplyLikeEntity(PathMetadata metadata, PathInits inits) {
        this(SongReplyLikeEntity.class, metadata, inits);
    }

    public QSongReplyLikeEntity(Class<? extends SongReplyLikeEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.memberEntity = inits.isInitialized("memberEntity") ? new QMemberEntity(forProperty("memberEntity")) : null;
        this.songReplyEntity = inits.isInitialized("songReplyEntity") ? new QSongReplyEntity(forProperty("songReplyEntity"), inits.get("songReplyEntity")) : null;
    }

}

