package TeamDPlus.code.domain.account.history;

import TeamDPlus.code.domain.account.Account;
import TeamDPlus.code.domain.account.AccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;



@Transactional
@SpringBootTest
@Slf4j
class HistoryRepositoryTest {


    @Autowired
    HistoryRepository historyRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    EntityManager em;

    @Test
    public void 사용자의_전체연혁_조회() throws Exception {
        //given
        Account testAccount = Account.builder()
                .email("test")
                .nickname("test")
                .titleContent("test")
                .subContent("test")
                .career(1)
                .tendency("무슨무슨형")
                .build();
        Account saveAccount = accountRepository.save(testAccount);
        em.flush();
        em.clear();
        History testHistory1 = History.builder().account(saveAccount).build();
        History testHistory2 = History.builder().account(saveAccount).build();
        historyRepository.save(testHistory1);
        historyRepository.save(testHistory2);
        em.flush();
        em.clear();
        //when
        List<History> allByAccountId = historyRepository.findAllByAccountId(saveAccount.getId());
        //then
        Assertions.assertThat(allByAccountId.size()).isEqualTo(2);
    }

}








