package com.surfer.apiserver.domain.database.repository;

import com.surfer.apiserver.api.search.dto.AlbumSearchDTO;
import com.surfer.apiserver.api.search.dto.LyricsSearchDTO;
import com.surfer.apiserver.api.search.dto.PlaylistSearchDTO;
import com.surfer.apiserver.api.search.dto.SongSearchDTO;
import com.surfer.apiserver.domain.database.entity.SongEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface SongRepository extends JpaRepository<SongEntity, Long> {


//    @Query("SELECT s FROM SongEntity s")
//    List<SongEntity> findKeyword1(String keyword);



    @Query("SELECT DISTINCT new com.surfer.apiserver.api.search.dto.SongSearchDTO(ae.albumImage, se.songTitle, sse.songSingerName,se.songSeq) FROM SongSingerEntity sse join sse.songEntity se join se.albumEntity ae WHERE se.songTitle LIKE %:keyword% OR sse.songSingerName LIKE %:keyword%")
    List<SongSearchDTO> findKeywordSong(@Param("keyword") String keyword);

    @Query("SELECT DISTINCT new com.surfer.apiserver.api.search.dto.AlbumSearchDTO(ae.albumImage,ae.albumTitle, ase.albumSingerName ,ae.albumSeq) FROM AlbumSingerEntity ase join ase.albumEntity ae WHERE ase.albumSingerName LIKE %:keyword%")
    List<AlbumSearchDTO> findKeywordAlbum(@Param("keyword") String keyword);

    @Query("SELECT new com.surfer.apiserver.api.search.dto.PlaylistSearchDTO(ple.playlistName, ae.albumImage, me.name , ple.playlistGroupSeq) FROM PlaylistTrackEntity plte JOIN plte.playlistGroupEntity ple JOIN ple.memberEntity me JOIN plte.songEntity se JOIN se.albumEntity ae WHERE ple.playlistName LIKE %:keyword% AND plte.regDate = (SELECT MIN(plte2.regDate) FROM PlaylistTrackEntity plte2 WHERE plte2.playlistGroupEntity = ple)")
    List<PlaylistSearchDTO> findKeywordPlaylist(@Param("keyword") String keyword);

    @Query("SELECT DISTINCT new com.surfer.apiserver.api.search.dto.LyricsSearchDTO(ae.albumImage, se.songTitle, sse.songSingerName, se.lyrics,se.songSeq) FROM SongSingerEntity sse join sse.songEntity se join se.albumEntity ae WHERE se.lyrics LIKE %:keyword%")
    List<LyricsSearchDTO> findKeywordLyrics(@Param("keyword") String keyword);




}



