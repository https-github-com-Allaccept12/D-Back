package TeamDPlus.code.dto.request;


import TeamDPlus.code.domain.post.tag.PostTag;
import TeamDPlus.code.dto.common.CommonDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class PostRequestDto {

    @Getter
    @NoArgsConstructor
    public static class PostCreateAndUpdate {

        private String title;

        private String category;

        private String content;

        private List<CommonDto.ImgUrlDto> img;

        private List<CommonDto.PostTagDto> hashTag;

        private boolean is_selected;

    }

    @Getter
    @NoArgsConstructor
    public static class PostComment {

        private String content;

    }

    @Getter
    @NoArgsConstructor
    public static class PostAnswer {

        private String content;

    }

    @Getter
    @NoArgsConstructor
    public static class PostAnswerComment {

        private String content;

    }
}

