
package com.example.TeamDPlus.service.artwork;

import com.example.TeamDPlus.dto.request.ArtWorkRequestDto.ArtWorkCreate;
import com.example.TeamDPlus.dto.request.ArtWorkRequestDto.ArtWorkUpdate;
import com.example.TeamDPlus.dto.response.ArtWorkResponseDto;
import com.example.TeamDPlus.dto.response.ArtWorkResponseDto.ArtworkMain;
import com.example.TeamDPlus.dto.response.MainResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ArtworkMainService {

    List<ArtworkMain> showArtworkMain(Long accountId, Long artworkId,String category, int sort);

    int createArtwork(Long accountId, ArtWorkCreate artWorkCreate, List<MultipartFile> multipartFiles);

    Long updateArtwork(Long account, Long artworkId, ArtWorkUpdate artWorkUpdate, List<MultipartFile> multipartFiles);

    void deleteArtwork(Long accountId, Long artworkId);

    ArtWorkResponseDto.ArtWorkDetail detailArtWork(Long accountId, Long artWorkId);

    MainResponseDto mostPopularArtWork(Long accountId);

    List<ArtworkMain> findBySearchKeyWord(String keyword, Long lastArtWorkId, Long accountId);

    List<ArtworkMain> findByFollowerArtWork(Long accountId,String category, Long lastArtWorkId);




}