package TeamDPlus.code.dto.response;
import TeamDPlus.code.domain.account.QSpecialty;
import TeamDPlus.code.domain.account.Specialty;
import TeamDPlus.code.domain.artwork.ArtWorks;
import TeamDPlus.code.domain.artwork.comment.ArtWorkComment;
import TeamDPlus.code.domain.artwork.image.ArtWorkImage;
import TeamDPlus.code.dto.common.CommonDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.sql.Timestamp;
import java.util.stream.Collectors;

public class ArtWorkResponseDto {

    @Getter
    @NoArgsConstructor
    public static class ArtworkMain {

        private Long artwork_id;
        private Long account_id;
        private String account_nickname;
        private String account_profile;
        private String category;
        private String img;
        private Long view_count;
        private Long like_count;
        private boolean is_like;
        private Timestamp create_time;

        @Builder
        public ArtworkMain(final Long artwork_id,final Long account_id,final String account_nickname,final String account_profile,
                               final String img,final Long view_count,final String category,final Timestamp create_time) {
            this.artwork_id = artwork_id;
            this.account_id = account_id;
            this.img = img;
            this.account_profile = account_profile;
            this.view_count = view_count;
            this.create_time = create_time;
            this.category = category;
            this.account_nickname = account_nickname;
        }
        public void setLikeCountAndIsLike(Long likeCount,boolean is_like) {
            this.is_like = is_like;
            this.like_count = likeCount;
        }
    }

    @Getter
    @NoArgsConstructor
    public static class ArtWorkDetail {

        private Long artwork_id;
        private Long account_id;
        private boolean scope;
        private String title;
        private List<CommonDto.ImgUrlDto> img;
        private String content;
        private Long view_count;
        private Boolean is_like;
        private Boolean is_bookmark;
        private Long like_count;
        private String category;
        private List<ArtWorkComment> comment;
        private boolean is_follow;
        private Timestamp create_time;
        private Timestamp modify_time;
        private Specialty specialty;

        @Builder
        public ArtWorkDetail(final Long artwork_id,final Long account_id,final boolean scope,final String title,
                                 final List<CommonDto.ImgUrlDto> img,final String content,final Long view_count,final Boolean is_like,
                                 final Boolean is_bookmark,final Long like_count,final String category,
                                 final List<ArtWorkComment> comment,final Timestamp create_time, final Timestamp modify_time,
                             boolean is_follow,final Specialty specialty) {
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
            this.is_follow = is_follow;
            this.create_time = create_time;
            this.modify_time = modify_time;
            this.specialty = specialty;
        }

        public static ArtWorkDetail from(final List<ArtWorkImage> imgList, final List<ArtWorkComment> commentList,
                                         final ArtWorks artWorks,final boolean is_like, final boolean is_bookmark,
                                         final Long like_count,final boolean is_follow) {
            return ArtWorkDetail.builder()
                    .artwork_id(artWorks.getId())
                    .account_id(artWorks.getAccount().getId())
                    .scope(artWorks.isScope())
                    .title(artWorks.getTitle())
                    .img(imgList.stream()
                            .map(i -> new CommonDto.ImgUrlDto(i.getArtworkImg())).collect(Collectors.toList()))
                    .content(artWorks.getContent())
                    .view_count(artWorks.getView())
                    .category(artWorks.getCategory())
                    .comment(commentList)
                    .is_like(is_like)
                    .is_bookmark(is_bookmark)
                    .is_follow(is_follow)
                    .like_count(like_count)
                    .specialty(artWorks.getSpecialty())
                    .create_time(artWorks.getCreated())
                    .modify_time(artWorks.getModified())
                    .build();

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

    @Getter
    @NoArgsConstructor
    public static class ArtWorkComment {
        private Long account_id;
        private Long comment_id;
        private String content;
        private Timestamp modify_time;

        @Builder
        public ArtWorkComment(final Long account_id, final Long comment_id, final String content, final Timestamp modify_time) {
            this.account_id = account_id;
            this.comment_id = comment_id;
            this.content = content;
            this.modify_time = modify_time;
        }
    }



}
