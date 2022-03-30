package com.example.dplus.dto.response;


import com.example.dplus.domain.post.Post;
import com.example.dplus.domain.post.PostImage;
import com.example.dplus.domain.post.PostTag;
import com.example.dplus.dto.common.CommonDto.ImgUrlDto;
import com.example.dplus.dto.common.CommonDto.PostTagDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
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
        private Long like_count;
        private Long comment_count;
        private String category;
        private Timestamp create_time;
        private Boolean is_selected;
        private List<PostTagDto> hash_tag;

        @Builder
        public PostPageMain(final Long post_id, final Long account_id, final String account_nickname,
                            final String account_profile_img, final String title, final String content,
                            final String category, final Timestamp create_time, final Boolean is_selected,
                            final Long like_count,final List<PostTag> postTagList,final Long comment_count){

            this.post_id = post_id;
            this.account_id = account_id;
            this.account_nickname = account_nickname;
            this.account_profile_img = account_profile_img;
            this.title = title;
            this.content = content;
            this.category = category;
            this.create_time = create_time;
            this.is_selected = is_selected;
            this.like_count = like_count;
            this.hash_tag = postTagList.stream()
                    .map(p -> new PostTagDto(p.getHashTag()))
                    .collect(Collectors.toList());
            this.comment_count = comment_count;
        }

    }


   // 디모 - 꿀팁
    @Getter
    @NoArgsConstructor
    public static class PostDetailPage {

        private Boolean is_like;
        private Boolean is_bookmark;
        private Boolean is_follow;
        private Long comment_count;
        private PostSubDetail postSubDetail;
        private List<ImgUrlDto> img;
        private List<PostTagDto> hash_tag;
        private List<PostResponseDto.PostComment> comment;
        private Long bookmark_count;

        @Builder
        public PostDetailPage(final boolean is_like, final Boolean is_bookmark, final Boolean is_follow,
                              final PostSubDetail postSubDetail, final List<ImgUrlDto> img,
                              final List<PostTagDto> hash_tag, final List<PostComment> comment, final Long comment_count,
                              final Long bookmark_count) {
            this.is_like = is_like;
            this.is_bookmark = is_bookmark;
            this.is_follow = is_follow;
            this.postSubDetail = postSubDetail;
            this.img = img;
            this.hash_tag = hash_tag;
            this.comment = comment;
            this.comment_count = comment_count;
            this.bookmark_count = bookmark_count;
        }
        public static PostDetailPage of(final List<PostImage> postImageList, final List<PostComment> commentList,
                                          final Post postDetail, final Boolean is_like, final Boolean is_bookmark,
                                          final Boolean is_follow, final Long comment_count, final Long bookmark_count){

            return PostDetailPage.builder()
                    .postSubDetail(new PostSubDetail(postDetail.getId(),
                            postDetail.getAccount().getId(),
                            postDetail.getAccount().getNickname(),
                            postDetail.getAccount().getProfileImg(),
                            postDetail.getTitle(),
                            postDetail.getContent(),
                            postDetail.getView(),
                            postDetail.getCategory(),
                            postDetail.getCreated(),
                            postDetail.getModified(),
                            (long) postDetail.getPostLikeList().size()))
                    .img(postImageList.stream()
                            .map(i -> new ImgUrlDto(i.getPostImg())).collect(Collectors.toList()))
                    .comment(commentList)
                    .is_like(is_like)
                    .is_bookmark(is_bookmark)
                    .is_follow(is_follow)
                    .comment_count(comment_count)
                    .hash_tag(postDetail.getPostTagList().stream()
                            .map(tag -> new PostTagDto(tag.getHashTag()))
                            .collect(Collectors.toList()))
                    .bookmark_count(bookmark_count)
                    .build();
        }
    }

    @Getter
    @NoArgsConstructor
    public static class PostSubDetail {
        private Long post_id;
        private Long account_id;
        private String account_nickname;
        private String account_profile_img;
        private String title;
        private String content;
        private Long view_count;
        private String category;
        private Timestamp create_time;
        private Timestamp modify_time;
        private Long like_count;

        @Builder
        public PostSubDetail(final Long post_id, final Long account_id, final String account_nickname,
                             final String account_profile_img, final String title,
                             final String content, final Long view_count, final String category,
                             final Timestamp create_time, final Timestamp modify_time, final Long like_count) {
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
            this.like_count = like_count;
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
        private String nickname;
        private String profile_img;
        private Long comment_id;
        private String content;
        private Timestamp modify_time;
        private Long like_count;
        private Boolean is_comment_like;

        @Builder
        public PostComment(final Long account_id, final String nickname,
                           final String profile_img, final Long comment_id,
                           final String content, final Timestamp modify_time, final Long like_count) {
            this.account_id = account_id;
            this.nickname = nickname;
            this.profile_img = profile_img;
            this.comment_id = comment_id;
            this.content = content;
            this.modify_time = modify_time;
            this.like_count = like_count;
        }
        public void setIs_comment_like(Boolean is_comment_like){
            this.is_comment_like = is_comment_like;
        }
    }






    @Getter
    @NoArgsConstructor
    public static class PostAnswerDetailPage {

        private Boolean is_like;
        private Boolean is_bookmark;
        private Boolean is_follow;
        private PostAnswerSubDetail postAnswerSubDetail;
        private List<ImgUrlDto> img;
        private List<PostTagDto> hash_tag;
        private List<PostResponseDto.PostAnswer> answers;
        private Long bookMark_count;
        private Long answer_count;

        @Builder
        public PostAnswerDetailPage(Boolean is_like, Boolean is_bookmark, Boolean is_follow, PostAnswerSubDetail postAnswerSubDetail,
                                    List<ImgUrlDto> img, List<PostTagDto> hash_tag,
                                    List<PostAnswer> answers, Long bookMark_count, Long answer_count) {
            this.is_like = is_like;
            this.is_bookmark = is_bookmark;
            this.is_follow = is_follow;
            this.postAnswerSubDetail = postAnswerSubDetail;
            this.img = img;
            this.hash_tag = hash_tag;
            this.answers = answers;
            this.bookMark_count = bookMark_count;
            this.answer_count = answer_count;

        }
        public static PostAnswerDetailPage from(final List<PostImage> postImageList, final List<PostAnswer> answerList,
                                                final Post post,final Boolean is_like,final Boolean is_bookmark,
                                                final Boolean is_follow, final Long bookMark_count){
            return PostAnswerDetailPage.builder()
                    .postAnswerSubDetail(PostAnswerSubDetail.from(post))
                    .img(postImageList.stream()
                            .map(i -> new ImgUrlDto(i.getPostImg())).collect(Collectors.toList()))
                    .answers(answerList)
                    .answer_count((long) answerList.size())
                    .is_like(is_like)
                    .is_bookmark(is_bookmark)
                    .is_follow(is_follow)
                    .hash_tag(post.getPostTagList().stream()
                            .map(t -> new PostTagDto(t.getHashTag()))
                            .collect(Collectors.toList()))
                    .bookMark_count(bookMark_count)
                    .build();
        }

    }

    @Getter
    @NoArgsConstructor
    public static class PostAnswerSubDetail {
        private Long post_id;
        private Long account_id;
        private String account_nickname;
        private String account_profile_img;
        private String title;
        private String content;
        private Long view_count;
        private Long answer_count;
        private Long like_count;
        private String category;
        private Timestamp create_time;
        private Timestamp modify_time;
        private Boolean is_selected;

        @Builder
        private PostAnswerSubDetail(final Long post_id, final Long account_id, final String account_nickname,
                             final String account_profile_img, final String title, final String content,
                             final Long view_count, final Long like_count, final String category,
                             final Timestamp create_time, final Timestamp modify_time, final Boolean is_selected) {
            this.post_id = post_id;
            this.account_id = account_id;
            this.account_nickname = account_nickname;
            this.account_profile_img = account_profile_img;
            this.title = title;
            this.content = content;
            this.view_count = view_count;
            this.like_count = like_count;
            this.category = category;
            this.create_time = create_time;
            this.modify_time = modify_time;
            this.is_selected = is_selected;
        }
        public static PostAnswerSubDetail from(Post post) {
            return PostAnswerSubDetail.builder()
                    .account_id(post.getAccount().getId())
                    .post_id(post.getId())
                    .account_nickname(post.getAccount().getNickname())
                    .account_profile_img(post.getAccount().getProfileImg())
                    .title(post.getTitle())
                    .content(post.getContent())
                    .view_count(post.getView())
                    .like_count((long) post.getPostLikeList().size())
                    .category(post.getCategory())
                    .create_time(post.getCreated())
                    .modify_time(post.getModified())
                    .is_selected(post.isSelected())
                    .build();

        }
    }

    @Getter
    @NoArgsConstructor
    public static class PostAnswer {
        private Long answer_id;
        private Long account_id;
        private String account_nickname;
        private String account_profile_img;
        private String content;
        private Timestamp modify_time;
        private Boolean is_selected;
        private Long like_count;
        private Boolean is_like = false;
        private Boolean is_follow = false;

        @Builder
        public PostAnswer(final Long answer_id, final Long account_id, final String account_nickname, final String account_profile_img,
                          final String content, final Timestamp modify_time, final Boolean is_selected, final Long like_count) {
            this.answer_id = answer_id;
            this.account_id = account_id;
            this.account_nickname = account_nickname;
            this.account_profile_img = account_profile_img;
            this.content = content;
            this.modify_time = modify_time;
            this.is_selected = is_selected;
            this.like_count = like_count;
        }

        public void setLikeCountAndIsLike(Boolean is_like) {
            this.is_like = is_like;
        }

        public void setIsFollow(Boolean is_follow) {
            this.is_follow = is_follow;
        }
    }

    @Getter
    @NoArgsConstructor
    public static class PostSimilarQuestion {
        private Long post_id;
        private Long account_id;
        private String account_profile_img;
        private String title;
        private String content;
        private Long answer_count;
        private Long like_count;
        private String category;
        private Timestamp create_time;
        private Timestamp modify_time;
        private List<PostTagDto> hash_tag;

        @Builder
        private PostSimilarQuestion(final Long post_id, final Long account_id, final String account_profile_img,
                                   final String title, final String content, final Long like_count, final String category,
                                   final Timestamp create_time, final Timestamp modify_time,final List<PostTagDto> hash_tag) {
            this.post_id = post_id;
            this.account_id = account_id;
            this.account_profile_img = account_profile_img;
            this.title = title;
            this.content = content;
            this.like_count = like_count;
            this.category = category;
            this.create_time = create_time;
            this.modify_time = modify_time;
            this.hash_tag = hash_tag;
        }
        public void setAnswer_count(Long answer_count) {
            this.answer_count = answer_count;
        }

        public static PostSimilarQuestion of(Post post) {
            return PostSimilarQuestion.builder()
                    .post_id(post.getId())
                    .account_id(post.getAccount().getId())
                    .account_profile_img(post.getAccount().getProfileImg())
                    .title(post.getTitle())
                    .content(post.getContent())
                    .like_count((long) post.getPostLikeList().size())
                    .category(post.getCategory())
                    .create_time(post.getCreated())
                    .modify_time(post.getModified())
                    .hash_tag(post.getPostTagList().stream()
                            .map(t -> new PostTagDto(t.getHashTag()))
                            .collect(Collectors.toList()))
                    .build();
        }





    }

}
