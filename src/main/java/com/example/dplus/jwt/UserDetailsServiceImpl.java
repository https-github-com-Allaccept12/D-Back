package com.example.dplus.jwt;

import com.example.dplus.domain.account.Account;
import com.example.dplus.domain.account.AccountRepository;
import com.example.dplus.advice.ApiRequestException;
import com.example.dplus.advice.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final AccountRepository accountRepository;

    public UserDetails loadUserByUsername(String userPk) throws UsernameNotFoundException {
        Account user = accountRepository.findById(Long.parseLong(userPk))
                .orElseThrow(() -> new ApiRequestException(ErrorCode.NO_AUTHENTICATION_ERROR));
        return new UserDetailsImpl(user);
    }
}
