package TeamDPlus.code.service.account.follow;

import TeamDPlus.code.domain.account.Account;
import TeamDPlus.code.domain.account.AccountRepository;
import TeamDPlus.code.domain.account.follow.Follow;
import TeamDPlus.code.domain.account.follow.FollowRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Transactional
class FollowServiceTest {


    @Autowired
    FollowRepository followRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    EntityManager em;




    @Test
    public void 이미_팔로우_했을때() throws Exception {
        //given
        Account account1 = testAccountSet("test1.img");
        Account account2 = testAccountSet("test2.img");
        followSet(account2.getId(), account1.getId());
        //when
        boolean checkFollow = followRepository.existsByFollowerIdAndFollowingId(account1.getId(), account2.getId());
        //then
        assertThat(checkFollow).isTrue();
    }
    @Test
    public void 팔로우_안한_상태일때() throws Exception {
        //given
        //다른사람들은 팔로우 했지만 testAccount는 팔로우 안한 상태일때
        Account account1 = testAccountSet("test1.img");
        Account account2 = testAccountSet("test2.img");
        Account account3 = testAccountSet("test2.img");
        followSet(account2.getId(), account1.getId());
        //when
        boolean checkFollow = followRepository.existsByFollowerIdAndFollowingId(account1.getId(), account3.getId());
        //then
        assertThat(checkFollow).isFalse();
    }

    @Test
    public void 언_팔로우() throws Exception {
        //given
        Account account1 = testAccountSet("test1.img");
        Account account2 = testAccountSet("test2.img");
        followSet(account2.getId(), account1.getId());
        //when - then
        boolean checkFollow = followRepository.existsByFollowerIdAndFollowingId(account1.getId(), account2.getId());
        assertThat(checkFollow).isTrue();
        em.flush();
        em.clear();
        followRepository.deleteByFollowerIdAndFollowingId(account1.getId(), account2.getId());
        boolean checkUnFollow = followRepository.existsByFollowerIdAndFollowingId(account1.getId(), account2.getId());
        assertThat(checkUnFollow).isFalse();
    }

    private Follow followSet(Long followingId, Long followerId) {
        Follow follow1 = Follow.builder().followingId(followingId).followerId(followerId).build();
        Follow save = followRepository.save(follow1);
        em.flush();
        em.clear();
        return save;
    }

    private Account testAccountSet(String img) {
        Account testAccount = Account.builder()
                .email("test")
                .nickname("test")
                .profileImg(img)
                .titleContent("test")
                .subContent("test")
                .career(1)
                .tendency("무슨무슨형")
                .build();
        Account save = accountRepository.save(testAccount);
        em.flush();
        em.clear();
        return save;
    }


}