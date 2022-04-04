package com.example.dplus.repository.artwork;

import com.example.dplus.dto.response.ArtWorkResponseDto.*;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ArtWorkRepositoryCustom {

    // visitAccountId 게시글 등록자가 내 마이페이지에 방문했을때 게시글 표시 public, private 구분
    List<ArtWorkFeed> findByMasterArtWorkImageAndAccountId(Long visitAccountId);

    List<MyArtWork> findByArtWork(Long lastArtWorkId,Long visitAccountId,Long accountId);

    List<ArtWorkBookMark> findArtWorkBookMarkByAccountId(Long lastArtWorkId, Long accountId);

    List<ArtworkMain> findArtWorkByMostViewAndMostLike(String interest);

    List<ArtworkMain> findAllArtWork(Long lastArtWorkId,String category);

    List<ArtworkMain> showArtWorkLikeSort(String category, Pageable pageable);

    List<ArtWorkSimilarWork> findSimilarArtWork(Long accountId, Long artWorkId);

    List<ArtworkMain> findBySearchKeyWord(String keyword,Long lastArtWorkId);

    ArtWorkSubDetail findByArtWorkSubDetail(Long artworkId);

    List<ArtworkMain> findByFollowerArtWork(Long accountId,String category, Long lastArtWorkId);


}