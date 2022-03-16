package TeamDPlus.code.domain.account.rank;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RankRepository extends JpaRepository<Ranks, Long> ,RankRepositoryCustom{


}
