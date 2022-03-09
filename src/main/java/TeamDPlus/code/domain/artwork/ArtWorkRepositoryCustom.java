package TeamDPlus.code.domain.artwork;

import TeamDPlus.code.dto.response.ArtWorkResponseDto;

import java.util.List;
import java.util.Optional;

public interface ArtWorkRepositoryCustom {

    // isPortfolio 포트폴리오탭 검색시 true = isMaster조건 추가
    // isPortfolio 내가 등록한 작품 검색시 false = isMaster조건 없애기
    // visitAccountId 게시글 등록자가 내 마이페이지에 방문했을때 게시글 표시 public, private 구분
    List<ArtWorkResponseDto.ArtWorkFeed> findByArtWorkImageAndAccountId(Long visitAccountId,Long accountId,boolean isPortfolio);



}
