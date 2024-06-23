package com.surfer.apiserver.domain.database.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSongLikeEntity is a Querydsl query type for SongLikeEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSongLikeEntity extends EntityPathBase<SongLikeEntity> {

    private static final long serialVersionUID = 634395682L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSongLikeEntity songLikeEntity = new QSongLikeEntity("songLikeEntity");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QMemberEntity member;

    public final QSongEntity song;

    public QSongLikeEntity(String variable) {
        this(SongLikeEntity.class, forVariable(variable), INITS);
    }

    public QSongLikeEntity(Path<? extends SongLikeEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSongLikeEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSongLikeEntity(PathMetadata metadata, PathInits inits) {
        this(SongLikeEntity.class, metadata, inits);
    }

    public QSongLikeEntity(Class<? extends SongLikeEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new QMemberEntity(forProperty("member")) : null;
        this.song = inits.isInitialized("song") ? new QSongEntity(forProperty("song"), inits.get("song")) : null;
    }

}

