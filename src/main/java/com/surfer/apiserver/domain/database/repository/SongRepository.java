package com.surfer.apiserver.domain.database.repository;

import com.surfer.apiserver.domain.database.entity.SongEntity;
import com.surfer.apiserver.search.dto.AlbumSearchDTO;
import com.surfer.apiserver.search.dto.LyricsSearchDTO;
import com.surfer.apiserver.search.dto.PlayListSearchDTO;
import com.surfer.apiserver.search.dto.SongSearchDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface SongRepository extends JpaRepository<SongEntity, Long> {


//    @Query("SELECT s FROM SongEntity s")
//    List<SongEntity> findKeyword1(String keyword);



    @Query("SELECT DISTINCT new com.surfer.apiserver.search.dto.SongSearchDTO(ae.albumImage, se.songTitle, sse.songSingerName) FROM SongSingerEntity sse join sse.songEntity se join se.albumEntity ae WHERE se.songTitle LIKE %:keyword% OR sse.songSingerName LIKE %:keyword%")
    List<SongSearchDTO> findKeywordSong(@Param("keyword") String keyword);

    @Query("SELECT DISTINCT new com.surfer.apiserver.search.dto.AlbumSearchDTO(ae.albumImage,ae.albumTitle, ase.albumSingerName) FROM AlbumSingerEntity ase join ase.albumEntity ae WHERE ase.albumSingerName LIKE %:keyword%")
    List<AlbumSearchDTO> findKeywordAlbum(@Param("keyword") String keyword);

    @Query("SELECT new com.surfer.apiserver.search.dto.PlayListSearchDTO(ple.playListName, ae.albumImage, me.name) FROM PlayListTrackEntity plte JOIN plte.playListEntity ple JOIN ple.memberEntity me JOIN plte.songEntity se JOIN se.albumEntity ae WHERE ple.playListName LIKE %:keyword% AND plte.playListTrackRegDate = (SELECT MIN(plte2.playListTrackRegDate) FROM PlayListTrackEntity plte2 WHERE plte2.playListEntity = ple)")
    List<PlayListSearchDTO> findKeywordPlayList(@Param("keyword") String keyword);

    @Query("SELECT DISTINCT new com.surfer.apiserver.search.dto.LyricsSearchDTO(ae.albumImage, se.songTitle, sse.songSingerName, se.lyrics) FROM SongSingerEntity sse join sse.songEntity se join se.albumEntity ae WHERE se.lyrics LIKE %:keyword%")
    List<LyricsSearchDTO> findKeywordLyrics(@Param("keyword") String keyword);




}



