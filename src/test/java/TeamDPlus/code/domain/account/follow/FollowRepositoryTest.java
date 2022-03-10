package TeamDPlus.code.domain.account.follow;

import TeamDPlus.code.domain.account.Account;
import TeamDPlus.code.domain.account.AccountRepository;
import TeamDPlus.code.domain.account.history.HistoryRepository;
import TeamDPlus.code.dto.request.AccountRequestDto;
import TeamDPlus.code.dto.response.FollowResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
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
    @Commit
    public void 팔로잉_리스트_쿼리() throws Exception {
        //given
        //account3을 1,2가 팔로우
        Account account1 = testAccountSet("회원 1");
        Account account2 = testAccountSet("회원 2");
        Account account3 = testAccountSet("회원 3");
        Follow follow1 = followSet(account3.getId(), account1.getId());
        Follow follow2 = followSet(account3.getId(), account2.getId());
        followSet(account1.getId(), account3.getId());
        //when
        List<FollowResponseDto.FollowList> findId = followRepository.findAllByFollowingId(account3.getId());

        //then
        assertThat(follow1.getFollowerId()).isEqualTo(account1.getId());
        assertThat(follow2.getFollowerId()).isEqualTo(account2.getId());
        assertThat(follow1.getFollowingId()).isEqualTo(account3.getId());
        assertThat(follow2.getFollowingId()).isEqualTo(account3.getId());
        assertThat(findId.get(0).getAccount_id()).isEqualTo(account1.getId());
        assertThat(findId.get(1).getAccount_id()).isEqualTo(account2.getId());
        assertThat(findId.get(0).getAccount_img()).isEqualTo(account1.getProfileImg());
        assertThat(findId.get(1).getAccount_img()).isEqualTo(account2.getProfileImg());
    }
    @Test
    public void 팔로워_리스트_쿼리() throws Exception {
        //given
        //account1이 2,3을 팔로우
        Account account1 = testAccountSet("회원 1");
        Account account2 = testAccountSet("회원 2");
        Account account3 = testAccountSet("회원 3");
        Follow follow1 = followSet(account2.getId(), account1.getId());
        Follow follow2 = followSet(account3.getId(), account1.getId());
        followSet(account1.getId(), account3.getId());
        //when
        List<FollowResponseDto.FollowList> findId = followRepository.findAllByFollowerId(account1.getId());

        //then
        assertThat(follow1.getFollowerId()).isEqualTo(account1.getId());
        assertThat(follow2.getFollowerId()).isEqualTo(account1.getId());
        assertThat(follow1.getFollowingId()).isEqualTo(account2.getId());
        assertThat(follow2.getFollowingId()).isEqualTo(account3.getId());
        assertThat(findId.get(0).getAccount_id()).isEqualTo(account2.getId());
        assertThat(findId.get(1).getAccount_id()).isEqualTo(account3.getId());
        assertThat(findId.get(0).getAccount_img()).isEqualTo(account2.getProfileImg());
        assertThat(findId.get(1).getAccount_img()).isEqualTo(account3.getProfileImg());
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