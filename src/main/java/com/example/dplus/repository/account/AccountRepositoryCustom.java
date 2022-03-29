package com.example.dplus.repository.account;

import com.example.dplus.domain.account.Account;

import java.util.List;

public interface AccountRepositoryCustom {

    List<Account> findTopArtist(String interest);
    void accountCreateCountInitialization();

}
