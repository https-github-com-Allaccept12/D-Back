package TeamDPlus.code.dto.response;

import TeamDPlus.code.domain.post.Post;
import TeamDPlus.code.domain.post.answer.PostAnswerComment;
import TeamDPlus.code.domain.post.comment.PostComment;
import TeamDPlus.code.domain.post.image.PostImage;
import TeamDPlus.code.domain.post.tag.PostTag;
import TeamDPlus.code.dto.common.CommonDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
                            final String title, final String content, final String category, final Timestamp create_time) {
            this.post_id = post_id;
            this.account_id = account_id;
            this.account_nickname = account_nickname;
            this.account_profile = account_profile;
            this.title = title;
            this.content = content;
            this.category = category;
            this.create_time = create_time;
        }
        public void setCountList(Long bookmark_count, Long like_count, Long comment_count){
            this.bookmark_count = bookmark_count;
            this.like_count = like_count;
            this.comment_count = comment_count;
        }
    }

    @Getter
    @NoArgsConstructor
    public static class PostDetailPage {

        private Long post_id;
        private Long account_id;
        private Long account_nickname;
        private String account_profile;
        private String title;
        private List<CommonDto.ImgUrlDto> img;
        private List<CommonDto.PostTagDto> hashTag;
        private String content;
        private Long view_count;
        private Boolean is_like;
        private Boolean is_bookmark;
        private Long like_count;
        private String category;
        private List<PostResponseDto.PostComment> comment;
        private Timestamp create_time;
        private Timestamp modify_time;

        @Builder
        public PostDetailPage(final Long post_id,final Long account_id, final Long account_nickname, final String title,
                              final List<CommonDto.ImgUrlDto> img,final String content,final Long view_count,
                              final Boolean is_like,final Boolean is_bookmark,final Long like_count,
                              final String category,final List<PostResponseDto.PostComment> comment,
                              final Timestamp create_time,final Timestamp modify_time, final String account_profile,
                              final List<CommonDto.PostTagDto> hashTag) {
            this.post_id = post_id;
            this.account_id = account_id;
            this.account_nickname = account_nickname;
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
            this.account_profile = account_profile;
            this.hashTag = hashTag;
        }

        public static PostDetailPage from(final List<PostImage> postImageList, final List<PostComment> commentList,
                                          final List<PostTag> postTagsList,
                                          final Post post, final boolean is_like, final boolean is_bookmark, Long like_count
                                          ){
            return PostDetailPage.builder()
                    .post_id(post.getId())
                    .account_id(post.getAccount().getId())
                    .title(post.getTitle())
                    .img(postImageList.stream()
                            .map(i -> new CommonDto.ImgUrlDto(i.getPostImg())).collect(Collectors.toList()))
                    .comment(commentList)
                    .content(post.getContent())
                    .view_count(post.getView())
                    .is_like(is_like)
                    .is_bookmark(is_bookmark)
                    .like_count(like_count)
                    .category(post.getCategory())
                    .create_time(post.getCreated())
                    .modify_time(post.getModified())
                    .hashTag(postTagsList.stream()
                            .map(i -> new CommonDto.PostTagDto(i.getHashTag())).collect(Collectors.toList()))
                    .build();
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
        private int is_selected;
        private Long like_count;

        @Builder
        public PostComment(final Long account_id, final Long comment_id,
                           final String content, final Timestamp modify_time, final int is_selected, final Long like_count) {
            this.account_id = account_id;
            this.comment_id = comment_id;
            this.content = content;
            this.modify_time = modify_time;
            this.is_selected = is_selected;
            this.like_count = like_count;
        }
    }

    @Getter
    @NoArgsConstructor
    public static class PostAnswer {
        private Long account_id;
        private Long answer_id;
        private String content;
        private Timestamp modify_time;
        private int is_selected;
        private Long like_count;
        private Long view_count;
        private List<PostResponseDto.PostAnswerComment> answerComment;

        @Builder
        public PostAnswer(final Long account_id, final Long answer_id, final String content, final Timestamp modify_time,
                          final int is_selected, final Long like_count, final Long view_count,
                          final List<PostResponseDto.PostAnswerComment> answerComment) {
            this.account_id = account_id;
            this.answer_id = answer_id;
            this.content = content;
            this.modify_time = modify_time;
            this.is_selected = is_selected;
            this.like_count = like_count;
            this.view_count = view_count;
            this.answerComment = answerComment;
        }
    }

    @Getter
    @NoArgsConstructor
    public static class PostAnswerComment {
        private Long account_id;
        private Long answer_comment_id;
        private String content;

        @Builder
        public PostAnswerComment(final Long account_id, final Long answer_comment_id, final String content) {
            this.account_id = account_id;
            this.answer_comment_id = answer_comment_id;
            this.content = content;
        }
    }
}
