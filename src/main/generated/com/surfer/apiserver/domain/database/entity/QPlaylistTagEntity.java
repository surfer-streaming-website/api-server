package com.surfer.apiserver.domain.database.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPlaylistTagEntity is a Querydsl query type for PlaylistTagEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPlaylistTagEntity extends EntityPathBase<PlaylistTagEntity> {

    private static final long serialVersionUID = 1403845304L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPlaylistTagEntity playlistTagEntity = new QPlaylistTagEntity("playlistTagEntity");

    public final QPlaylistGroupEntity playlistGroupEntity;

    public final NumberPath<Long> playlistTagSeq = createNumber("playlistTagSeq", Long.class);

    public final QTagEntity tagEntity;

    public QPlaylistTagEntity(String variable) {
        this(PlaylistTagEntity.class, forVariable(variable), INITS);
    }

    public QPlaylistTagEntity(Path<? extends PlaylistTagEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPlaylistTagEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPlaylistTagEntity(PathMetadata metadata, PathInits inits) {
        this(PlaylistTagEntity.class, metadata, inits);
    }

    public QPlaylistTagEntity(Class<? extends PlaylistTagEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.playlistGroupEntity = inits.isInitialized("playlistGroupEntity") ? new QPlaylistGroupEntity(forProperty("playlistGroupEntity"), inits.get("playlistGroupEntity")) : null;
        this.tagEntity = inits.isInitialized("tagEntity") ? new QTagEntity(forProperty("tagEntity")) : null;
    }

}

