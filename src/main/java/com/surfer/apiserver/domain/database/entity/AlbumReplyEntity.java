package com.surfer.apiserver.domain.database.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Table(name = "album_reply")
@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AlbumReplyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "album_reply_seq")
    @SequenceGenerator(name = "album_reply_seq", allocationSize = 1, sequenceName = "album_reply_seq")
    private Long albumReplySeq;

    @Column(name = "album_reply_regdate")
    @CreationTimestamp
    private Date albumReplyRegdate;

    @Column(name = "album_reply_content")
    private String albumReplyContent;

    @Column(name = "album_reply_like")
    private int albumReplyLike;

    @Column(name = "album_reply_cordate")
    @UpdateTimestamp
    private Date albumReplyCordate;


    @Column(name = "album_reply_correct")
    @Builder.Default
    private Boolean albumReplyCorrect = false;


/*

    @Column(columnDefinition = "number(1,0) default 0")
    private Boolean albumReplyCorrect = false;
*/




    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "album_seq")
    @JsonIgnore
    private AlbumEntity albumEntity;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "member_seq")
    @JsonIgnore
    private MemberEntity memberEntity;

}
