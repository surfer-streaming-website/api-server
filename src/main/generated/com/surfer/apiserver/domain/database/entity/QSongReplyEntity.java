package com.surfer.apiserver.domain.database.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSongReplyEntity is a Querydsl query type for SongReplyEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSongReplyEntity extends EntityPathBase<SongReplyEntity> {

    private static final long serialVersionUID = 1969609381L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSongReplyEntity songReplyEntity = new QSongReplyEntity("songReplyEntity");

    public final QMemberEntity memberEntity;

    public final QSongEntity songEntity;

    public final StringPath songReplyContent = createString("songReplyContent");

    public final DateTimePath<java.util.Date> songReplyCordate = createDateTime("songReplyCordate", java.util.Date.class);

    public final BooleanPath songReplyCorrect = createBoolean("songReplyCorrect");

    public final NumberPath<Integer> songReplyLike = createNumber("songReplyLike", Integer.class);

    public final DateTimePath<java.util.Date> songReplyRegdate = createDateTime("songReplyRegdate", java.util.Date.class);

    public final NumberPath<Long> songReplySeq = createNumber("songReplySeq", Long.class);

    public QSongReplyEntity(String variable) {
        this(SongReplyEntity.class, forVariable(variable), INITS);
    }

    public QSongReplyEntity(Path<? extends SongReplyEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSongReplyEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSongReplyEntity(PathMetadata metadata, PathInits inits) {
        this(SongReplyEntity.class, metadata, inits);
    }

    public QSongReplyEntity(Class<? extends SongReplyEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.memberEntity = inits.isInitialized("memberEntity") ? new QMemberEntity(forProperty("memberEntity")) : null;
        this.songEntity = inits.isInitialized("songEntity") ? new QSongEntity(forProperty("songEntity"), inits.get("songEntity")) : null;
    }

}

