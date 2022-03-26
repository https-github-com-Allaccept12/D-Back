package com.example.dplus.repository.artwork;

import com.example.dplus.domain.artwork.ArtWorks;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtWorkRepository extends JpaRepository<ArtWorks,Long> ,ArtWorkRepositoryCustom{


}
