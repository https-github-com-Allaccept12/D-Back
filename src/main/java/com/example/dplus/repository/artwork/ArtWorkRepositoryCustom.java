package com.example.dplus.repository.artwork;

import com.example.dplus.dto.response.ArtWorkResponseDto.*;

import org.springframework.data.domain.Pageable;
import java.util.List;

public interface ArtWorkRepositoryCustom {

    // isPortfolio 포트폴리오탭 검색시 true = isMaster조건 추가
    // isPortfolio 내가 등록한 작품 검색시 false = isMaster조건 없애기
    // visitAccountId 게시글 등록자가 내 마이페이지에 방문했을때 게시글 표시 public, private 구분
    List<ArtWorkFeed> findByArtWorkImageAndAccountId(Long lastArtWorkId,Pageable pageable,Long visitAccountId, Long accountId, boolean isPortfolio);

    List<ArtWorkBookMark> findArtWorkBookMarkByAccountId(Long lastArtWorkId, Pageable pageable, Long accountId);

    List<ArtworkMain> findArtWorkByMostViewAndMostLike(String interest);

    List<ArtworkMain> findAllArtWork(Long lastArtWorkId,String category, Pageable pageable,int sort);

    List<ArtWorkSimilarWork> findSimilarArtWork(Long accountId, Long artWorkId, Pageable pageable);

    List<ArtworkMain> findBySearchKeyWord(String keyword,Long lastArtWorkId,Pageable pageable);

    ArtWorkSubDetail findByArtWorkSubDetail(Long artworkId);

    List<ArtworkMain> findByFollowerArtWork(Long accountId,String category, Long lastArtWorkId, Pageable pageable);


    void updateArtWorkIdMasterToFalse(Long artWorkId);

    void updateAllArtWorkIsMasterToTrue(List<Long> artWorkId);

}
