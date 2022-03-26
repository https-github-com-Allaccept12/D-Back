package com.example.TeamDPlus.domain.artwork;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtWorkRepository extends JpaRepository<ArtWorks,Long> ,ArtWorkRepositoryCustom{


}
