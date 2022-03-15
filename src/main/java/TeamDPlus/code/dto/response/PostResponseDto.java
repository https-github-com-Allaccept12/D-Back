package TeamDPlus.code.dto.response;

import TeamDPlus.code.domain.post.Post;
import TeamDPlus.code.domain.post.comment.PostComment;
import TeamDPlus.code.domain.post.image.PostImage;
import TeamDPlus.code.domain.post.tag.PostTag;
import TeamDPlus.code.dto.common.CommonDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class PostResponseDto {


    @Getter
    @NoArgsConstructor
    public static class PostPageMain {

        private Long post_id;
        private Long account_id;
        private String account_nickname;
        private String account_profile_img;
        private String title;
        private String content;
        private Long bookmark_count;
        private Long like_count;
        private Long comment_count;
        private String category;
        private Timestamp create_time;
        private boolean is_selected;
        private List<CommonDto.PostTagDto> hash_tag;

        // 태그 탭 상의할 것
        @Builder
        public PostPageMain(final Long post_id, final Long account_id, final String account_nickname,
                            final String account_profile_img, final String title,
                            final String content, final String category,
                            final Timestamp create_time, final List<CommonDto.PostTagDto> hash_tag, final boolean is_selected) {

            this.post_id = post_id;
            this.account_id = account_id;
            this.account_nickname = account_nickname;
            this.account_profile_img = account_profile_img;
            this.title = title;
            this.content = content;
            this.category = category;
            this.create_time = create_time;
            this.hash_tag = hash_tag;
            this.is_selected = is_selected;
        }
        public void setCountList(Long bookmark_count, Long like_count, Long comment_count){
            this.bookmark_count = bookmark_count;
            this.like_count = like_count;
            this.comment_count = comment_count;
        }
    }


    // 디모 - 꿀팁
    @Getter
    @NoArgsConstructor
    public static class PostDetailPage {


        private boolean is_like;
        private boolean is_bookmark;
        private boolean is_follow;
        private Long bookmark_count;
        private Long like_count;
        private Long comment_count;
        private PostSubDetail postSubDetail;
        private List<CommonDto.ImgUrlDto> img;
        private List<CommonDto.PostTagDto> hash_tag;
        private List<PostResponseDto.PostComment> comment;

        @Builder
        public PostDetailPage(boolean is_like, boolean is_bookmark, boolean is_follow, PostSubDetail postSubDetail,
                              List<CommonDto.ImgUrlDto> img, List<CommonDto.PostTagDto> hash_tag,
                              List<PostComment> comment) {
            this.is_like = is_like;
            this.is_bookmark = is_bookmark;
            this.is_follow = is_follow;
            this.postSubDetail = postSubDetail;
            this.img = img;
            this.hash_tag = hash_tag;
            this.comment = comment;
        }

        public void setCountList(Long bookmark_count, Long like_count, Long comment_count){
            this.bookmark_count = bookmark_count;
            this.like_count = like_count;
            this.comment_count = comment_count;
        }

        public static PostDetailPage from(final List<PostImage> postImageList, final List<PostComment> commentList,
                                          final List<PostTag> postTagsList, final PostSubDetail postSubDetail,
                                          final boolean is_like, final boolean is_bookmark, final boolean is_follow,
                                          final Long like_count, final Long bookmark_count, final Long comment_count
                                          ){
            return PostDetailPage.builder()
                    .postSubDetail(postSubDetail)
                    .img(postImageList.stream()
                            .map(i -> new CommonDto.ImgUrlDto(i.getPostImg())).collect(Collectors.toList()))
                    .comment(commentList)
                    .is_like(is_like)
                    .is_bookmark(is_bookmark)
                    .is_follow(is_follow)
//                    .like_count(like_count)
//                    .bookmark_count(bookmark_count)
//                    .comment_count(comment_count)
                    .hash_tag(postTagsList.stream()
                            .map(i -> new CommonDto.PostTagDto(i.getHashTag())).collect(Collectors.toList()))
                    .build();
        }

    }

    @Getter
    @NoArgsConstructor
    public static class PostSubDetail {
        private Long post_id;
        private Long account_id;
        private Long account_nickname;
        private String account_profile_img;
        private String title;
        private String content;
        private Long view_count;
        private String category;
        private Timestamp create_time;
        private Timestamp modify_time;

        @Builder
        public PostSubDetail(Long post_id, Long account_id, Long account_nickname,
                             String account_profile_img, String title,
                             String content, Long view_count, String category,
                             Timestamp create_time, Timestamp modify_time) {
            this.post_id = post_id;
            this.account_id = account_id;
            this.account_nickname = account_nickname;
            this.account_profile_img = account_profile_img;
            this.title = title;
            this.content = content;
            this.view_count = view_count;
            this.category = category;
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

    @Getter
    @NoArgsConstructor
    public static class PostComment{
        private Long account_id;
        private Long comment_id;
        private String content;
        private Timestamp modify_time;
        private Long like_count;

        @Builder
        public PostComment(final Long account_id, final Long comment_id,
                           final String content, final Timestamp modify_time, final Long like_count) {
            this.account_id = account_id;
            this.comment_id = comment_id;
            this.content = content;
            this.modify_time = modify_time;
            this.like_count = like_count;
        }
    }
}
