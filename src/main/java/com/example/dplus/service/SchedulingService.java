package com.example.dplus.service;


import com.example.dplus.repository.account.AccountRepository;
import com.example.dplus.repository.account.rank.RankRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
public class SchedulingService {

    private final RankRepository rankRepository;
    private final AccountRepository accountRepository;

    @Scheduled(cron = "0 0 0/1 * * *")
    @Transactional
    public void rankRepositoryInitialization() {
        log.info("랭크 시스템 초기화 집계를 다시 시작합니다.");
        
        rankRepository.RankInitializationBulk();
    }

    @Scheduled(cron = "0 0 0 1 * *")
    @Transactional
    public void accountCreateCountInitialization() {
        log.info("게시글 및 작품 작성 가능분을 초기화 합니다");
        accountRepository.accountCreateCountInitialization();
    }
}
