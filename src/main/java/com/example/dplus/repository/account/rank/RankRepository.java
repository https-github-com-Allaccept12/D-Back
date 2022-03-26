package com.example.dplus.repository.account.rank;

import com.example.dplus.domain.account.Rank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RankRepository extends JpaRepository<Rank, Long> ,RankRepositoryCustom{


}
