package TeamDPlus.code.dto.common;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class CommonDto {


    @Getter
    @NoArgsConstructor
    public static class CommentDto {
        private Long account_id;
        private Long comment_id;
        private String content;
        private Timestamp modify_time;
    }

    @Getter
    @NoArgsConstructor
    public static class ImgUrlDto {
        private String img_url;
        private boolean thumbnail;
    }
}