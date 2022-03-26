package com.example.dplus.domain.account;

import com.example.dplus.dto.response.AccountResponseDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AccountRepositoryCustom {

    List<AccountResponseDto.TopArtist> findTopArtist(Pageable pageable,String interest);
    void accountCreateCountInitialization();

}
