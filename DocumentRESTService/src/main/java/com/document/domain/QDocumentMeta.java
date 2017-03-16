package com.document.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDocumentMeta is a Querydsl query type for DocumentMeta
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QDocumentMeta extends EntityPathBase<DocumentMeta> {

    private static final long serialVersionUID = 179258052L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDocumentMeta documentMeta = new QDocumentMeta("documentMeta");

    public final QAuthor author;

    public final StringPath description = createString("description");

    public final NumberPath<Long> documentID = createNumber("documentID", Long.class);

    public final StringPath location = createString("location");

    public final StringPath name = createString("name");

    public final DateTimePath<java.util.Date> uploadTImestamp = createDateTime("uploadTImestamp", java.util.Date.class);

    public QDocumentMeta(String variable) {
        this(DocumentMeta.class, forVariable(variable), INITS);
    }

    public QDocumentMeta(Path<? extends DocumentMeta> path) {
        this(path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QDocumentMeta(PathMetadata metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QDocumentMeta(PathMetadata metadata, PathInits inits) {
        this(DocumentMeta.class, metadata, inits);
    }

    public QDocumentMeta(Class<? extends DocumentMeta> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.author = inits.isInitialized("author") ? new QAuthor(forProperty("author")) : null;
    }

}

