package TeamDPlus.code.domain.post;

import TeamDPlus.code.domain.account.Account;
import TeamDPlus.code.domain.account.AccountRepository;
import TeamDPlus.code.domain.account.rank.Rank;
import TeamDPlus.code.domain.account.rank.RankRepository;
import TeamDPlus.code.domain.artwork.ArtWorks;
import TeamDPlus.code.domain.artwork.image.ArtWorkImage;
import TeamDPlus.code.domain.post.bookmark.PostBookMark;
import TeamDPlus.code.domain.post.bookmark.PostBookMarkRepository;
import TeamDPlus.code.domain.post.bookmark.QPostBookMark;
import TeamDPlus.code.domain.post.comment.PostComment;
import TeamDPlus.code.domain.post.comment.PostCommentRepository;
import TeamDPlus.code.domain.post.comment.like.PostCommentLikes;
import TeamDPlus.code.domain.post.comment.like.PostCommentLikesRepository;
import TeamDPlus.code.domain.post.image.PostImage;
import TeamDPlus.code.domain.post.image.PostImageRepository;
import TeamDPlus.code.domain.post.like.PostLikes;
import TeamDPlus.code.domain.post.like.PostLikesRepository;
import TeamDPlus.code.domain.post.like.QPostLikes;
import TeamDPlus.code.domain.post.tag.PostTagRepository;
import TeamDPlus.code.dto.response.ArtWorkResponseDto;
import TeamDPlus.code.dto.response.PostResponseDto;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static TeamDPlus.code.domain.account.QAccount.account;
import static TeamDPlus.code.domain.artwork.QArtWorks.artWorks;
import static TeamDPlus.code.domain.post.QPost.post;
import static TeamDPlus.code.domain.post.bookmark.QPostBookMark.*;
import static TeamDPlus.code.domain.post.comment.QPostComment.*;
import static TeamDPlus.code.domain.post.like.QPostLikes.*;
import static TeamDPlus.code.domain.post.tag.QPostTag.postTag;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Slf4j
public class PostRepositoryTests {

    @Autowired
    EntityManager em;

    @Autowired
    JPAQueryFactory queryFactory;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    PostTagRepository postTagRepository;

    @Autowired
    PostImageRepository postImageRepository;

    @Autowired
    PostBookMarkRepository postBookMarkRepository;

    @Autowired
    PostCommentRepository postCommentRepository;

    @Autowired
    RankRepository rankRepository;

    @Autowired
    PostCommentLikesRepository postCommentLikesRepository;

    @Autowired
    PostLikesRepository postLikesRepository;
    @Autowired
    PostRepository postRepository;

    @Test
    @Commit
    public void 전체포스트_목록() throws Exception {
        //given account,artwork,
        Account testAccount = testAccountSet();
        Post post1 = testPostSet(testAccount, PostBoard.QNA);
        Post post2 = testPostSet(testAccount, PostBoard.QNA);

        Pageable pageable = PageRequest.of(0, 3);
        // when
        List<PostResponseDto.PostPageMain> fetch = queryFactory
                .select(Projections.constructor(PostResponseDto.PostPageMain.class,
                        post.id,
                        account.id,
                        account.nickname,
                        account.profileImg,
                        post.title,
                        post.category,
                        post.content,
                        post.created
                ))
                .from(post)
                .join(account).on(account.id.eq(post.account.id))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .where(post.id.lt(12))
                .fetch();
        for (PostResponseDto.PostPageMain postPageMain : fetch) {
            System.out.println("postPageMain = " + postPageMain.getAccount_id());
            System.out.println("postPageMain.getPost_id() = " + postPageMain.getPost_id());
        }

        // then
        assertThat(fetch.size()).isEqualTo(2);
        assertThat(fetch.get(0).getAccount_id()).isEqualTo(testAccount.getId());
        assertThat(fetch.get(1).getAccount_id()).isEqualTo(testAccount.getId());
    }

    private Account testAccountSet() {
        Rank rank = Rank.builder().rankScore(0L).build();
        Rank saveRank = rankRepository.save(rank);
        Account testAccount = Account.builder()
                .email("test")
                .nickname("test")
                .titleContent("test")
                .subContent("test")
                .career(1)
                .tendency("무슨무슨형")
                .interest("test")
                .rank(saveRank)
                .build();
        Account save = accountRepository.save(testAccount);
        em.flush();
        em.clear();
        return save;
    }

    private Post testPostSet(Account account, PostBoard board) {
        Post post = Post.builder()
                .title("test")
                .category("test")
                .content("test")
                .board(board)
                .account(account)
                .build();
        Post postSaved = postRepository.save(post);
        em.flush();
        em.clear();
        return postSaved;
    }

    private PostImage testPostImageSet(Post post, String test) {
        PostImage testArtWorkImage = PostImage.builder()
                .post(post)
                .postImg(test)
                .build();
        PostImage save = postImageRepository.save(testArtWorkImage);

        em.flush();
        em.clear();
        return save;
    }

