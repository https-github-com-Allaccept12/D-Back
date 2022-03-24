package TeamDPlus.code.service.account.init;


import TeamDPlus.code.advice.ApiRequestException;
import TeamDPlus.code.advice.ErrorCode;
import TeamDPlus.code.advice.BadArgumentsValidException;
import TeamDPlus.code.domain.account.Account;
import TeamDPlus.code.domain.account.AccountRepository;
import TeamDPlus.code.dto.request.AccountRequestDto;
import TeamDPlus.code.dto.request.AccountRequestDto.InitInterestSetting;
import TeamDPlus.code.dto.request.AccountRequestDto.InitProfileSetting;
import TeamDPlus.code.dto.request.AccountRequestDto.InitTendencySetting;
import TeamDPlus.code.service.file.FileProcessService;
import TeamDPlus.code.service.file.FileService;
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
    public Long setInitProfile(MultipartFile profileImg, InitProfileSetting dto, Long accountId) {
        String profileUrl = fileProcessService.uploadImage(profileImg);
        Account account = getAccount(accountId);
        account.setInitProfile(dto);
        account.updateProfileImg(profileUrl);
        return account.getId();
    }
    @Transactional
    public Long updateProfile(MultipartFile profileImg, InitProfileSetting dto, Long accountId) {
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
    public Long setInitTendency(InitTendencySetting dto, Long accountId) {
        Account account = getAccount(accountId);
        account.initTendency(dto.getTendency());
        return account.getId();
    }

    @Transactional
    public Long setInitInterest(InitInterestSetting dto, Long accountId) {
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
