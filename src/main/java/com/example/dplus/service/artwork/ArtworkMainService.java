
package com.example.dplus.service.artwork;

import com.example.dplus.dto.request.ArtWorkRequestDto.ArtWorkCreate;
import com.example.dplus.dto.request.ArtWorkRequestDto.ArtWorkUpdate;
import com.example.dplus.dto.response.ArtWorkResponseDto;
import com.example.dplus.dto.response.ArtWorkResponseDto.ArtworkMain;
import com.example.dplus.dto.response.MainResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ArtworkMainService {

    List<ArtworkMain> showArtworkMain(Long accountId, Long artworkId,String category);

    List<ArtworkMain> showArtWorkLikeSort(Long accountId, String category,int start );

    int createArtwork(Long accountId, ArtWorkCreate artWorkCreate, List<MultipartFile> multipartFiles);

    Long updateArtwork(Long account, Long artworkId, ArtWorkUpdate artWorkUpdate, List<MultipartFile> multipartFiles);

    void deleteArtwork(Long accountId, Long artworkId);

    ArtWorkResponseDto.ArtWorkDetail detailArtWork(Long accountId, Long artWorkId);

    MainResponseDto mostPopularArtWork(Long accountId,String interest);

    List<ArtworkMain> findBySearchKeyWord(String keyword,Long lastId,  Long accountId);

    List<ArtworkMain> findByFollowerArtWork(Long accountId,String category, Long lastArtWorkId);




}