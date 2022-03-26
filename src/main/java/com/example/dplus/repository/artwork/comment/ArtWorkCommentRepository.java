package com.example.dplus.repository.artwork.comment;

import com.example.dplus.domain.artwork.ArtWorkComment;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ArtWorkCommentRepository extends JpaRepository<ArtWorkComment, Long>,ArtWorkCommentRepositoryCustom {

    void deleteAllByArtWorksId(Long artWorkId);

}
