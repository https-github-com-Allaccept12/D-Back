package com.example.dplus.domain.account.history;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HistoryRepository extends JpaRepository<History,Long> {

    List<History> findAllByAccountId(Long accountId);

    void deleteAllByAccountId(Long accountId);

}
