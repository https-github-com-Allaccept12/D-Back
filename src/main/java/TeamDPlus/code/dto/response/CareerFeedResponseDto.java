package TeamDPlus.code.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

public class CareerFeedResponseDto {


    @Getter
    @NoArgsConstructor
    public static class CareerFeed {

        private Long artwork_id;
        private Long account_id;
        private String scope;
        private String title;
        private String img;
        private String content;
        private Long view_count;
        private Boolean is_like;
        private Boolean is_bookmark;
        private Long like_count;
        private String category;
        private Timestamp create_time;
        private Timestamp modify_time;

    }
}

