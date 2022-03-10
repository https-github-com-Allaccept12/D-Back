package TeamDPlus.code.domain.artwork;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArtWorkRepository extends JpaRepository<ArtWorks,Long> ,ArtWorkRepositoryCustom{


}
