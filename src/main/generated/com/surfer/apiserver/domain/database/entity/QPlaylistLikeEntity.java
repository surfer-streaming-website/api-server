package com.surfer.apiserver.domain.database.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPlaylistLikeEntity is a Querydsl query type for PlaylistLikeEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPlaylistLikeEntity extends EntityPathBase<PlaylistLikeEntity> {

    private static final long serialVersionUID = 399507231L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPlaylistLikeEntity playlistLikeEntity = new QPlaylistLikeEntity("playlistLikeEntity");

    public final QMemberEntity memberEntity;

    public final QPlaylistGroupEntity playlistGroupEntity;

    public final NumberPath<Long> playlistLikeSeq = createNumber("playlistLikeSeq", Long.class);

    public final DateTimePath<java.time.LocalDateTime> regDate = createDateTime("regDate", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> updateDate = createDateTime("updateDate", java.time.LocalDateTime.class);

    public QPlaylistLikeEntity(String variable) {
        this(PlaylistLikeEntity.class, forVariable(variable), INITS);
    }

    public QPlaylistLikeEntity(Path<? extends PlaylistLikeEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPlaylistLikeEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPlaylistLikeEntity(PathMetadata metadata, PathInits inits) {
        this(PlaylistLikeEntity.class, metadata, inits);
    }

    public QPlaylistLikeEntity(Class<? extends PlaylistLikeEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.memberEntity = inits.isInitialized("memberEntity") ? new QMemberEntity(forProperty("memberEntity")) : null;
        this.playlistGroupEntity = inits.isInitialized("playlistGroupEntity") ? new QPlaylistGroupEntity(forProperty("playlistGroupEntity"), inits.get("playlistGroupEntity")) : null;
    }

}

