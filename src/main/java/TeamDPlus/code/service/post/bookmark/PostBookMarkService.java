package TeamDPlus.code.service.post.bookmark;

import TeamDPlus.code.advice.ApiRequestException;
import TeamDPlus.code.domain.account.Account;
import TeamDPlus.code.domain.post.Post;
import TeamDPlus.code.domain.post.PostRepository;
import TeamDPlus.code.domain.post.bookmark.PostBookMark;
import TeamDPlus.code.domain.post.bookmark.PostBookMarkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostBookMarkService {

    private final PostBookMarkRepository postBookMarkRepository;
    private final PostRepository postRepository;

    public void doBookMark(Account account, Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ApiRequestException("존재하지 않는 게시글 입니다."));
        if (postBookMarkRepository.existByAccountIdAndPostId(account.getId(), postId)) {
            throw new ApiRequestException("이미 북마크한 게시글 입니다.");
        }
        PostBookMark postBookMark = PostBookMark.builder().post(post).account(account).build();
        postBookMarkRepository.save(postBookMark);
    }

    public void unBookMark(Account account, Long postId) {
        postBookMarkRepository.deleteByPostIdAndAccountId(postId,account.getId());
    }

}
