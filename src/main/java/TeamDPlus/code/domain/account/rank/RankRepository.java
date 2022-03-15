package TeamDPlus.code.domain.account.rank;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RankRepository extends JpaRepository<Rank, Long> ,RankRepositoryCustom{


}
