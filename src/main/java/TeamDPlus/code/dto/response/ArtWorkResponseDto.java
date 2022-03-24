package TeamDPlus.code.dto.response;
import TeamDPlus.code.domain.account.Specialty;
import TeamDPlus.code.domain.artwork.ArtWorks;
import TeamDPlus.code.domain.artwork.comment.ArtWorkComment;
import TeamDPlus.code.domain.artwork.image.ArtWorkImage;
import TeamDPlus.code.dto.common.CommonDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

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
        private Boolean is_like;
        private Timestamp create_time;

        @Builder
        public ArtworkMain(final Long artwork_id,final Long account_id,final String account_nickname,final String account_profile,
                               final String img,final Long view_count,final Long like_count,final String category,final Timestamp create_time) {
            this.artwork_id = artwork_id;
            this.account_id = account_id;
            this.img = img;
            this.account_profile = account_profile;
            this.view_count = view_count;
            this.like_count = like_count;
            this.create_time = create_time;
            this.category = category;
            this.account_nickname = account_nickname;
        }
        public void setLikeCountAndIsLike(boolean is_like) {
            this.is_like = is_like;
        }
    }

    @Getter
    @NoArgsConstructor
    public static class ArtWorkDetail {

        private Boolean is_like;
        private Boolean is_bookmark;
        private Boolean is_follow ;
        private ArtWorkSubDetail artWorkSubDetail;
        private List<CommonDto.ImgUrlDto> img;
        private List<ArtWorkComment> comment;
        private List<ArtWorkSimilarWork> similar_Work;

        @Builder
        public ArtWorkDetail(final List<CommonDto.ImgUrlDto> img,
                             final List<ArtWorkComment> comment,
                             final List<ArtWorkSimilarWork> similar_Work,
                             final ArtWorkSubDetail artWorkSubDetail,
                             final boolean is_like, final boolean is_bookmark,final boolean is_follow) {
            this.is_like = is_like;
            this.is_bookmark = is_bookmark;
            this.is_follow = is_follow;
            this.img = img;
            this.comment = comment;
            this.similar_Work = similar_Work;
            this.artWorkSubDetail = artWorkSubDetail;
        }

        public static ArtWorkDetail from(final List<ArtWorkImage> imgList, final List<ArtWorkComment> commentList,
                                         final List<ArtWorkSimilarWork> similarList, ArtWorkSubDetail artWorkSubDetail,
                                         final boolean is_like, final boolean is_bookmark, final boolean is_follow) {
            return ArtWorkDetail.builder()
                    .img(imgList.stream()
                            .map(i -> new CommonDto.ImgUrlDto(i.getArtworkImg())).collect(Collectors.toList()))
                    .comment(commentList)
                    .similar_Work(similarList)
                    .artWorkSubDetail(artWorkSubDetail)
                    .is_like(is_like)
                    .is_bookmark(is_bookmark)
                    .is_follow(is_follow)
                    .build();
        }
    }

    @Getter
    @NoArgsConstructor
    public static class ArtWorkSubDetail {

        private Long artwork_id;
        private Long account_id;
        private String account_nickname;
        private String account_profile_img;
        private String title;
        private String content;
        private Long view_count;
        private Long comment_count;
        private Long like_count;
        private String category;
        private Timestamp create_time;
        private Timestamp modify_time;
        private String copyright;
        private Specialty specialty;

        @Builder
        public ArtWorkSubDetail(final Long artwork_id,final Long account_id,final String title,final String content,final Long view_count,
                                final Long like_count,final String category,final Timestamp create_time,
                                final Timestamp modify_time,final Specialty specialty,final String account_nickname,
                                final String account_profile_img,final String copyright) {
            this.artwork_id = artwork_id;
            this.account_id = account_id;
            this.title = title;
            this.content = content;
            this.view_count = view_count;
            this.like_count = like_count;
            this.category = category;
            this.create_time = create_time;
            this.modify_time = modify_time;
            this.specialty = specialty;
            this.account_nickname = account_nickname;
            this.account_profile_img = account_profile_img;
            this.copyright = copyright;
        }
        public void setComment_count(Long comment_count) {
            this.comment_count =comment_count;
        }


    }

    @Getter
    @NoArgsConstructor
    public static class ArtWorkFeed {

        private Long artwork_id;
        private boolean scope;
        private String img;
        private boolean is_master;

        @Builder
        public ArtWorkFeed(final Long artwork_id,final boolean scope,final String img, final boolean is_master) {
            this.artwork_id = artwork_id;
            this.scope = scope;
            this.img = img;
            this.is_master = is_master;
        }
    }



    @Getter
    @NoArgsConstructor
    public static class ArtWorkSimilarWork {
        private Long artwork_id;
        private String artwork_title;
        private String account_nickname;
        private String acccount_tendency;
        private String linkedIn;
        private String brunch;
        private String instagram;

        private String img;

        public ArtWorkSimilarWork(final Long artwork_id,final String artwork_title,final String img) {
            this.artwork_id = artwork_id;
            this.artwork_title = artwork_title;
            this.img = img;
        }
    }


    @Getter
    @NoArgsConstructor
    public static class ArtWorkBookMark {
        private Long artwork_id;
        private String account_nickname;
        private String img;
        private Long view;
        private Long like_count;

        @Builder
        public ArtWorkBookMark(final Long artwork_id,final String account_nickname,final String img,final Long view,final Long like_count) {
            this.artwork_id = artwork_id;
            this.account_nickname = account_nickname;
            this.img = img;
            this.view = view;
            this.like_count = like_count;
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
