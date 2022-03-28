package com.example.dplus.service.post;

import com.example.dplus.domain.account.Account;
import com.example.dplus.repository.account.AccountRepository;
import com.example.dplus.domain.post.Post;
import com.example.dplus.repository.post.PostRepository;
import com.example.dplus.domain.post.PostBookMark;
import com.example.dplus.repository.post.bookmark.PostBookMarkRepository;
import com.example.dplus.domain.post.PostComment;
import com.example.dplus.repository.post.comment.PostCommentRepository;
import com.example.dplus.domain.post.PostImage;
import com.example.dplus.repository.post.image.PostImageRepository;
import com.example.dplus.domain.post.PostLikes;
import com.example.dplus.repository.post.like.PostLikesRepository;
import com.example.dplus.dto.response.PostResponseDto;
import com.querydsl.core.types.Projections;
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
import static org.assertj.core.api.Assertions.assertThat;

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
        Post post1 = testPostSet(testAccount,"테스트입니다.1","테스트내용1");
        Post post2 = testPostSet(testAccount,"테스트입니다.1","테스트내용1");
        PostLikes postLikes1 = testPostLikes(post1, testAccount);
        PostLikes postLikes2 = testPostLikes(post2, testAccount);
        PostBookMark postBookMark1 = testPostBookMark(post1, testAccount);
        PostBookMark postBookMark2 = testPostBookMark(post2, testAccount);

        PostComment postComment1 = testPostComment(post1, testAccount, "와우");
        PostComment postComment2 = testPostComment(post2, testAccount, "와우2");

        Pageable pageable = PageRequest.of(0,3);

    }

    @Test
    public void 게시글_검색() throws Exception {
        //given
        Account account1 = testAccountSet();
        Post post1 = testPostSet(account1,"테스트입니다.1","테스트 내용1");
        Post post2 = testPostSet(account1,"123123test312321","테스트내용1231231");
        Pageable pageable = PageRequest.of(0,5);
        String keyword = "테스트";
        //when
        List<PostResponseDto.PostPageMain> result = queryFactory
                .select(Projections.constructor(PostResponseDto.PostPageMain.class,
                        post.id,
                        account.id,
                        account.nickname,
                        account.profileImg,
                        post.title,
                        post.content,
                        post.category,
                        post.created
                ))
                .from(post)
                .join(account).on(account.id.eq(post.account.id))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .where(post.id.lt(10L),
                        post.title.contains(keyword)
                                .or(post.content.contains(keyword))
                                .or(post.account.nickname.contains(keyword)))
                .fetch();
        //then
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0).getAccount_id()).isEqualTo(post2.getAccount().getId());
        assertThat(result.get(0).getContent()).isEqualTo(post1.getContent());

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

    private Post testPostSet(Account account , String title, String content) {
        Post post = Post.builder()
                .title(title)
                .category("test")
                .content(content)
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
