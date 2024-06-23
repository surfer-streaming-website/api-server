package com.surfer.apiserver.domain.database.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAlbumSingerEntity is a Querydsl query type for AlbumSingerEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAlbumSingerEntity extends EntityPathBase<AlbumSingerEntity> {

    private static final long serialVersionUID = 221374171L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAlbumSingerEntity albumSingerEntity = new QAlbumSingerEntity("albumSingerEntity");

    public final QAlbumEntity albumEntity;

    public final StringPath albumSingerName = createString("albumSingerName");

    public final NumberPath<Long> albumSingerSeq = createNumber("albumSingerSeq", Long.class);

    public QAlbumSingerEntity(String variable) {
        this(AlbumSingerEntity.class, forVariable(variable), INITS);
    }

    public QAlbumSingerEntity(Path<? extends AlbumSingerEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAlbumSingerEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAlbumSingerEntity(PathMetadata metadata, PathInits inits) {
        this(AlbumSingerEntity.class, metadata, inits);
    }

    public QAlbumSingerEntity(Class<? extends AlbumSingerEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.albumEntity = inits.isInitialized("albumEntity") ? new QAlbumEntity(forProperty("albumEntity"), inits.get("albumEntity")) : null;
    }

}

