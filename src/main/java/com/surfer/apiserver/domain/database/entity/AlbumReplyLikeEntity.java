package com.surfer.apiserver.domain.database.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "album_reply_like")
@Entity
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class AlbumReplyLikeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "album_reply_like_seq")
    @SequenceGenerator(name = "album_reply_like_seq", allocationSize = 1, sequenceName = "album_reply_like_seq")
    private Long albumReplyLikeSeq;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "member_id")
    @JsonIgnore
    private MemberEntity memberEntity;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "album_reply_seq")
    @JsonIgnore
    private AlbumReplyEntity albumReplyEntity;
}