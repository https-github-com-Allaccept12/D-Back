package com.code.domain.post;

import com.example.dplus.domain.account.Account;
import com.example.dplus.domain.post.*;
import com.example.dplus.repository.account.AccountRepository;
import com.example.dplus.domain.account.Rank;
import com.example.dplus.repository.account.rank.RankRepository;
import com.example.dplus.repository.post.PostRepository;
import com.example.dplus.repository.post.answer.PostAnswerRepository;
import com.example.dplus.repository.post.bookmark.PostBookMarkRepository;
import com.example.dplus.repository.post.comment.PostCommentRepository;

import com.example.dplus.repository.post.comment.PostCommentLikesRepository;
import com.example.dplus.repository.post.image.PostImageRepository;
import com.example.dplus.repository.post.like.PostAnswerLikesRepository;
import com.example.dplus.repository.post.like.PostLikesRepository;
import com.example.dplus.repository.post.tag.PostTagRepository;

import com.example.dplus.dto.response.AccountResponseDto;
import com.example.dplus.dto.response.PostResponseDto;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static com.example.dplus.domain.account.QAccount.account;
import static com.example.dplus.domain.post.QPost.post;
import static com.example.dplus.domain.post.QPostAnswer.postAnswer;
import static com.example.dplus.domain.post.QPostAnswerLikes.postAnswerLikes;
import static com.example.dplus.domain.post.QPostBookMark.postBookMark;
import static com.example.dplus.domain.post.QPostComment.postComment;
import static com.example.dplus.domain.post.QPostCommentLikes.postCommentLikes;
import static com.example.dplus.domain.post.QPostLikes.postLikes;
import static com.example.dplus.domain.post.QPostTag.postTag;
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

    @Autowired
    PostAnswerRepository postAnswerRepository;

    @Autowired
    PostAnswerLikesRepository postAnswerLikesRepository;

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
                        post.content,
                        post.category,
                        post.created,
                        post.isSelected
                ))
                .from(post)
                .join(account).on(account.id.eq(post.account.id))
                .leftJoin(postTag).on(postTag.post.eq(post))
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

    @Test
    @Commit
    public void 답글_서브_디테일() throws Exception {
        //given account,artwork,
        Account testAccount = testAccountSet();
        Post post1 = testPostSet(testAccount, PostBoard.QNA);

        testPostAnswer(post1, testAccount, "testcontent");
        testPostAnswer(post1, testAccount, "testcontent");
        testPostLikes(post1, testAccount);
        testPostLikes(post1, testAccount);
        testPostLikes(post1, testAccount);

        // when
        PostResponseDto.PostAnswerSubDetail fetch = queryFactory
                .select(Projections.constructor(PostResponseDto.PostAnswerSubDetail.class,
                        post.id,
                        account.id,
                        account.profileImg,
                        account.nickname,
                        post.title,
                        post.content,
                        post.view,
                        postLikes.count(),
                        post.category,
                        post.created,
                        post.modified,
                        post.isSelected
                ))
                .from(post)
                .innerJoin(post.account, account)
                .leftJoin(postLikes).on(postLikes.post.eq(post))
                .where(post.id.eq(post1.getId()))
                .groupBy(post.id)
                .fetchOne();

        //then
        assert fetch != null;

        assertThat(fetch.getLike_count()).isEqualTo(3);
    }

    @Test
    @Commit
    public void 카테고리로_찾기() throws Exception {
        //given account,artwork,
        Account testAccount = testAccountSet();
        Post post1 = testPostSet(testAccount, PostBoard.QNA);
        Post post2 = testPostSet(testAccount, PostBoard.QNA);
        Post post3 = testPostSet(testAccount, PostBoard.QNA);

        testPostAnswer(post1, testAccount, "testcontent");
        testPostAnswer(post1, testAccount, "testcontent");
        testPostLikes(post1, testAccount);
        testPostLikes(post1, testAccount);
        testPostLikes(post1, testAccount);

        // when
        List<PostResponseDto.PostSimilarQuestion> fetch = queryFactory
                .select(Projections.constructor(PostResponseDto.PostSimilarQuestion.class,
                        post.id,
                        account.id,
                        account.profileImg,
                        post.title,
                        post.content,
                        postLikes.count(),
                        post.category,
                        post.created,
                        post.modified
                ))
                .from(post)
                .join(post.account, account)
                .leftJoin(postLikes).on(postLikes.post.eq(post))
                .where(post.category.eq(post1.getCategory()))
                .offset(0)
                .limit(5)
                .groupBy(post.id)
                .orderBy(postLikes.count().desc(), post.view.desc())
                .fetch();

        //then
        assert fetch != null;
        assertThat(fetch.size()).isEqualTo(3);
    }

    @Test
    @Commit
    public void 나의_질문() throws Exception {
        //given account,artwork,
        Account testAccount = testAccountSet();
        Account testAccount2 = testAccountSet();

        Post post1 = testPostSet(testAccount, PostBoard.QNA);
        Post post2 = testPostSet(testAccount, PostBoard.QNA);
        Post post3 = testPostSet(testAccount, PostBoard.QNA);
        Post post4 = testPostSet(testAccount2, PostBoard.QNA);

        testPostAnswer(post1, testAccount, "testcontent");
        testPostAnswer(post1, testAccount, "testcontent");
        testPostLikes(post1, testAccount);
        testPostLikes(post1, testAccount);
        testPostLikes(post1, testAccount);
        testPostLikes(post2, testAccount);
        testPostLikes(post2, testAccount);
        testPostLikes(post3, testAccount);
        testPostLikes(post4, testAccount2);

        Pageable pageable = PageRequest.of(0,5);

        // when
        List<AccountResponseDto.MyPost> fetch = queryFactory
                .select(Projections.constructor(AccountResponseDto.MyPost.class,
                        post.id,
                        post.title,
                        post.content,
                        postLikes.count(),
                        post.created,
                        post.modified,
                        account.profileImg
                ))
                .from(post)
                .join(post.account, account).on(account.id.eq(testAccount.getId()))
                .leftJoin(postLikes).on(postLikes.post.eq(post))
                .where(post.board.eq(PostBoard.valueOf("QNA")))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .groupBy(post.id)
                .orderBy(postLikes.count().desc(), post.view.desc())
                .fetch();

        for (AccountResponseDto.MyPost myPost : fetch) {
            System.out.println(myPost.getLike_count());
        }

        //then
        assert fetch != null;
        assertThat(fetch.size()).isEqualTo(3);
    }

    @Test
    @Commit
    public void 내가스크랩한글() throws Exception {
        //given account,artwork,
        Account testAccount = testAccountSet();
        Account testAccount2 = testAccountSet();

        Post post1 = testPostSet(testAccount,PostBoard.QNA);
        Post post2 = testPostSet(testAccount, PostBoard.QNA);
        Post post3 = testPostSet(testAccount, PostBoard.QNA);
        Post post4 = testPostSet(testAccount2, PostBoard.QNA);

        testPostAnswer(post1, testAccount, "testcontent");
        testPostAnswer(post1, testAccount, "testcontent");
        testPostLikes(post1, testAccount);
        testPostLikes(post1, testAccount);
        testPostLikes(post1, testAccount);
        testPostLikes(post2, testAccount);
        testPostLikes(post2, testAccount);
        testPostLikes(post3, testAccount);
        testPostBookMark(post1, testAccount);
        testPostBookMark(post2, testAccount2);

        Pageable pageable = PageRequest.of(0,5);

        // when
        List<AccountResponseDto.MyPost> fetch = queryFactory
                .select(Projections.constructor(AccountResponseDto.MyPost.class,
                        post.id,
                        post.title,
                        post.content,
                        postLikes.count(),
                        post.created,
                        post.modified,
                        post.account.profileImg
                ))
                .from(post)
                .join(postBookMark).on(postBookMark.post.eq(post))
                .leftJoin(postLikes).on(postLikes.post.eq(post))
                .where(postBookMark.account.id.eq(testAccount.getId()), post.board.eq(PostBoard.valueOf("QNA")))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .groupBy(post.id)
                .orderBy(post.created.desc())
                .fetch();

        for (AccountResponseDto.MyPost myPost : fetch) {
            System.out.println(myPost.getLike_count());
        }

        //then
        assert fetch != null;
        assertThat(fetch.size()).isEqualTo(1);
    }

    @Test
    @Commit
    public void 답글불러오기() throws Exception {
        //given account,artwork,
        Account testAccount = testAccountSet();
        Post post1 = testPostSet(testAccount, PostBoard.QNA);
        Post post2 = testPostSet(testAccount, PostBoard.QNA);

        PostAnswer answer1 = testPostAnswer(post1, testAccount, "testcontent");
        testPostAnswer(post1, testAccount, "testcontent");
        testPostAnswer(post1, testAccount, "testcontent");
        testPostAnswer(post1, testAccount, "testcontent");
        testPostAnswer(post2, testAccount, "testcontent");
        testPostAnswer(post2, testAccount, "testcontent");
        testPostAnswerLikes(answer1, testAccount);
        testPostAnswerLikes(answer1, testAccount);
        testPostAnswerLikes(answer1, testAccount);

        // when
        List<PostResponseDto.PostAnswer> fetch = queryFactory
                .select(Projections.constructor(PostResponseDto.PostAnswer.class,
                        postAnswer.id,
                        postAnswer.account.id,
                        postAnswer.account.nickname,
                        postAnswer.account.profileImg,
                        postAnswer.content,
                        postAnswer.modified,
                        postAnswer.isSelected,
                        postAnswerLikes.count()
                ))
                .from(postAnswer)
                .innerJoin(postAnswer.account, account)
                .leftJoin(postAnswerLikes).on(postAnswer.id.eq(postAnswerLikes.postAnswer.id))
                .where(postAnswer.post.id.eq(post1.getId()))
                .groupBy(postAnswer.id)
                .orderBy(postAnswerLikes.count().desc())
                .fetch();

        //then
        assert fetch != null;
        assertThat(fetch.size()).isEqualTo(4);
        assertThat(fetch.get(0).getLike_count()).isEqualTo(3);
    }

    @Test
    @Commit
    public void 나의답글() throws Exception {
        //given account,artwork,
        Account testAccount = testAccountSet();
        Account testAccount2 = testAccountSet();
        Post post1 = testPostSet(testAccount, PostBoard.QNA);
        Post post2 = testPostSet(testAccount, PostBoard.QNA);

        PostAnswer answer1 = testPostAnswer(post1, testAccount, "testcontent");
        testPostAnswer(post1, testAccount, "testcontent");
        testPostAnswer(post1, testAccount2, "testcontent");
        testPostAnswer(post2, testAccount, "testcontent");
        testPostAnswer(post2, testAccount2, "testcontent");
        testPostAnswerLikes(answer1, testAccount);
        testPostAnswerLikes(answer1, testAccount);
        testPostAnswerLikes(answer1, testAccount);

        Pageable pageable = PageRequest.of(0,5);

        // when
        List<AccountResponseDto.MyAnswer> fetch = queryFactory
                .select(Projections.constructor(AccountResponseDto.MyAnswer.class,
                        postAnswer.id,
                        postAnswer.content,
                        postAnswerLikes.count(),
                        postAnswer.created,
                        postAnswer.modified,
                        account.profileImg
                ))
                .from(postAnswer)
                .join(postAnswer.account, account).on(account.id.eq(testAccount.getId()))
                .leftJoin(postAnswerLikes).on(postAnswer.id.eq(postAnswerLikes.postAnswer.id))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .groupBy(postAnswer.id)
                .orderBy(postAnswerLikes.count().desc())
                .fetch();

        //then
        assert fetch != null;
        assertThat(fetch.size()).isEqualTo(3);
        assertThat(fetch.get(0).getLike_count()).isEqualTo(3);
    }

    @Test
    @Commit
    public void 나의댓글() throws Exception {
        //given account,artwork,
        Account testAccount = testAccountSet();
        Account testAccount2 = testAccountSet();
        Post post1 = testInfoSet(testAccount);
        Post post2 = testInfoSet(testAccount);

        PostComment comment1 = testPostComment(post1, testAccount);
        testPostComment(post1, testAccount);
        testPostComment(post1, testAccount2);
        testPostComment(post2, testAccount);
        testPostComment(post2, testAccount2);
        testPostCommentLikes(comment1, testAccount);
        testPostCommentLikes(comment1, testAccount);
        testPostCommentLikes(comment1, testAccount);

        Pageable pageable = PageRequest.of(0,5);

        // when
        List<AccountResponseDto.MyComment> fetch = queryFactory
                .select(Projections.constructor(AccountResponseDto.MyComment.class,
                        postComment.id,
                        postComment.content,
                        postCommentLikes.count(),
                        postComment.created,
                        postComment.modified,
                        account.profileImg
                ))
                .from(postComment)
                .join(postComment.account, account).on(account.id.eq(testAccount.getId()))
                .leftJoin(postCommentLikes).on(postComment.id.eq(postCommentLikes.postComment.id))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .groupBy(postComment.id)
                .orderBy(postCommentLikes.count().desc())
                .fetch();

        //then
        assert fetch != null;
        assertThat(fetch.size()).isEqualTo(3);
        assertThat(fetch.get(0).getLike_count()).isEqualTo(3);
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
                .board(PostBoard.QNA)
                .build();
        Post postSaved = postRepository.save(post);
        em.flush();
        em.clear();
        return postSaved;
    }

    private Post testInfoSet(Account account) {
        Post post = Post.builder()
                .title("test")
                .category("test")
                .content("test")
                .account(account)
                .board(PostBoard.INFO)
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

    private PostAnswer testPostAnswer(Post post, Account account, String content) {
        PostAnswer answer = PostAnswer.builder()
                .account(account)
                .post(post)
                .content(content)
                .isSelected(true)
                .build();
        PostAnswer saveAnswer = postAnswerRepository.save(answer);
        em.flush();
        em.clear();
        return saveAnswer;
    }

    private PostLikes testPostLikes(Post post, Account account){
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
        return save;
    }
    private PostAnswerLikes testPostAnswerLikes(PostAnswer postAnswer, Account account){
        PostAnswerLikes answerLikes = PostAnswerLikes.builder()
                .postAnswer(postAnswer)
                .account(account)
                .build();
        PostAnswerLikes save = postAnswerLikesRepository.save(answerLikes);

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
