<<<<<<< HEAD
//package TeamDPlus.code.domain.post;
//
//import TeamDPlus.code.domain.account.Account;
//import TeamDPlus.code.domain.account.AccountRepository;
//import TeamDPlus.code.domain.artwork.ArtWorks;
//import TeamDPlus.code.domain.artwork.image.ArtWorkImage;
//import TeamDPlus.code.domain.post.bookmark.PostBookMark;
//import TeamDPlus.code.domain.post.bookmark.PostBookMarkRepository;
//import TeamDPlus.code.domain.post.bookmark.QPostBookMark;
//import TeamDPlus.code.domain.post.comment.PostComment;
//import TeamDPlus.code.domain.post.comment.PostCommentRepository;
//import TeamDPlus.code.domain.post.comment.QPostComment;
//import TeamDPlus.code.domain.post.image.PostImage;
//import TeamDPlus.code.domain.post.image.PostImageRepository;
//import TeamDPlus.code.domain.post.like.PostLikes;
//import TeamDPlus.code.domain.post.like.PostLikesRepository;
//import TeamDPlus.code.domain.post.like.QPostLikes;
//import TeamDPlus.code.dto.response.PostResponseDto;
//import com.querydsl.core.types.Projections;
//import com.querydsl.jpa.impl.JPAQueryFactory;
//import lombok.extern.slf4j.Slf4j;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.test.annotation.Commit;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.transaction.annotation.Transactional;
//
//import javax.persistence.EntityManager;
//import java.util.List;
//
//import static TeamDPlus.code.domain.account.QAccount.account;
//import static TeamDPlus.code.domain.artwork.QArtWorks.artWorks;
//import static TeamDPlus.code.domain.post.QPost.post;
//import static TeamDPlus.code.domain.post.bookmark.QPostBookMark.*;
//import static TeamDPlus.code.domain.post.comment.QPostComment.*;
//import static TeamDPlus.code.domain.post.like.QPostLikes.*;
//import static org.assertj.core.api.Assertions.assertThat;
//
//@SpringBootTest
//@Transactional
//@Slf4j
//public class PostRepositoryTests {
//
//    @Autowired
//    EntityManager em;
//
//    @Autowired
//    JPAQueryFactory queryFactory;
//
//    @Autowired
//    AccountRepository accountRepository;
//
//    @Autowired
//    PostImageRepository postImageRepository;
//
//    @Autowired
//    PostBookMarkRepository postBookMarkRepository;
//
//    @Autowired
//    PostCommentRepository postCommentRepository;
//
//    @Autowired
//    PostLikesRepository postLikesRepository;
//    @Autowired
//    PostRepository postRepository;
//
//    @Test
//    @Commit
//    public void 전체포스트_목록() throws Exception {
//        //given account,artwork,
//        Account testAccount = testAccountSet();
//        Post post1 = testPostSet(testAccount);
//        Post post2 = testPostSet(testAccount);
//
//        Pageable pageable = PageRequest.of(0,3);
//        // when
//        List<PostResponseDto.PostPageMain> fetch = queryFactory
//                .select(Projections.constructor(PostResponseDto.PostPageMain.class,
//                        post.id,
//                        account.id,
//                        account.nickname,
//                        account.profileImg,
//                        post.title,
//                        post.category,
//                        post.content,
//                        post.created
//                ))
//                .from(post)
//                .join(account).on(account.id.eq(post.account.id))
//                .offset(pageable.getOffset())
//                .limit(pageable.getPageSize())
//                .where(post.id.lt(12))
//                .fetch();
//        for (PostResponseDto.PostPageMain postPageMain : fetch) {
//            System.out.println("postPageMain = " + postPageMain.getAccount_id());
//            System.out.println("postPageMain.getPost_id() = " + postPageMain.getPost_id());
//        }
//
//        // then
//        assertThat(fetch.size()).isEqualTo(2);
//        assertThat(fetch.get(0).getAccount_id()).isEqualTo(testAccount.getId());
//        assertThat(fetch.get(1).getAccount_id()).isEqualTo(testAccount.getId());
//    }
//
//    private Account testAccountSet() {
//        Account testAccount = Account.builder()
//                .email("test")
//                .nickname("test")
//                .titleContent("test")
//                .subContent("test")
//                .career(1)
//                .tendency("무슨무슨형")
//                .build();
//        Account save = accountRepository.save(testAccount);
//        em.flush();
//        em.clear();
//        return save;
//    }
//
//    private Post testPostSet(Account account) {
//        Post post = Post.builder()
//                .title("test")
//                .category("test")
//                .content("test")
//                .account(account)
//                .build();
//        Post postSaved = postRepository.save(post);
//        em.flush();
//        em.clear();
//        return postSaved;
//    }
//
//    private PostImage testPostImageSet(Post post, String test) {
//        PostImage testArtWorkImage = PostImage.builder()
//                .post(post)
//                .postImg(test)
//                .build();
//        PostImage save = postImageRepository.save(testArtWorkImage);
//
//        em.flush();
//        em.clear();
//        return save;
//    }
//
//    private PostBookMark testPostBookMark(Post post, Account account){
//        PostBookMark testPostBookmark = PostBookMark.builder()
//                .post(post)
//                .account(account)
//                .build();
//        PostBookMark save = postBookMarkRepository.save(testPostBookmark);
//        em.flush();
//        em.clear();
//        return save;
//    }
//
//    private PostComment testPostComment(Post post, Account account, String content){
//        PostComment comment = PostComment.builder()
//                .account(account)
//                .post(post)
//                .content(content)
//                .build();
//        PostComment save = postCommentRepository.save(comment);
//        em.flush();
//        em.clear();
//        return save;
//    }
//
//    private PostLikes testPostLikes(Post post, Account account){
//        PostLikes likes = PostLikes.builder()
//                .post(post)
//                .account(account)
//                .build();
//        PostLikes save = postLikesRepository.save(likes);
//        em.flush();
//        em.clear();
//        return save;
//    }
//}
=======
package TeamDPlus.code.domain.post;

import TeamDPlus.code.domain.account.Account;
import TeamDPlus.code.domain.account.AccountRepository;
import TeamDPlus.code.domain.artwork.ArtWorks;
import TeamDPlus.code.domain.artwork.image.ArtWorkImage;
import TeamDPlus.code.domain.post.bookmark.PostBookMark;
import TeamDPlus.code.domain.post.bookmark.PostBookMarkRepository;
import TeamDPlus.code.domain.post.bookmark.QPostBookMark;
import TeamDPlus.code.domain.post.comment.PostComment;
import TeamDPlus.code.domain.post.comment.PostCommentRepository;
import TeamDPlus.code.domain.post.comment.QPostComment;
import TeamDPlus.code.domain.post.comment.like.PostCommentLikesRepository;
import TeamDPlus.code.domain.post.image.PostImage;
import TeamDPlus.code.domain.post.image.PostImageRepository;
import TeamDPlus.code.domain.post.like.PostLikes;
import TeamDPlus.code.domain.post.like.PostLikesRepository;
import TeamDPlus.code.domain.post.like.QPostLikes;
import TeamDPlus.code.domain.post.tag.PostTagRepository;
import TeamDPlus.code.dto.response.PostResponseDto;
import com.querydsl.core.types.Projections;
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
        Post post1 = testPostSet(testAccount);
        Post post2 = testPostSet(testAccount);

        Pageable pageable = PageRequest.of(0,3);
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
>>>>>>> 02cdf82da8e989874b0c5964fbfb1f9d6a28acbe
