package TeamDPlus.code.service.post;

import TeamDPlus.code.domain.account.Account;
import TeamDPlus.code.domain.account.AccountRepository;
import TeamDPlus.code.domain.post.Post;
import TeamDPlus.code.domain.post.PostRepository;
import TeamDPlus.code.domain.post.bookmark.PostBookMark;
import TeamDPlus.code.domain.post.bookmark.PostBookMarkRepository;
import TeamDPlus.code.domain.post.comment.PostComment;
import TeamDPlus.code.domain.post.comment.PostCommentRepository;
import TeamDPlus.code.domain.post.image.PostImage;
import TeamDPlus.code.domain.post.image.PostImageRepository;
import TeamDPlus.code.domain.post.like.PostLikes;
import TeamDPlus.code.domain.post.like.PostLikesRepository;
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

@SpringBootTest
@Transactional
@Slf4j
public class PostMainPageServiceImplTest {

    @Autowired
    EntityManager em;

    @Autowired
    JPAQueryFactory queryFactory;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    PostImageRepository postImageRepository;

    @Autowired
    PostBookMarkRepository postBookMarkRepository;

    @Autowired
    PostCommentRepository postCommentRepository;

    @Autowired
    PostLikesRepository postLikesRepository;
    @Autowired
    PostRepository postRepository;

    @Test
    @Commit
    public void 전체포스트_목록_서비스() throws Exception {
        Account testAccount = testAccountSet();
        Post post1 = testPostSet(testAccount);
        Post post2 = testPostSet(testAccount);
        PostLikes postLikes1 = testPostLikes(post1, testAccount);
        PostLikes postLikes2 = testPostLikes(post2, testAccount);
        PostBookMark postBookMark1 = testPostBookMark(post1, testAccount);
        PostBookMark postBookMark2 = testPostBookMark(post2, testAccount);

        PostComment postComment1 = testPostComment(post1, testAccount, "와우");
        PostComment postComment2 = testPostComment(post2, testAccount, "와우2");

        Pageable pageable = PageRequest.of(0,3);

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

    private Post testPostSet(Account account) {
        Post post = Post.builder()
                .title("test")
                .category("test")
                .content("test")
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

    private PostBookMark testPostBookMark(Post post, Account account){
        PostBookMark testPostBookmark = PostBookMark.builder()
                .post(post)
                .account(account)
                .build();
        PostBookMark save = postBookMarkRepository.save(testPostBookmark);
        em.flush();
        em.clear();
        return save;
    }

    private PostComment testPostComment(Post post, Account account, String content){
        PostComment comment = PostComment.builder()
                .account(account)
                .post(post)
                .content(content)
                .build();
        PostComment save = postCommentRepository.save(comment);
        em.flush();
        em.clear();
        return save;
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
}
