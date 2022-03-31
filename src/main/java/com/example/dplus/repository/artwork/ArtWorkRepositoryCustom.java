package com.example.dplus.repository.artwork;

import com.example.dplus.dto.response.ArtWorkResponseDto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ArtWorkRepositoryCustom {

    // visitAccountId 게시글 등록자가 내 마이페이지에 방문했을때 게시글 표시 public, private 구분
    List<ArtWorkFeed> findByMasterArtWorkImageAndAccountId(Long visitAccountId);

    List<MyArtWork> findByArtWork(Long lastArtWorkId,Pageable pageable,Long visitAccountId,Long accountId);

    List<ArtWorkBookMark> findArtWorkBookMarkByAccountId(Long lastArtWorkId, Pageable pageable, Long accountId);

    List<ArtworkMain> findArtWorkByMostViewAndMostLike(String interest);

    List<ArtworkMain> findAllArtWork(Long lastArtWorkId,String category, Pageable pageable);

    Page<ArtworkMain> showArtWorkLikeSort(String category, Pageable pageable);

    List<ArtWorkSimilarWork> findSimilarArtWork(Long accountId, Long artWorkId, Pageable pageable);

    List<ArtworkMain> findBySearchKeyWord(String keyword,Long lastArtWorkId,Pageable pageable);

    ArtWorkSubDetail findByArtWorkSubDetail(Long artworkId);

    List<ArtworkMain> findByFollowerArtWork(Long accountId,String category, Long lastArtWorkId, Pageable pageable);


}
