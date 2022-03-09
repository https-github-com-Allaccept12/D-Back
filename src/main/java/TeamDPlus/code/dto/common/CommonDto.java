package TeamDPlus.code.dto.common;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class CommonDto {


    public static class CommentDto {
        private Long account_id;
        private Long comment_id;
        private String content;
        private Timestamp modify_time;
    }

    public static class ImgUrlDto {
        private String img_url;
    }
}
