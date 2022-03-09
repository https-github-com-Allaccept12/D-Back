package TeamDPlus.code.domain.artwork;

import TeamDPlus.code.domain.account.Account;
import TeamDPlus.code.domain.account.AccountRepository;
import TeamDPlus.code.domain.artwork.ArtWorks;
import TeamDPlus.code.domain.artwork.ArtWorkRepository;
import TeamDPlus.code.domain.artwork.image.ArtWorkImage;
import TeamDPlus.code.domain.artwork.image.ArtWorkImageRepository;
import TeamDPlus.code.dto.response.ArtWorkResponseDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.List;

import static TeamDPlus.code.domain.artwork.QArtWorks.artWorks;
import static TeamDPlus.code.domain.artwork.image.QArtWorkImage.artWorkImage;


@SpringBootTest
@Transactional
@Slf4j
class ArtWorkRepositoryTest {

    @Autowired
    JPAQueryFactory queryFactory;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    ArtWorkRepository artWorkRepository;

    @Autowired
    ArtWorkImageRepository artWorkImageRepository;

    @Autowired
    EntityManager em;



    @Test
    @Commit
    public void careerFeed_query_test() throws Exception {
        //given account,artwork,
        Account testAccount = Account.builder()
                .email("test")
                .nickname("test")
                .content("test")
                .career(1)
                .tendency("무슨무슨형")
                .build();
        Account saveAccount = accountRepository.save(testAccount);
        ArtWorks testArtWorks = ArtWorks.builder()
                .scope("public")
                .title("test")
                .content("test")
                .view(1L)
                .category("test")
                .account(testAccount)
                .build();
        testArtWorks.updateArtWorkIsMaster();
        ArtWorks saveArtWork = artWorkRepository.save(testArtWorks);
        em.flush();
        em.clear();
        ArtWorkImage testArtWorkImage = ArtWorkImage.builder()
                .artWorks(saveArtWork)
                .artworkImg("test.img")
                .build();
        artWorkImageRepository.save(testArtWorkImage);
        em.flush();
        em.clear();
        //log.info(String.valueOf(saveCareer.getArtWorks().getArtWorkImage().size()));

        //when
        List<ArtWorkResponseDto.ArtWorkFeed> fetch = queryFactory
                .select(Projections.constructor(
                        ArtWorkResponseDto.ArtWorkFeed.class,
                        artWorks.id,
                        artWorks.scope,
                        artWorks.title,
                        artWorkImage.artworkImg,
                        artWorks.view,
                        artWorks.created,
                        artWorks.modified
                ))
                .from(artWorks)
                .leftJoin(artWorkImage).on(artWorkImage.artWorks.eq(artWorks))
                .where(artWorks.isMaster.isTrue().and(artWorks.account.id.eq(saveAccount.getId())))
                .fetch();
        //then
        Assertions.assertThat(fetch.get(0).getArtwork_id()).isEqualTo(testArtWorks.getId());
        Assertions.assertThat(fetch.get(0).getImg()).isEqualTo(testArtWorkImage.getArtworkImg());
    }

}