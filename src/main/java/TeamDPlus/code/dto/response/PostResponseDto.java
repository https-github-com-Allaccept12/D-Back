package TeamDPlus.code.dto.response;

import TeamDPlus.code.dto.ArtWorkCommentDto;
import TeamDPlus.code.dto.ImageUrlDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

public class PostResponseDto {

    @Getter
    @NoArgsConstructor
    public static class PostPageMain {

        private Long post_id;
        private Long account_id;
        private String title;
        private String img;
        private String content;
        private Long view_count;
        private Boolean is_like;
        private Boolean is_bookmark;
        private Long like_count;
        private String category;
        private LocalDateTime create_time;
        private LocalDateTime modify_time;

        @Builder

        public PostPageMain(Long post_id, Long account_id, String title, String img,
                            String content, Long view_count, Boolean is_like,
                            Boolean is_bookmark, Long like_count, String category,
                            LocalDateTime create_time, LocalDateTime modify_time) {
            this.post_id = post_id;
            this.account_id = account_id;
            this.title = title;
            this.img = img;
            this.content = content;
            this.view_count = view_count;
            this.is_like = is_like;
            this.is_bookmark = is_bookmark;
            this.like_count = like_count;
            this.category = category;
            this.create_time = create_time;
            this.modify_time = modify_time;
        }
    }

    @Getter
    @NoArgsConstructor
    public static class PostDetailPage {

        private Long post_id;
        private Long account_id;
        private String title;
        private List<ImageUrlDto> img;
        private String content;
        private Long view_count;
        private Boolean is_like;
        private Boolean is_bookmark;
        private Long like_count;
        private String category;
        private List<ArtWorkCommentDto> comment;
        private LocalDateTime create_time;
        private LocalDateTime modify_time;

        @Builder

        public PostDetailPage(Long post_id, Long account_id, String title,
                              List<ImageUrlDto> img, String content, Long view_count,
                              Boolean is_like, Boolean is_bookmark, Long like_count,
                              String category, List<ArtWorkCommentDto> comment,
                              LocalDateTime create_time, LocalDateTime modify_time) {
            this.post_id = post_id;
            this.account_id = account_id;
            this.title = title;
            this.img = img;
            this.content = content;
            this.view_count = view_count;
            this.is_like = is_like;
            this.is_bookmark = is_bookmark;
            this.like_count = like_count;
            this.category = category;
            this.comment = comment;
            this.create_time = create_time;
            this.modify_time = modify_time;
        }
    }

    @Getter
    @NoArgsConstructor
    public static class PostBookmarkPage {
        private Long post_id;
        private Long account_id;
        private String title;
        private String img;
        private String content;
        private Long view_count;
        private Boolean is_like;
        private Boolean is_bookmark;
        private Long like_count;
        private String category;
        private LocalDateTime create_time;
        private LocalDateTime modify_time;

        @Builder

        public PostBookmarkPage(Long post_id, Long account_id, String title, String img, String content,
                                Long view_count, Boolean is_like, Boolean is_bookmark, Long like_count,
                                String category, LocalDateTime create_time, LocalDateTime modify_time) {
            this.post_id = post_id;
            this.account_id = account_id;
            this.title = title;
            this.img = img;
            this.content = content;
            this.view_count = view_count;
            this.is_like = is_like;
            this.is_bookmark = is_bookmark;
            this.like_count = like_count;
            this.category = category;
            this.create_time = create_time;
            this.modify_time = modify_time;
        }
    }
}
