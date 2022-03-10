package TeamDPlus.code.dto.response;

import TeamDPlus.code.domain.account.Account;
import TeamDPlus.code.domain.artwork.ArtWorks;
import TeamDPlus.code.domain.artwork.image.ArtWorkImage;
import TeamDPlus.code.domain.artwork.like.ArtWorkLikes;
import TeamDPlus.code.dto.ArtWorkCommentDto;
import TeamDPlus.code.dto.ImageUrlDto;
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
        private String img;
        private Long view_count;
        private Long like_count;
        private String category;
        private Timestamp create_time;
        private Timestamp modify_time;

        @Builder
        public ArtworkPageMain(Long artwork_id, Long account_id, String img,
                               Long view_count, Long like_count,
                               String category, Timestamp create_time, Timestamp modify_time) {
            this.artwork_id = artwork_id;
            this.account_id = account_id;
            this.img = img;
            this.view_count = view_count;
            this.like_count = like_count;
            this.category = category;
            this.create_time = create_time;
            this.modify_time = modify_time;
        }

        public static ArtworkPageMain from(final ArtWorks artwork, final Account account,
                                           final ArtWorkImage artWorkImage,
                                           final ArtWorkLikes artWorkLikes,
                                           final Long like_count){
            return ArtworkPageMain.builder()
                    .artwork_id(artwork.getId())
                    .account_id(account.getId())
                    .img(artWorkImage.getArtworkImg())
                    .view_count(artwork.getView())
                    .like_count(like_count)
                    .category(artwork.getCategory())
                    .create_time(artwork.getCreated())
                    .modify_time(artwork.getModified())
            .build();
        }
    }

    @Getter
    @NoArgsConstructor
    public static class ArtWorkDetailPage {

        private Long artwork_id;
        private Long account_id;
        private String scope;
        private String title;
        private List<ImageUrlDto> img;
        private String content;
        private Long view_count;
        private Boolean is_like;
        private Boolean is_bookmark;
        private Long like_count;
        private String category;
        private List<ArtWorkCommentDto> comment;
        private Timestamp create_time;
        private Timestamp modify_time;

        @Builder
        public ArtWorkDetailPage(Long artwork_id, Long account_id, String scope, String title,
                                 List<ImageUrlDto> img, String content, Long view_count, Boolean is_like,
                                 Boolean is_bookmark, Long like_count, String category,
                                 List<ArtWorkCommentDto> comment, Timestamp create_time,
                                 Timestamp modify_time) {
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
        private String title;
        private String img;
        private Long view_count;
        private Timestamp create_time;
        private Timestamp modify_time;

        @Builder
        public ArtWorkFeed(Long artwork_id, String scope, String title, String img, Long view_count, Timestamp create_time, Timestamp modify_time) {
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
