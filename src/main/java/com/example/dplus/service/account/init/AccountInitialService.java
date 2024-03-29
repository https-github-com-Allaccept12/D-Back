package com.example.dplus.service.account.init;


import com.example.dplus.advice.ErrorCode;
import com.example.dplus.advice.ErrorCustomException;
import com.example.dplus.domain.account.Account;
import com.example.dplus.dto.request.AccountRequestDto.InitInterestSetting;
import com.example.dplus.dto.request.AccountRequestDto.InitProfileSetting;
import com.example.dplus.dto.request.AccountRequestDto.InitTendencySetting;
import com.example.dplus.repository.account.AccountRepository;
import com.example.dplus.service.file.FileProcessService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountInitialService {


    private final AccountRepository accountRepository;
    private final FileProcessService fileProcessService;

    @Transactional
    @CacheEvict(value="accountInfo", key="#accountId")
    public Long setInitProfile(MultipartFile profileImg, InitProfileSetting dto, Long accountId) {
        Account account = getAccount(accountId);
        if (profileImg != null) {
            String profileUrl = fileProcessService.uploadImage(profileImg);
            account.updateProfileImg(profileUrl);
        }
        account.setInitProfile(dto);
        return account.getId();
    }
    @Transactional
    @CacheEvict(value="accountInfo", key="#accountId")
    public Long updateProfile(MultipartFile profileImg, InitProfileSetting dto, Long accountId) {
        Account account = getAccount(accountId);
        if (profileImg != null) {
            fileProcessService.deleteImage(dto.getDelete_profile_img());
            String profileUrl = fileProcessService.uploadImage(profileImg);
            account.updateProfileImg(profileUrl);
        }
        account.setInitProfile(dto);
        return account.getId();

    }
    @Transactional
    @CacheEvict(value="accountInfo", key="#accountId")
    public Long setInitTendency(InitTendencySetting dto, Long accountId) {
        Account account = getAccount(accountId);
        account.initTendency(dto.getTendency());
        return account.getId();
    }

    @Transactional
    @CacheEvict(value="accountInfo", key="#accountId")
    public Long setInitInterest(InitInterestSetting dto, Long accountId) {
        Account account = getAccount(accountId);
        account.updateInterest(dto.getInterest());
        return account.getId();
    }
    @Transactional(readOnly = true)
    public void getNickNameValidation(String nickname) {
        Account account = accountRepository.findByNickname(nickname).orElse(null);
        if (account != null){
            throw new ErrorCustomException(ErrorCode.ALREADY_NICKNAME_ERROR);
        }
        if (!Pattern.matches("^[A-Za-z0-9가-힣]{3,10}$", nickname)){
            throw new ErrorCustomException(ErrorCode.BAD_CONDITION_NICKNAME_ERROR); //닉네임 조건에 맞지않음
        }
    }

    private Account getAccount(Long accountId) {
        return accountRepository.findById(accountId).orElseThrow(() -> new ErrorCustomException(ErrorCode.NO_USER_ERROR));
    }

}
