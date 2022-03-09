package TeamDPlus.code.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

public class ArtWorkResponseDto {

    @Getter
    @NoArgsConstructor
    public static class ArtWorkFeed {

        private Long artwork_id;
        private String scope;
        private String title;
        private String img;
        private Long view_count;
        private Timestamp create_time;
        private Timestamp modify_time;

        @Builder
        public ArtWorkFeed(Long artwork_id, String scope, String title,String img, Long view_count, Timestamp create_time, Timestamp modify_time) {
            this.artwork_id = artwork_id;
            this.scope = scope;
            this.title = title;
            this.img = img;
            this.view_count = view_count;
            this.create_time = create_time;
            this.modify_time = modify_time;
        }
    }
}
