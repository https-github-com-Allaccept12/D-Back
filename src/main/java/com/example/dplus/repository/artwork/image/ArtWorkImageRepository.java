package com.example.dplus.repository.artwork.image;

import com.example.dplus.domain.artwork.ArtWorkImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArtWorkImageRepository extends JpaRepository<ArtWorkImage, Long>{

    List<ArtWorkImage> findByArtWorksId(Long artWorkId);
    void deleteAllByArtWorksId(Long artWorkId);
    void deleteByArtworkImg(String artWorkImg);




}
