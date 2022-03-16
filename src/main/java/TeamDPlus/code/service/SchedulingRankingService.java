package TeamDPlus.code.service;


import TeamDPlus.code.domain.account.rank.RankRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
public class SchedulingRankingService {

    private final RankRepository rankRepository;


//    @Scheduled(cron = "*/10 * * * * *")
//    @Transactional
//    public void rankRepositoryInitialization() {
//        log.info("랭크 시스템 초기화 집계를 다시 시작합니다.");
//        rankRepository.RankInitializationBulk();
//    }

}
