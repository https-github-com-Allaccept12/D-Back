package TeamDPlus.code.domain.artwork;

import TeamDPlus.code.domain.account.Account;
import TeamDPlus.code.domain.account.AccountRepository;
import TeamDPlus.code.domain.artwork.ArtWorks;
import TeamDPlus.code.domain.artwork.ArtWorkRepository;
import TeamDPlus.code.domain.artwork.bookmark.ArtWorkBookMark;
import TeamDPlus.code.domain.artwork.bookmark.ArtWorkBookMarkRepository;
import TeamDPlus.code.domain.artwork.image.ArtWorkImage;
import TeamDPlus.code.domain.artwork.image.ArtWorkImageRepository;
import TeamDPlus.code.domain.artwork.image.QArtWorkImage;
import TeamDPlus.code.dto.response.ArtWorkResponseDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.List;

import static TeamDPlus.code.domain.account.QAccount.account;
import static TeamDPlus.code.domain.artwork.QArtWorks.artWorks;
import static TeamDPlus.code.domain.artwork.bookmark.QArtWorkBookMark.artWorkBookMark;
import static TeamDPlus.code.domain.artwork.image.QArtWorkImage.artWorkImage;
import static org.assertj.core.api.Assertions.assertThat;


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
    ArtWorkBookMarkRepository artWorkBookMarkRepository;

    @Autowired
    EntityManager em;


    @Test
    @Commit
    public void artwork_feed_query_test() throws Exception {
        //given account,artwork,
        Account testAccount = Account.builder()
                .email("test")
                .nickname("test")
                .titleContent("test")
                .subContent("test")
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
        assertThat(fetch.get(0).getArtwork_id()).isEqualTo(testArtWorks.getId());
        assertThat(fetch.get(0).getImg()).isEqualTo(testArtWorkImage.getArtworkImg());
    }


    @Test
    public void artwork_bookmark_test() throws Exception {
        //give
        Account account1 = testAccountSet();
        ArtWorks artWorks1 = testArtWorksSet(account1);
        ArtWorks artWorks2 = testArtWorksSet(account1);
        ArtWorkImage artWorkImage1 = testArtWorkImageSet(artWorks1,"test1.img",true);
        ArtWorkImage artWorkImage2 = testArtWorkImageSet(artWorks2,"test2.img",true);

        ArtWorkBookMark testArtWorkBookMark = ArtWorkBookMark.builder().artWorks(artWorks1).account(account1).build();
        artWorkBookMarkRepository.save(testArtWorkBookMark);
        em.flush();
        em.clear();
        ArtWorkBookMark testArtWorkBookMark1 = ArtWorkBookMark.builder().artWorks(artWorks2).account(account1).build();
        artWorkBookMarkRepository.save(testArtWorkBookMark1);
        em.flush();
        em.clear();
        //when
        List<ArtWorkResponseDto.ArtWorkBookMark> bookMarkList = queryFactory
                .select(Projections.constructor(ArtWorkResponseDto.ArtWorkBookMark.class,
                        artWorks.id,
                        artWorks.account.nickname,
                        artWorkImage.artworkImg,
                        artWorks.view))
                .from(artWorks)
                .join(artWorkBookMark).on(artWorkBookMark.artWorks.eq(artWorks))
                .join(artWorkImage).on(artWorkImage.artWorks.eq(artWorks))
                .where(artWorkBookMark.account.id.eq(account1.getId()).and(artWorks.scope.eq("public")))
                .fetch();
        //then
        //log.info(bookMarkList.get(0).toString());
        //log.info(bookMarkList.get(1).toString());

        assertThat(bookMarkList.get(0).getImg()).isEqualTo(artWorkImage1.getArtworkImg());
        assertThat(bookMarkList.get(0).getAccount_nickname()).isEqualTo(account1.getNickname());
        assertThat(bookMarkList.get(1).getImg()).isEqualTo(artWorkImage2.getArtworkImg());
        assertThat(bookMarkList.get(1).getAccount_nickname()).isEqualTo(account1.getNickname());
    }

    @Test
    public void 작품_전체_불러오기() throws Exception {
        //given
        Account account1 = testAccountSet();
        ArtWorks artWorks1 = testArtWorksSet(account1);
        ArtWorks artWorks2 = testArtWorksSet(account1);
        ArtWorkImage artWorkImage1 = testArtWorkImageSet(artWorks1,"test1.img",false);
        ArtWorkImage artWorkImage2 = testArtWorkImageSet(artWorks1,"test2.img",true);
        ArtWorkImage artWorkImage3 = testArtWorkImageSet(artWorks2, "test3.img", true);
        ArtWorkImage artWorkImage4 = testArtWorkImageSet(artWorks2, "test4.img", false);
        Pageable paging = PageRequest.of(0,4);
        //when
        List<ArtWorkResponseDto.ArtworkPageMain> result = queryFactory
                .select(Projections.constructor(ArtWorkResponseDto.ArtworkPageMain.class,
                        artWorks.id,
                        account.id,
                        account.nickname,
                        account.profileImg,
                        QArtWorkImage.artWorkImage.artworkImg,
                        artWorks.view,
                        artWorks.category,
                        artWorks.created
                ))
                .from(artWorks)
                .join(account).on(account.id.eq(artWorks.account.id))
                .join(QArtWorkImage.artWorkImage).on(QArtWorkImage.artWorkImage.artWorks.eq(artWorks).and(QArtWorkImage.artWorkImage.thumbnail.isTrue()))
                .offset(paging.getOffset())
                .limit(paging.getPageSize())
                .where(artWorks.id.lt(10))
                .fetch();
        //then
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0).getImg()).isEqualTo(artWorkImage2.getArtworkImg());
        assertThat(result.get(0).getAccount_id()).isEqualTo(account1.getId());
        assertThat(result.get(1).getImg()).isEqualTo(artWorkImage3.getArtworkImg());
        assertThat(result.get(1).getAccount_id()).isEqualTo(account1.getId());


    }


    private Account testAccountSet() {
        Account testAccount = Account.builder()
                .email("test")
                .nickname("test")
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

    private ArtWorks testArtWorksSet(Account account) {
        ArtWorks testArtWorks = ArtWorks.builder()
                .scope("public")
                .title("test")
                .content("test")
                .view(1L)
                .category("test")
                .account(account)
                .build();
        testArtWorks.updateArtWorkIsMaster();
        ArtWorks saveArtWork = artWorkRepository.save(testArtWorks);
        em.flush();
        em.clear();
        return saveArtWork;
    }

    private ArtWorkImage testArtWorkImageSet(ArtWorks artWorks,String test,boolean thumb) {
        ArtWorkImage testArtWorkImage = ArtWorkImage.builder()
                .artWorks(artWorks)
                .artworkImg(test)
                .thumbnail(thumb)
                .build();
        ArtWorkImage save = artWorkImageRepository.save(testArtWorkImage);

        em.flush();
        em.clear();
        return save;
    }
}