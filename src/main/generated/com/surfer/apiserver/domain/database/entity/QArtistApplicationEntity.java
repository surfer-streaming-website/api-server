package com.surfer.apiserver.domain.database.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QArtistApplicationEntity is a Querydsl query type for ArtistApplicationEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QArtistApplicationEntity extends EntityPathBase<ArtistApplicationEntity> {

    private static final long serialVersionUID = -1217536167L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QArtistApplicationEntity artistApplicationEntity = new QArtistApplicationEntity("artistApplicationEntity");

    public final StringPath albumName = createString("albumName");

    public final NumberPath<Long> application_id = createNumber("application_id", Long.class);

    public final StringPath artistName = createString("artistName");

    public final StringPath authorName = createString("authorName");

    public final StringPath copyrightName = createString("copyrightName");

    public final DateTimePath<java.util.Date> createAt = createDateTime("createAt", java.util.Date.class);

    public final EnumPath<com.surfer.apiserver.common.constant.CommonCode.LocationType> locationType = createEnum("locationType", com.surfer.apiserver.common.constant.CommonCode.LocationType.class);

    public final QMemberEntity member;

    public final EnumPath<com.surfer.apiserver.common.constant.CommonCode.Sector> sector = createEnum("sector", com.surfer.apiserver.common.constant.CommonCode.Sector.class);

    public final EnumPath<com.surfer.apiserver.common.constant.CommonCode.ArtistApplicationStatus> status = createEnum("status", com.surfer.apiserver.common.constant.CommonCode.ArtistApplicationStatus.class);

    public final DateTimePath<java.util.Date> updateAt = createDateTime("updateAt", java.util.Date.class);

    public QArtistApplicationEntity(String variable) {
        this(ArtistApplicationEntity.class, forVariable(variable), INITS);
    }

    public QArtistApplicationEntity(Path<? extends ArtistApplicationEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QArtistApplicationEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QArtistApplicationEntity(PathMetadata metadata, PathInits inits) {
        this(ArtistApplicationEntity.class, metadata, inits);
    }

    public QArtistApplicationEntity(Class<? extends ArtistApplicationEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new QMemberEntity(forProperty("member")) : null;
    }

}

