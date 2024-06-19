package com.surfer.apiserver.domain.database.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Table(name = "song_reply")
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SongReplyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "song_reply_seq")
    @SequenceGenerator(name = "song_reply_seq", allocationSize = 1, sequenceName = "song_reply_seq")
    private Long songReplySeq;

    @Column(name = "song_reply_regdate")
    @CreationTimestamp
    private Date songReplyRegdate;

    @Column(name = "song_reply_content", nullable = false)
    private String songReplyContent;

    @Column(name = "song_reply_like")
    private int songReplyLike;

    @Column(name = "song_reply_cordate")
    @UpdateTimestamp
    private Date songReplyCordate;

    @Column(name = "song_reply_correct")
    @Builder.Default
    private Boolean songReplyCorrect=false;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "member_seq")
    @JsonIgnore
    private MemberEntity memberEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "song_seq")
    @JsonIgnore
    private SongEntity songEntity;
}
