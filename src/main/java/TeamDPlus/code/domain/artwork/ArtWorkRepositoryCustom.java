package TeamDPlus.code.domain.artwork;

import TeamDPlus.code.dto.response.ArtWorkResponseDto;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

public interface ArtWorkRepositoryCustom {

    // isPortfolio 포트폴리오탭 검색시 true = isMaster조건 추가
    // isPortfolio 내가 등록한 작품 검색시 false = isMaster조건 없애기
    // visitAccountId 게시글 등록자가 내 마이페이지에 방문했을때 게시글 표시 public, private 구분
    List<ArtWorkResponseDto.ArtWorkFeed> findByArtWorkImageAndAccountId(Long lastArtWorkId,Pageable pageable,Long visitAccountId,Long accountId,boolean isPortfolio);

    Page<ArtWorkResponseDto.ArtWorkBookMark> findArtWorkBookMarkByAccountId(Long lastArtWorkId,Pageable pageable,Long accountId);

    Page<ArtWorkResponseDto.ArtworkMain> findArtWorkByMostViewAndMostLike(String interest,Pageable pageable);

    Page<ArtWorkResponseDto.ArtworkMain> findAllArtWork(Long lastArtWorkId,Pageable pageable);

    Page<ArtWorkResponseDto.ArtWorkSimilarWork> findSimilarArtWork(Long accountId, Pageable pageable);

    Page<ArtWorkResponseDto.ArtworkMain> findBySearchKeyWord(String keyword,Long lastArtWorkId,Pageable pageable);

    ArtWorkResponseDto.ArtWorkSubDetail findByArtWorkSubDetail(Long accountId, Long artworkId);


    void updateArtWorkIdMasterToFalse(Long artWorkId);

    void updateAllArtWorkIsMasterToTrue(List<Long> artWorkId);

}
