package TeamDPlus.code.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class PostDto {

    @Getter
    @NoArgsConstructor
    public static class PostUpdate {

        private String title;

        private String category;

        private String content;

        private List<ImageUrlDto> img;
    }
}

