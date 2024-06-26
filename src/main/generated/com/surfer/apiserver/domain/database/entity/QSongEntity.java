package com.surfer.apiserver.domain.database.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSongEntity is a Querydsl query type for SongEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSongEntity extends EntityPathBase<SongEntity> {

    private static final long serialVersionUID = -1514737237L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSongEntity songEntity = new QSongEntity("songEntity");

    public final QAlbumEntity albumEntity;

    public final StringPath genre = createString("genre");

    public final StringPath lyrics = createString("lyrics");

    public final StringPath producer = createString("producer");

    public final NumberPath<Integer> recentlyPlayedCount = createNumber("recentlyPlayedCount", Integer.class);

    public final NumberPath<Integer> songNumber = createNumber("songNumber", Integer.class);

    public final ListPath<SongReplyEntity, QSongReplyEntity> songReplyEntities = this.<SongReplyEntity, QSongReplyEntity>createList("songReplyEntities", SongReplyEntity.class, QSongReplyEntity.class, PathInits.DIRECT2);

    public final NumberPath<Long> songSeq = createNumber("songSeq", Long.class);

    public final ListPath<SongSingerEntity, QSongSingerEntity> songSingerEntities = this.<SongSingerEntity, QSongSingerEntity>createList("songSingerEntities", SongSingerEntity.class, QSongSingerEntity.class, PathInits.DIRECT2);

    public final BooleanPath songState = createBoolean("songState");

    public final StringPath songTitle = createString("songTitle");

    public final StringPath soundSourceName = createString("soundSourceName");

    public final NumberPath<Integer> totalPlayedCount = createNumber("totalPlayedCount", Integer.class);

    public QSongEntity(String variable) {
        this(SongEntity.class, forVariable(variable), INITS);
    }

    public QSongEntity(Path<? extends SongEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSongEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSongEntity(PathMetadata metadata, PathInits inits) {
        this(SongEntity.class, metadata, inits);
    }

    public QSongEntity(Class<? extends SongEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.albumEntity = inits.isInitialized("albumEntity") ? new QAlbumEntity(forProperty("albumEntity"), inits.get("albumEntity")) : null;
    }

}

