package com.example.dplus.repository.artwork.like;

import com.example.dplus.domain.artwork.ArtWorkLikes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtWorkLikesRepository extends JpaRepository<ArtWorkLikes, Long>,ArtWorkLikesRepositoryCustom {

    Long countArtWorkLikesByArtWorksId(Long artWorkId);

    void deleteAllByArtWorksId(Long artWorkId);

    void deleteByArtWorksIdAndAccountId(Long artWorkId,Long accountId);

}
