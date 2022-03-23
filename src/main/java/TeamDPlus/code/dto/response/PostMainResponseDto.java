package TeamDPlus.code.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@NoArgsConstructor
public class PostMainResponseDto {

    private List<PostResponseDto.PostPageMain> postRecommendationFeed;
    private List<PostResponseDto.PostPageMain> postMainPage;

    @Builder
    public PostMainResponseDto(List<PostResponseDto.PostPageMain> postRecommendationFeed, List<PostResponseDto.PostPageMain> postMainPage) {
        this.postRecommendationFeed = postRecommendationFeed;
        this.postMainPage = postMainPage;
    }
}