    private PostBookMark testPostBookMark(Post post, Account account) {
        PostBookMark testPostBookmark = PostBookMark.builder()
                .post(post)
                .account(account)
                .build();
        PostBookMark save = postBookMarkRepository.save(testPostBookmark);
        em.flush();
        em.clear();
        return save;
    }

    private PostComment testPostComment(Post post, Account account) {
        PostComment comment = PostComment.builder()
                .account(account)
                .post(post)
                .content("test1")
                .build();
        PostComment save = postCommentRepository.save(comment);
        em.flush();
        em.clear();
        return save;
    }

    private PostLikes testPostLikes(Post post, Account account) {
        PostLikes likes = PostLikes.builder()
                .post(post)
                .account(account)
                .build();
        PostLikes save = postLikesRepository.save(likes);
        em.flush();
        em.clear();
        return save;
    }

    private PostCommentLikes testPostCommentLikes(PostComment comment, Account account) {
        PostCommentLikes likes = PostCommentLikes.builder()
                .postComment(comment)
                .account(account)
                .build();
        PostCommentLikes save = postCommentLikesRepository.save(likes);
        em.flush();
        em.clear();
        return save;
    }

    @Test
    public void 상세페이지() throws Exception {

        // given
        Account testAccount = testAccountSet();
        Account testAccount2 = testAccountSet();
        Account testAccount3 = testAccountSet();
        Post post1 = testPostSet(testAccount, PostBoard.QNA);

        PostLikes postLikes = testPostLikes(post1, testAccount2);
        PostLikes postLikes1 = testPostLikes(post1, testAccount3);
        PostBookMark bookMark1 = testPostBookMark(post1, testAccount2);
        PostBookMark bookMark2 = testPostBookMark(post1, testAccount3);

        PostResponseDto.PostSubDetail subDetail = queryFactory
                .select(Projections.constructor(PostResponseDto.PostSubDetail.class,
                        Expressions.asNumber(post1.getId()).as("post_id"),
                        account.id,
                        account.profileImg,
                        account.nickname,
                        post.title,
                        post.content,
                        post.view,
                        post.category,
                        post.created,
                        post.modified,
                        QPostLikes.postLikes.count()
                ))
                .from(post)
                .groupBy(post.id)
                .innerJoin(post.account, account)
                .leftJoin(QPostLikes.postLikes).on(QPostLikes.postLikes.post.eq(post))
                .where(post.id.eq(post1.getId()))
                .fetchOne();

        assertThat(subDetail.getLike_count()).isEqualTo(2);

    }

    @Test
    // 좋아요, 조회수가 많은 게시물 상위 10개
    public void 추천피드() throws Exception {
        Account testAccount = testAccountSet();
        Account testAccount2 = testAccountSet();
        Account testAccount3 = testAccountSet();
        Account testAccount4 = testAccountSet();
        Post post1 = testPostSet(testAccount4, PostBoard.QNA);
        Post post2 = testPostSet(testAccount4, PostBoard.QNA);
        Post post3 = testPostSet(testAccount4, PostBoard.QNA);
        Post post4 = testPostSet(testAccount4, PostBoard.QNA);
        Post post5 = testPostSet(testAccount4, PostBoard.QNA);
        Post post6 = testPostSet(testAccount4, PostBoard.QNA);
        Post post7 = testPostSet(testAccount4, PostBoard.QNA);
        Post post8 = testPostSet(testAccount4, PostBoard.QNA);
        Post post9 = testPostSet(testAccount4, PostBoard.QNA);
        testPostLikes(post1, testAccount);
        testPostLikes(post1, testAccount2);
        testPostLikes(post1, testAccount3);
        testPostLikes(post1, testAccount4);
        testPostLikes(post2, testAccount2);
        testPostLikes(post2, testAccount3);
        testPostLikes(post2, testAccount4);
        testPostLikes(post4, testAccount3);
        testPostLikes(post4, testAccount4);
        testPostLikes(post3, testAccount4);
        Pageable pageable = PageRequest.of(0,12);

        List<PostResponseDto.PostPageMain> result = queryFactory
                .select(
                        Projections.constructor(PostResponseDto.PostPageMain.class,
                                post.id,
                                account.id,
                                account.nickname,
                                account.profileImg,
                                post.title,
                                post.content,
                                post.category,
                                post.created,
                                post.isSelected,
                                postLikes.id.count()
                                ))
                .from(post)
                .join(post.account, account)
                .leftJoin(postLikes).on(postLikes.post.eq(post))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .groupBy(post.id)
                .orderBy(postLikes.id.count().desc(), post.view.desc())
                .fetch();

        assertThat(result.get(0).getPost_id()).isEqualTo(post1.getId());
        assertThat(result.get(1).getPost_id()).isEqualTo(post2.getId());
        assertThat(result.get(2).getPost_id()).isEqualTo(post4.getId());
        assertThat(result.get(3).getPost_id()).isEqualTo(post3.getId());
    }

