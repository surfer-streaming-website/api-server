package com.surfer.apiserver.domain.database.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "song_reply_like")
@Entity
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class SongReplyLikeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "song_reply_like_seq")
    @SequenceGenerator(name = "song_reply_like_seq", sequenceName = "song_reply_like_seq", allocationSize = 1)
    private Long songReplyLikeSeq;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "member_id")
    @JsonIgnore
    private MemberEntity memberEntity;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "song_reply_seq")
    @JsonIgnore
    private SongReplyEntity songReplyEntity;
}