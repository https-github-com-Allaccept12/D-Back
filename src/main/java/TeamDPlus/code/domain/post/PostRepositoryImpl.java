package TeamDPlus.code.domain.post;

import TeamDPlus.code.domain.artwork.image.QArtWorkImage;
import TeamDPlus.code.dto.response.ArtWorkResponseDto;
import TeamDPlus.code.dto.response.PostResponseDto;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import TeamDPlus.code.domain.post.QPost;



import java.util.List;

import static TeamDPlus.code.domain.account.QAccount.account;
import static TeamDPlus.code.domain.post.QPost.post;
import static TeamDPlus.code.domain.post.image.QPostImage.postImage;

@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    // 전체 페이지
    @Override
    public Page<PostResponseDto.PostPageMain> findAllPost(Long lastPostId, Pageable pageable) {
        List<PostResponseDto.PostPageMain> result = jpaQueryFactory
                .select(Projections.constructor(PostResponseDto.PostPageMain.class,
                        post.id,
                        account.id,
                        account.nickname,
                        post.title,
                        postImage.postImg,
                        post.category,
                        post.view,
                        post.content,
                        post.created,
                        post.modified
                ))
                .from(post)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(post.created.desc())
                .fetch();

        int count = result.size();

        return new PageImpl<>(result, pageable, count);
    }

    public BooleanExpression isLastPost(Long lastPostId){
        return lastPostId != 0 ? post.id.lt(lastPostId) : null;
    }
}
