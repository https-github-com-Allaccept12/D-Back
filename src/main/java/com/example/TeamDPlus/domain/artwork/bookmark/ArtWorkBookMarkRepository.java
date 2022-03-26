package com.example.TeamDPlus.domain.artwork.bookmark;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArtWorkBookMarkRepository extends JpaRepository<ArtWorkBookMark,Long> ,ArtWorkBookMarkRepositoryCustom{

    List<ArtWorkBookMark> findArtWorkBookMarkByAccountId(Long accountId);

    List<ArtWorkBookMark> findArtWorkBookMarkByArtWorksId(Long artWorkId);

    void deleteAllByArtWorksId(Long artWorkId);

    void deleteByArtWorksIdAndAccountId(Long artWorkId,Long accountId);
}
