package TeamDPlus.code.dto.common;

import java.time.LocalDateTime;

public class CommonDto {


    public static class CommentDto {
        private Long account_id;
        private Long comment_id;
        private String content;
        private LocalDateTime modify_time;
    }

    public static class ImgUrlDto {
        private String img_url;
    }
}
