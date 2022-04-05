package com.example.dplus.repository.artwork.image;

import com.example.dplus.domain.artwork.ArtWorkImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ArtWorkImageRepository extends JpaRepository<ArtWorkImage, Long>{

    @Query("select art from ArtWorkImage art where art.id =:artWorkId")
    List<ArtWorkImage> findByArtWorksId(@Param("artWorkId") Long artWorkId);
    void deleteAllByArtWorksId(Long artWorkId);
    void deleteByArtworkImg(String artWorkImg);




}
