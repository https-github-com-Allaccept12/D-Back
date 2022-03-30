package com.example.dplus.dto.response;

import com.example.dplus.domain.post.Post;
import com.example.dplus.dto.response.PostResponseDto.PostPageMain;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class PostMainResponseDto {

    private List<PostPageMain> postRecommendationFeed;
    private List<PostPageMain> postMainPage;

    @Builder
    public PostMainResponseDto(List<PostPageMain> postRecommendationFeed,
                               List<PostPageMain> postMainPage) {
        this.postRecommendationFeed = postRecommendationFeed;
        this.postMainPage = postMainPage;
    }
    public static PostMainResponseDto of(List<Post> postList,List<Post> postRecommendationFeed) {
        return PostMainResponseDto.builder()
                .postMainPage(postList.stream()
                        .map(post -> new PostPageMain(post.getId(),
                                post.getAccount().getId(),
                                post.getAccount().getNickname(),
                                post.getAccount().getProfileImg(),
                                post.getTitle(),
                                post.getContent(),
                                post.getCategory(),
                                post.getCreated(),
                                post.isSelected(),
                                (long) post.getPostLikeList().size(),
                                post.getPostTagList(),
                                (long) post.getPostCommentList().size()))
                        .collect(Collectors.toList()))
                .postRecommendationFeed(postRecommendationFeed.stream()
                        .map(post -> new PostPageMain(post.getId(),
                                post.getAccount().getId(),
                                post.getAccount().getNickname(),
                                post.getAccount().getProfileImg(),
                                post.getTitle(),
                                post.getContent(),
                                post.getCategory(),
                                post.getCreated(),
                                post.isSelected(),
                                (long) post.getPostLikeList().size(),
                                post.getPostTagList(),
                                (long) post.getPostCommentList().size()))
                        .collect(Collectors.toList()))
                .build();


    }

}
