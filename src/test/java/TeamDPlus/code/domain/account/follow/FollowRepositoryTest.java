package TeamDPlus.code.domain.account.follow;

import TeamDPlus.code.domain.account.Account;
import TeamDPlus.code.domain.account.AccountRepository;
import TeamDPlus.code.domain.account.history.HistoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Transactional
@Slf4j
class FollowRepositoryTest {

    @Autowired
    FollowRepository followRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    EntityManager em;

    @Test
    public void 팔로우_쿼리() throws Exception {
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
        //when


        //then
    }

}