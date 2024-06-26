package com.surfer.apiserver.domain.database.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPlaylistTrackEntity is a Querydsl query type for PlaylistTrackEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPlaylistTrackEntity extends EntityPathBase<PlaylistTrackEntity> {

    private static final long serialVersionUID = 425863401L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPlaylistTrackEntity playlistTrackEntity = new QPlaylistTrackEntity("playlistTrackEntity");

    public final QPlaylistGroupEntity playlistGroupEntity;

    public final NumberPath<Long> playlistTrackSeq = createNumber("playlistTrackSeq", Long.class);

    public final DateTimePath<java.time.LocalDateTime> regDate = createDateTime("regDate", java.time.LocalDateTime.class);

    public final QSongEntity songEntity;

    public final DateTimePath<java.time.LocalDateTime> updateDate = createDateTime("updateDate", java.time.LocalDateTime.class);

    public QPlaylistTrackEntity(String variable) {
        this(PlaylistTrackEntity.class, forVariable(variable), INITS);
    }

    public QPlaylistTrackEntity(Path<? extends PlaylistTrackEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPlaylistTrackEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPlaylistTrackEntity(PathMetadata metadata, PathInits inits) {
        this(PlaylistTrackEntity.class, metadata, inits);
    }

    public QPlaylistTrackEntity(Class<? extends PlaylistTrackEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.playlistGroupEntity = inits.isInitialized("playlistGroupEntity") ? new QPlaylistGroupEntity(forProperty("playlistGroupEntity"), inits.get("playlistGroupEntity")) : null;
        this.songEntity = inits.isInitialized("songEntity") ? new QSongEntity(forProperty("songEntity"), inits.get("songEntity")) : null;
    }

}

