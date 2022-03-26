package com.example.dplus.domain.artwork.image;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArtWorkImageRepository extends JpaRepository<ArtWorkImage, Long>,ArtworkImageRepositoryCustom {

    List<ArtWorkImage> findByArtWorksId(Long artWorkId);
    void deleteAllByArtWorksId(Long artWorkId);
    void deleteByArtworkImg(String artWorkImg);

    ArtWorkImage findByArtworkImg(String artworkImg);


}
