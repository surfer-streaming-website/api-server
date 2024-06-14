package com.surfer.apiserver.domain.database.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;
import java.util.Date;

@Entity
@Table(name = "playlistTrack")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PlayListTrackEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "playlist_track_seq")
    @SequenceGenerator(name= "playlist_track_seq",sequenceName = "playlist_track_seq",allocationSize = 1)
    @Column(name="playlist_track_seq",nullable = false,updatable = false)
    private Long playListTrackSeq;

    @Column(name = "playlist_track_reg_date")
    @Temporal(TemporalType.DATE)
    private Date playListTrackRegDate;

    @Column(name = "playlist_track_update_date")
    @Temporal(TemporalType.DATE)
    @UpdateTimestamp
    private Date playListTrackUpdateDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "playlist_group_seq",nullable = false)
    private PlayListEntity playListEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "song_seq",nullable = false)
    private SongEntity songEntity;

}
