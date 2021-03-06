package com.example.dplus.dto.common;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.sql.Timestamp;

public class CommonDto {


    @Getter
    @NoArgsConstructor
    public static class CommentDto {
        private Long account_id;
        private Long comment_id;
        @NotEmpty(message = "내용을 입력해주세요.")
        private String content;
        private Timestamp modify_time;

        public CommentDto(final Long account_id, final Long comment_id, final String content, final Timestamp modify_time) {
            this.account_id = account_id;
            this.comment_id = comment_id;
            this.content = content;
            this.modify_time = modify_time;
        }
    }

    @Getter
    @NoArgsConstructor
    public static class uploadImgDto {
        private String filename;

        public uploadImgDto(final String filename) {
            this.filename = filename;
        }
    }

    @Getter
    @NoArgsConstructor
    public static class ImgUrlDto {
        private String img_url;

        public ImgUrlDto(final String img_url) {
            this.img_url = img_url;
        }


    }

    @Getter
    @NoArgsConstructor
    public static class PostTagDto {
        private String tag;

        public PostTagDto(final String hashTag) {
            this.tag = hashTag;
        }

    }

    @Getter
    @NoArgsConstructor
    public static class IsCommentsLiked {
        private Long comment_id;
        private boolean is_comment_liked;

        public void setIsCommentsLiked(final Long comment_id, final boolean is_comment_liked){
            this.comment_id = comment_id;
            this.is_comment_liked = is_comment_liked;
        }
    }

    @Getter
    @NoArgsConstructor
    public static class ArtWorkKeyword {

        @NotEmpty(message = "내용을 입력해주세요.")
        private String keyword;
    }

    @Getter
    @NoArgsConstructor
    public static class Id {
        private Long id;
    }


}