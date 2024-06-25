package com.surfer.apiserver.domain.database.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAlbumReplyEntity is a Querydsl query type for AlbumReplyEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAlbumReplyEntity extends EntityPathBase<AlbumReplyEntity> {

    private static final long serialVersionUID = -1996361839L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAlbumReplyEntity albumReplyEntity = new QAlbumReplyEntity("albumReplyEntity");

    public final QAlbumEntity albumEntity;

    public final StringPath albumReplyContent = createString("albumReplyContent");

    public final DateTimePath<java.util.Date> albumReplyCordate = createDateTime("albumReplyCordate", java.util.Date.class);

    public final BooleanPath albumReplyCorrect = createBoolean("albumReplyCorrect");

    public final NumberPath<Integer> albumReplyLike = createNumber("albumReplyLike", Integer.class);

    public final DateTimePath<java.util.Date> albumReplyRegdate = createDateTime("albumReplyRegdate", java.util.Date.class);

    public final NumberPath<Long> albumReplySeq = createNumber("albumReplySeq", Long.class);

    public final QMemberEntity memberEntity;

    public QAlbumReplyEntity(String variable) {
        this(AlbumReplyEntity.class, forVariable(variable), INITS);
    }

    public QAlbumReplyEntity(Path<? extends AlbumReplyEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAlbumReplyEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAlbumReplyEntity(PathMetadata metadata, PathInits inits) {
        this(AlbumReplyEntity.class, metadata, inits);
    }

    public QAlbumReplyEntity(Class<? extends AlbumReplyEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.albumEntity = inits.isInitialized("albumEntity") ? new QAlbumEntity(forProperty("albumEntity"), inits.get("albumEntity")) : null;
        this.memberEntity = inits.isInitialized("memberEntity") ? new QMemberEntity(forProperty("memberEntity")) : null;
    }

}

