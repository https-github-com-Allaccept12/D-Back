
package com.example.dplus.service.artwork;

import com.example.dplus.dto.response.ArtWorkResponseDto;
import com.example.dplus.dto.response.MainResponseDto;
import com.example.dplus.dto.request.ArtWorkRequestDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ArtworkMainService {

    List<ArtWorkResponseDto.ArtworkMain> showArtworkMain(Long accountId, Long artworkId, String category, int sort);

    int createArtwork(Long accountId, ArtWorkRequestDto.ArtWorkCreateAndUpdate artWorkCreate, List<MultipartFile> multipartFiles);

    Long updateArtwork(Long account, Long artworkId, ArtWorkRequestDto.ArtWorkCreateAndUpdate artWorkUpdate, List<MultipartFile> multipartFiles);

    void deleteArtwork(Long accountId, Long artworkId);

    ArtWorkResponseDto.ArtWorkDetail detailArtWork(Long accountId, Long artWorkId);

    MainResponseDto mostPopularArtWork(Long accountId);

    List<ArtWorkResponseDto.ArtworkMain> findBySearchKeyWord(String keyword, Long lastArtWorkId, Long accountId);

    List<ArtWorkResponseDto.ArtworkMain> findByFollowerArtWork(Long accountId, String category, Long lastArtWorkId);




}