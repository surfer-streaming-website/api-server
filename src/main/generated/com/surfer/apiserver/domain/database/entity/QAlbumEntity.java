package com.surfer.apiserver.domain.database.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAlbumEntity is a Querydsl query type for AlbumEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAlbumEntity extends EntityPathBase<AlbumEntity> {

    private static final long serialVersionUID = 1963227455L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAlbumEntity albumEntity = new QAlbumEntity("albumEntity");

    public final StringPath agency = createString("agency");

    public final StringPath albumContent = createString("albumContent");

    public final StringPath albumImage = createString("albumImage");

    public final DatePath<java.util.Date> albumRegDate = createDate("albumRegDate", java.util.Date.class);

    public final ListPath<AlbumReplyEntity, QAlbumReplyEntity> albumReplyEntities = this.<AlbumReplyEntity, QAlbumReplyEntity>createList("albumReplyEntities", AlbumReplyEntity.class, QAlbumReplyEntity.class, PathInits.DIRECT2);

    public final NumberPath<Long> albumSeq = createNumber("albumSeq", Long.class);

    public final ListPath<AlbumSingerEntity, QAlbumSingerEntity> albumSingerEntities = this.<AlbumSingerEntity, QAlbumSingerEntity>createList("albumSingerEntities", AlbumSingerEntity.class, QAlbumSingerEntity.class, PathInits.DIRECT2);

    public final NumberPath<Integer> albumState = createNumber("albumState", Integer.class);

    public final StringPath albumTitle = createString("albumTitle");

    public final QMemberEntity memberEntity;

    public final DateTimePath<java.util.Date> releaseDate = createDateTime("releaseDate", java.util.Date.class);

    public final ListPath<SongEntity, QSongEntity> songEntities = this.<SongEntity, QSongEntity>createList("songEntities", SongEntity.class, QSongEntity.class, PathInits.DIRECT2);

    public QAlbumEntity(String variable) {
        this(AlbumEntity.class, forVariable(variable), INITS);
    }

    public QAlbumEntity(Path<? extends AlbumEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAlbumEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAlbumEntity(PathMetadata metadata, PathInits inits) {
        this(AlbumEntity.class, metadata, inits);
    }

    public QAlbumEntity(Class<? extends AlbumEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.memberEntity = inits.isInitialized("memberEntity") ? new QMemberEntity(forProperty("memberEntity")) : null;
    }

}

