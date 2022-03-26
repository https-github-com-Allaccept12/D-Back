package com.example.dplus.repository.account;

import com.example.dplus.domain.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account,Long>,AccountRepositoryCustom {
    Optional<Account> findByAccountName(String username);
    Optional<Account> findByNickname(String nickname);
}
