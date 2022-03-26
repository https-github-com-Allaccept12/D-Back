package com.example.dplus.domain.account.rank;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RankRepository extends JpaRepository<Rank, Long> ,RankRepositoryCustom{


}
