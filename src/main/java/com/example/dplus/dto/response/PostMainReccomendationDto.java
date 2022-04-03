package com.example.dplus.dto.response;

import com.example.dplus.domain.post.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class PostMainReccomendationDto {

    private List<PostResponseDto.PostPageMain> postRecommendationFeed;

    @Builder
    public PostMainReccomendationDto(List<PostResponseDto.PostPageMain> postRecommendationFeed) {
        this.postRecommendationFeed = postRecommendationFeed;
    }

    public static PostMainReccomendationDto from(List<Post> postRecommendationFeed) {
        return PostMainReccomendationDto.builder()
                .postRecommendationFeed(postRecommendationFeed.stream()
                        .map(post -> new PostResponseDto.PostPageMain(post.getId(),
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
