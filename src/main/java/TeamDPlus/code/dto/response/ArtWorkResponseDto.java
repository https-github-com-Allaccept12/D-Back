package TeamDPlus.code.dto.response;

import TeamDPlus.code.domain.artwork.ArtWorks;
import TeamDPlus.code.dto.common.CommonDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.sql.Timestamp;

public class ArtWorkResponseDto {

    @Getter
    @NoArgsConstructor
    public static class ArtworkPageMain {

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

        @Builder
        public ArtworkPageMain(final Long artwork_id,final Long account_id,final String scope,final String title,final String img,
                               final String content,final Long view_count,final Boolean is_like,final Boolean is_bookmark,final Long like_count,
                               final String category,final Timestamp create_time,final Timestamp modify_time) {
            this.artwork_id = artwork_id;
            this.account_id = account_id;
            this.scope = scope;
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
    public static class ArtWorkDetailPage {

        private Long artwork_id;
        private Long account_id;
        private String scope;
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
        public ArtWorkDetailPage(final Long artwork_id,final Long account_id,final String scope,final String title,
                                 final List<CommonDto.ImgUrlDto> img,final String content,final Long view_count,final Boolean is_like,
                                 final Boolean is_bookmark,final Long like_count,final String category,
                                 final List<CommonDto.CommentDto> comment,final Timestamp create_time,
                                 final Timestamp modify_time) {
            this.artwork_id = artwork_id;
            this.account_id = account_id;
            this.scope = scope;
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
    public static class ArtWorkFeed {

        private Long artwork_id;
        private String scope;
        private String img;
        private Long view_count;
        private boolean is_master;
        private Timestamp create_time;
        private Timestamp modify_time;

        @Builder
        public ArtWorkFeed(final Long artwork_id,final String scope,final String img,final Long view_count,
                           final boolean is_master,final Timestamp create_time,final Timestamp modify_time) {
            this.artwork_id = artwork_id;
            this.scope = scope;
            this.img = img;
            this.view_count = view_count;
            this.is_master = is_master;
            this.create_time = create_time;
            this.modify_time = modify_time;
        }
    }

    @Getter
    @NoArgsConstructor
    public static class ArtWorkBookMark {
        private Long artwork_id;
        private String account_nickname;
        private String img;
        private Long view;

        @Builder
        public ArtWorkBookMark(final Long artwork_id,final String account_nickname,final String img,final Long view) {
            this.artwork_id = artwork_id;
            this.account_nickname = account_nickname;
            this.img = img;
            this.view = view;
        }
    }


}
