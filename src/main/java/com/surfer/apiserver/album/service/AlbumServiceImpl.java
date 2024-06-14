package com.surfer.apiserver.album.service;

import com.surfer.apiserver.album.dto.AlbumDTO;
import com.surfer.apiserver.album.dto.AlbumReq;
import com.surfer.apiserver.album.dto.SongDTO;
import com.surfer.apiserver.domain.database.entity.AlbumEntity;
import com.surfer.apiserver.domain.database.entity.AlbumSingerEntity;
import com.surfer.apiserver.domain.database.entity.SongEntity;
import com.surfer.apiserver.domain.database.entity.SongSingerEntity;
import com.surfer.apiserver.domain.database.repository.AlbumRepository;
import com.surfer.apiserver.domain.database.repository.AlbumSingerRepository;
import com.surfer.apiserver.domain.database.repository.SongRepository;
import com.surfer.apiserver.domain.database.repository.SongSingerRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class AlbumServiceImpl implements AlbumService {


    private final SongRepository songRepository;
    private final SongSingerRepository songSingerRepository;
    private final AlbumRepository albumRepository;
    private final AlbumSingerRepository albumSingerRepository;

    @Autowired
    public AlbumServiceImpl(SongRepository songRepository, SongSingerRepository songSingerRepository, AlbumRepository albumRepository, AlbumSingerRepository albumSingerRepository) {

        this.songRepository = songRepository;
        this.songSingerRepository = songSingerRepository;
        this.albumRepository = albumRepository;
        this.albumSingerRepository = albumSingerRepository;
    }



    @Override
    public void saveAlbum(AlbumReq albumReq) {
        AlbumDTO albumDTO = albumReq.getAlbumDTO();
        List<String> albumSingerNameList = albumDTO.getAlbumSingerNameList();
        List<SongDTO> songDTOList = albumReq.getSongDTOList();


        // DTO를 엔터티로 변환
        AlbumEntity albumEntity = new AlbumEntity();
        albumEntity.setAlbumTitle(albumDTO.getAlbumTitle());
        albumEntity.setAgency(albumDTO.getAgency());
        albumEntity.setAlbumImage(albumDTO.getAlbumImage());
        albumEntity.setAlbumContent(albumDTO.getAlbumContent());
        albumEntity.setReleaseDate(albumDTO.getReleaseDate());

        albumEntity.setMemberEntity(albumDTO.getMemberEntity());

        albumRepository.save(albumEntity);

        for (String singer : albumSingerNameList){
            AlbumSingerEntity albumSingerEntity = new AlbumSingerEntity();
            albumSingerEntity.setAlbumSingerName(singer);
            albumSingerEntity.setAlbumEntity(albumEntity);

            albumSingerRepository.save(albumSingerEntity);
        }

        for(SongDTO songDTO : songDTOList){

            SongEntity songEntity = new SongEntity();
            songEntity.setAlbumEntity(albumEntity);
            songEntity.setSongTitle(songDTO.getSongTitle());
            songEntity.setSongNumber(songDTO.getSongNumber());
            songEntity.setLyrics(songDTO.getLyrics());
            songEntity.setGenre(songDTO.getGenre());
            songEntity.setSongState(songDTO.getSongState());
            songEntity.setSoundSourceName(songDTO.getSoundSourceName());
            songEntity.setProducer(songDTO.getProducer());

            songRepository.save(songEntity);

            for (String songSingerName : songDTO.getSongSingerNameList()) {
                SongSingerEntity songSingerEntity = new SongSingerEntity();
                songSingerEntity.setSongSingerName(songSingerName);
                songSingerEntity.setSongEntity(songEntity);

                songSingerRepository.save(songSingerEntity);
            }

        }
    }

}
