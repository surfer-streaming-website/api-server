package com.surfer.apiserver.domain.database.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSongSingerEntity is a Querydsl query type for SongSingerEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSongSingerEntity extends EntityPathBase<SongSingerEntity> {

    private static final long serialVersionUID = -1387569593L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSongSingerEntity songSingerEntity = new QSongSingerEntity("songSingerEntity");

    public final QSongEntity songEntity;

    public final StringPath songSingerName = createString("songSingerName");

    public final NumberPath<Long> songSingerSeq = createNumber("songSingerSeq", Long.class);

    public QSongSingerEntity(String variable) {
        this(SongSingerEntity.class, forVariable(variable), INITS);
    }

    public QSongSingerEntity(Path<? extends SongSingerEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSongSingerEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSongSingerEntity(PathMetadata metadata, PathInits inits) {
        this(SongSingerEntity.class, metadata, inits);
    }

    public QSongSingerEntity(Class<? extends SongSingerEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.songEntity = inits.isInitialized("songEntity") ? new QSongEntity(forProperty("songEntity"), inits.get("songEntity")) : null;
    }

}

