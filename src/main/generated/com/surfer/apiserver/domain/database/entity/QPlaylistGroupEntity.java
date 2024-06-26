package com.surfer.apiserver.domain.database.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPlaylistGroupEntity is a Querydsl query type for PlaylistGroupEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPlaylistGroupEntity extends EntityPathBase<PlaylistGroupEntity> {

    private static final long serialVersionUID = -69765347L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPlaylistGroupEntity playlistGroupEntity = new QPlaylistGroupEntity("playlistGroupEntity");

    public final NumberPath<Integer> isOpen = createNumber("isOpen", Integer.class);

    public final QMemberEntity memberEntity;

    public final NumberPath<Long> playlistGroupSeq = createNumber("playlistGroupSeq", Long.class);

    public final StringPath playlistName = createString("playlistName");

    public final ListPath<PlaylistTagEntity, QPlaylistTagEntity> playlistTagEntities = this.<PlaylistTagEntity, QPlaylistTagEntity>createList("playlistTagEntities", PlaylistTagEntity.class, QPlaylistTagEntity.class, PathInits.DIRECT2);

    public final ListPath<PlaylistTrackEntity, QPlaylistTrackEntity> playlistTrackEntities = this.<PlaylistTrackEntity, QPlaylistTrackEntity>createList("playlistTrackEntities", PlaylistTrackEntity.class, QPlaylistTrackEntity.class, PathInits.DIRECT2);

    public final DateTimePath<java.time.LocalDateTime> regDate = createDateTime("regDate", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> updateDate = createDateTime("updateDate", java.time.LocalDateTime.class);

    public QPlaylistGroupEntity(String variable) {
        this(PlaylistGroupEntity.class, forVariable(variable), INITS);
    }

    public QPlaylistGroupEntity(Path<? extends PlaylistGroupEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPlaylistGroupEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPlaylistGroupEntity(PathMetadata metadata, PathInits inits) {
        this(PlaylistGroupEntity.class, metadata, inits);
    }

    public QPlaylistGroupEntity(Class<? extends PlaylistGroupEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.memberEntity = inits.isInitialized("memberEntity") ? new QMemberEntity(forProperty("memberEntity")) : null;
    }

}

