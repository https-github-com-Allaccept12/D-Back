package TeamDPlus.code.dto.request;


import TeamDPlus.code.dto.ImageUrlDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class PostRequestDto {

    @Getter
    @NoArgsConstructor
    public static class PostUpdate {

        private String title;

        private String category;

        private String content;

        private List<ImageUrlDto> img;
    }

    @Getter
    @NoArgsConstructor
    public static class PostCreate {

        private String title;

        private String category;

        private String content;

        private List<ImageUrlDto> img;
    }
}

