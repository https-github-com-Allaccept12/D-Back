package TeamDPlus.code.dto.response;

import TeamDPlus.code.dto.common.CommonDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

public class PostResponseDto {

    @Getter
    @NoArgsConstructor
    public static class PostPageMain {

        private Long post_id;
        private Long account_id;

        private String account_nickname;
        private String account_profile;
        private String title;
        private String content;
        private Long bookmark_count;
        private Long like_count;
        private Long comment_count;
        private String category;
        private Timestamp create_time;

        // 태그 탭 상의할 것
        @Builder
        public PostPageMain(final Long post_id, final Long account_id, final String account_nickname, final String account_profile,
                            final String title, final String content, final String category, final Timestamp create_time
        // final Long bookmark_count, final Long comment_count, final Long like_count,
        ) {
            this.post_id = post_id;
            this.account_id = account_id;
            this.account_nickname = account_nickname;
            this.account_profile = account_profile;
            this.title = title;
            this.content = content;
//            this.bookmark_count = bookmark_count;
//            this.like_count = like_count;
//            this.comment_count = comment_count;
            this.category = category;
            this.create_time = create_time;
        }
    }

    @Getter
    @NoArgsConstructor
    public static class PostDetailPage {

        private Long post_id;
        private Long account_id;
        private String title;
        private List<CommonDto.ImgUrlDto> img;
        private String content;
        private Long view_count;
        private Boolean is_like;
        private Boolean is_bookmark;
        private Long like_count;
        private String category;
        private List<CommonDto.CommentDto> comment;
        private Timestamp create_time;
        private Timestamp modify_time;

        @Builder

        public PostDetailPage(final Long post_id,final Long account_id,final String title,
                              final List<CommonDto.ImgUrlDto> img,final String content,final Long view_count,
                              final Boolean is_like,final Boolean is_bookmark,final Long like_count,
                              final String category,final List<CommonDto.CommentDto> comment,
                              final Timestamp create_time,final Timestamp modify_time) {
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
        private Timestamp create_time;
        private Timestamp modify_time;

        @Builder
        public PostBookmarkPage(final Long post_id,final Long account_id,final String title,final String img,final String content,
                                final Long view_count,final Boolean is_like,final Boolean is_bookmark,final Long like_count,
                                final String category,final Timestamp create_time,final Timestamp modify_time) {
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
