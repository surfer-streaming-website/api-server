package com.surfer.apiserver.domain.database.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.List;

@Table(name = "album")
@Entity
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Setter
public class AlbumEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "album_id_seq")
    @SequenceGenerator(name = "album_id_seq", sequenceName = "album_id_seq", allocationSize = 1)
    @Column(name = "album_seq")
    private Long albumSeq;

    @Column(name = "album_title", nullable = false)
    private String albumTitle;

    @Column(name = "release_date")
    private Date releaseDate;

    @Column(name = "agency")
    private String agency;

    @Column(name = "album_content")
    private String albumContent;

    @Column(name = "album_image")
    private String albumImage;

    @Column(name="album_state")
    private int albumState;

    @Column(name="album_reg_date")
    @Temporal(TemporalType.DATE)
    @CreationTimestamp
    private Date albumRegDate;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "albumEntity", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<SongEntity> songEntityList;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    @JsonIgnore
    private MemberEntity memberEntity;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "albumEntity")
    @JsonIgnore
    private List<AlbumReplyEntity> albumReplyEntities;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "albumEntity")
    @JsonIgnore
    private List<AlbumSingerEntity> albumSingerEntities;

    }
