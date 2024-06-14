package com.surfer.apiserver.domain.database.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
@Table(name = "playlist")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PlayListEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "playlist_group_seq")
    @SequenceGenerator(name = "playlist_group_seq", sequenceName = "playlist_group_seq", allocationSize = 1)
    @Column(name = "playlist_group_seq", updatable = false)
    private Long playlistSeq;

    @Column(name = "tag")
    private String tag;

    @Column(name = "is_open")
    private int isOpen = 0;

    @Column(name = "playlist_name")
    private String playListName;

    @Column(name = "reg_date")
    private Date regDate;

    @Column(name = "playlist_reg_date", nullable = false)
    @Temporal(TemporalType.DATE)
    @UpdateTimestamp
    private Date albumRegDate;

    @Column(name = "playlist_update_date", nullable = false)
    @Temporal(TemporalType.DATE)
    @UpdateTimestamp
    private Date releaseDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_seq", nullable = false)
    private MemberEntity memberEntity;


}