    @Test
    public void 전체최신순() throws Exception {
        Account testAccount = testAccountSet();
        Account testAccount2 = testAccountSet();
        Account testAccount3 = testAccountSet();
        Account testAccount4 = testAccountSet();
        Post post1 = testPostSet(testAccount2, PostBoard.QNA);
        Post post2 = testPostSet(testAccount2, PostBoard.QNA);
        Post post3 = testPostSet(testAccount2, PostBoard.QNA);
        Post post4 = testPostSet(testAccount2, PostBoard.QNA);
        Post post5 = testPostSet(testAccount2, PostBoard.QNA);
        Post post6 = testPostSet(testAccount2, PostBoard.QNA);
        Post post7 = testPostSet(testAccount2, PostBoard.QNA);
        Post post8 = testPostSet(testAccount2, PostBoard.INFO);
        Post post9 = testPostSet(testAccount2, PostBoard.INFO);
        Post post10 = testPostSet(testAccount2, PostBoard.INFO);

        Pageable pageable = PageRequest.of(0,12);
        List<PostResponseDto.PostPageMain> list = queryFactory
                .select(Projections.constructor(PostResponseDto.PostPageMain.class,
                        post.id,
                        account.id,
                        account.nickname,
                        account.profileImg,
                        post.title,
                        post.content,
                        post.category,
                        post.created,
                        post.isSelected,
                        postLikes.count()
                ))
                .from(post)
                .join(account).on(account.id.eq(post.account.id))
                .leftJoin(postLikes).on(postLikes.post.eq(post))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .where(isLastPostId(50L), post.board.eq(PostBoard.QNA))
                .groupBy(post.id)
                .orderBy(post.created.desc())
                .fetch();

        for(int i = 0 ; i<list.size(); i++){
            System.out.println("post"+i+" : "+ list.get(i).getPost_id());
        }
        assertThat(list.get(6).getPost_id()).isEqualTo(post1.getId());
        assertThat(list.get(0).getPost_id()).isEqualTo(post7.getId());
        assertThat(list.get(3).getPost_id()).isNotEqualTo(post8.getId());
    }

    @Test
    public void 전체좋아요순() throws Exception {
        Account testAccount = testAccountSet();
        Account testAccount2 = testAccountSet();
        Account testAccount3 = testAccountSet();
        Account testAccount4 = testAccountSet();
        Post post1 = testPostSet(testAccount2, PostBoard.QNA);
        Post post2 = testPostSet(testAccount2, PostBoard.QNA);
        Post post3 = testPostSet(testAccount2, PostBoard.QNA);
        Post post4 = testPostSet(testAccount2, PostBoard.QNA);
        Post post5 = testPostSet(testAccount2, PostBoard.QNA);
        Post post6 = testPostSet(testAccount2, PostBoard.QNA);
        Post post7 = testPostSet(testAccount2, PostBoard.QNA);
        Post post8 = testPostSet(testAccount2, PostBoard.INFO);
        Post post9 = testPostSet(testAccount2, PostBoard.INFO);
        Post post10 = testPostSet(testAccount2, PostBoard.INFO);
        testPostLikes(post1, testAccount);
        testPostLikes(post1, testAccount2);
        testPostLikes(post1, testAccount3);
        testPostLikes(post1, testAccount4);
        testPostLikes(post2, testAccount2);
        testPostLikes(post3, testAccount3);
        testPostLikes(post4, testAccount4);

        Pageable pageable = PageRequest.of(0,12);
        List<PostResponseDto.PostPageMain> list = queryFactory
                .select(Projections.constructor(PostResponseDto.PostPageMain.class,
                        post.id,
                        account.id,
                        account.nickname,
                        account.profileImg,
                        post.title,
                        post.content,
                        post.category,
                        post.created,
                        post.isSelected,
                        postLikes.count()
                ))
                .from(post)
                .join(account).on(account.id.eq(post.account.id))
                .leftJoin(postLikes).on(postLikes.post.eq(post))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .where(isLastPostId(50L), post.board.eq(PostBoard.QNA))
                .groupBy(post.id)
                .orderBy(postLikes.count().desc())
                .fetch();

        for(int i = 0 ; i<list.size(); i++){
            System.out.println("post"+i+" : "+ list.get(i).getPost_id());
        }
        assertThat(list.get(0).getPost_id()).isEqualTo(post1.getId());
        assertThat(list.get(3).getPost_id()).isEqualTo(post4.getId());
        assertThat(list.get(6).getPost_id()).isEqualTo(post7.getId());
    }

    private BooleanExpression isLastPostId(Long isLastPostId) {
        return isLastPostId != 0 ? post.id.lt(isLastPostId) : null;
    }

}
