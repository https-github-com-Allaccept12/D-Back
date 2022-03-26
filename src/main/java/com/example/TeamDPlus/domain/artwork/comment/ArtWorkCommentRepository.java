package com.example.TeamDPlus.domain.artwork.comment;

import org.springframework.data.jpa.repository.JpaRepository;


public interface ArtWorkCommentRepository extends JpaRepository<ArtWorkComment, Long>,ArtWorkCommentRepositoryCustom {

    void deleteAllByArtWorksId(Long artWorkId);

}
