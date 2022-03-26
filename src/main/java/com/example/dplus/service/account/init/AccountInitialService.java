package com.example.dplus.service.account.init;


import com.example.dplus.advice.ApiRequestException;
import com.example.dplus.advice.BadArgumentsValidException;
import com.example.dplus.advice.ErrorCode;
import com.example.dplus.domain.account.Account;
import com.example.dplus.domain.account.AccountRepository;
import com.example.dplus.dto.request.AccountRequestDto;
import com.example.dplus.service.file.FileProcessService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public Long setInitProfile(MultipartFile profileImg, AccountRequestDto.InitProfileSetting dto, Long accountId) {
        String profileUrl = fileProcessService.uploadImage(profileImg);
        Account account = getAccount(accountId);
        account.setInitProfile(dto);
        return account.getId();
    }

    @Transactional
    public Long updateProfile(MultipartFile profileImg, AccountRequestDto.InitProfileSetting dto, Long accountId) {
        if (profileImg != null) {
            fileProcessService.deleteImage(dto.getDelete_profile_img());
            String profileUrl = fileProcessService.uploadImage(profileImg);
            Account account = getAccount(accountId);
            account.setInitProfile(dto);
            account.updateProfileImg(profileUrl);
            return account.getId();
        }
        Account account = getAccount(accountId);
        account.setInitProfile(dto);
        return account.getId();
    }

    @Transactional
    public Long setInitTendency(AccountRequestDto.InitTendencySetting dto, Long accountId) {
        Account account = getAccount(accountId);
        account.initTendency(dto.getTendency());
        return account.getId();
    }

    @Transactional
    public Long setInitInterest(AccountRequestDto.InitInterestSetting dto, Long accountId) {
        Account account = getAccount(accountId);
        account.updateInterest(dto.getInterest());
        return account.getId();
    }
    @Transactional(readOnly = true)
    public void getNickNameValidation(String nickname) {
        Account account = accountRepository.findByNickname(nickname).orElse(null);
        if (account != null){
            throw new BadArgumentsValidException(ErrorCode.ALREADY_NICKNAME_ERROR);
        }
        if (!Pattern.matches("^[A-Za-z0-9]{3,}$", nickname)){
            throw new BadArgumentsValidException(ErrorCode.BAD_CONDITION_NICKNAME_ERROR); //닉네임 조건에 맞지않음
        }
    }

    private Account getAccount(Long accountId) {
        return accountRepository.findById(accountId).orElseThrow(() -> new ApiRequestException(ErrorCode.NO_USER_ERROR));
    }

}
