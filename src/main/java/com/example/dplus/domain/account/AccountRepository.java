package com.example.dplus.domain.account;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account,Long>,AccountRepositoryCustom {
    Optional<Account> findByUsername(String username);
    Optional<Account> findByNickname(String nickname);
}
