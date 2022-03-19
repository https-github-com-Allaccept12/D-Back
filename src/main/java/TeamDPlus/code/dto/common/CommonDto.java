package TeamDPlus.code.dto.common;

import TeamDPlus.code.domain.artwork.image.ArtWorkImage;
import lombok.Builder;
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
        private boolean thumbnail;
    }

    @Getter
    @NoArgsConstructor
    public static class ImgUrlDto {
        private String img_url;
        private boolean thumbnail;

        public ImgUrlDto(String img_url) {
            this.img_url = img_url;
        }


    }

    @Getter
    @NoArgsConstructor
    public static class PostTagDto {
        private String hashTag;

        public PostTagDto(String hashTag) {
            this.hashTag = hashTag;
        }


    }

    @Getter
    @NoArgsConstructor
    public static class IsCommentsLiked {
        private boolean is_comment_liked;

        public void setIsCommentsLiked(boolean is_comment_liked){
            this.is_comment_liked = is_comment_liked;
        }
    }

    @Getter
    @NoArgsConstructor
    public static class ArtWorkKeyword {
        private String keyword;
    }

}