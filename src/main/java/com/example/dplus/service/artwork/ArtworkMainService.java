
package com.example.dplus.service.artwork;

import com.example.dplus.dto.request.ArtWorkRequestDto.ArtWorkCreate;
import com.example.dplus.dto.request.ArtWorkRequestDto.ArtWorkUpdate;
import com.example.dplus.dto.response.ArtWorkResponseDto.ArtWorkDetail;
import com.example.dplus.dto.response.ArtWorkResponseDto.ArtworkMain;
import com.example.dplus.dto.response.MainResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ArtworkMainService {

    List<ArtworkMain> showArtworkMain(Long artworkId,String category);

    List<ArtworkMain> showArtWorkLikeSort(String category, int start);

    int createArtwork(Long accountId, ArtWorkCreate artWorkCreate, List<MultipartFile> multipartFiles);

    Long updateArtwork(Long account, Long artworkId, ArtWorkUpdate artWorkUpdate, List<MultipartFile> multipartFiles);

    void deleteArtwork(Long accountId, Long artworkId, String category);

    ArtWorkDetail detailArtWork(Long accountId, Long artWorkId);

    MainResponseDto mostPopularArtWork(Long accountId,String interest);

    List<ArtworkMain> findBySearchKeyWord(String keyword,Long lastId);

    List<ArtworkMain> findByFollowerArtWork(Long accountId,String category, Long lastArtWorkId);




}