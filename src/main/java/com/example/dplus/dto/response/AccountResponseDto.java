package com.example.dplus.dto.response;

import com.example.dplus.domain.account.Account;
import com.example.dplus.domain.artwork.ArtWorks;
import com.example.dplus.domain.post.Post;
import com.example.dplus.dto.common.CommonDto.PostTagDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

public class AccountResponseDto {


    @Getter
    @NoArgsConstructor
    public static class AccountInfo {

        private Long account_id;
        private String account_name;
        private String nickname;
        private String profile_img;
        private Long exp;
        private String title_content;
        private String sub_content;
        private String tendency;
        private String linked_in;
        private String brunch;
        private String insta;
        private String work_email;
        private String work_time;
        private Long follower_count;
        private Long following_count;
        private Boolean is_follow;
        private String specialty;
        private String other_specialty;
        private Boolean is_mypage;
        private String job;

        @Builder
        public AccountInfo(final Long account_id,final String nickname,final String profile_img,final Long exp,
                                 final String tendency,final String title_content,final String sub_content,
                                 final String linked_in,final String brunch,final String insta, String work_email,
                                 final String work_time,final Long follower_count,final Long following_count,final boolean is_follow,
                                 final String specialty, final boolean is_mypage, final String other, final String job,final String account_name) {
            this.account_id = account_id;
            this.nickname = nickname;
            this.profile_img = profile_img;
            this.exp = exp;
            this.tendency = tendency;
            this.title_content = title_content;
            this.sub_content = sub_content;
            this.linked_in = linked_in;
            this.brunch = brunch;
            this.insta = insta;
            this.work_email = work_email;
            this.work_time = work_time;
            this.follower_count = follower_count;
            this.following_count = following_count;
            this.is_follow = is_follow;
            this.specialty = specialty;
            this.is_mypage = is_mypage;
            this.other_specialty = other;
            this.job = job;
            this.account_name =account_name;
        }
        public static AccountInfo from(final Account account, final Long follower, final Long following,
                                       final Boolean is_follow, final Boolean is_mypage) {
            return AccountInfo.builder()
                    .account_id(account.getId())
                    .nickname(account.getNickname())
                    .profile_img(account.getProfileImg())
                    .exp(account.getExp())
                    .tendency(account.getTendency())
                    .title_content(account.getTitleContent())
                    .sub_content(account.getSubContent())
                    .linked_in(account.getLinkedIn())
                    .brunch(account.getBrunch())
                    .insta(account.getInstagram())
                    .work_email(account.getWorkEmail())
                    .work_time(account.getWorkTime())
                    .follower_count(follower)
                    .following_count(following)
                    .is_follow(is_follow)
                    .specialty(account.getSpecialty())
                    .is_mypage(is_mypage)
                    .other(account.getOther())
                    .job(account.getJob())
                    .account_name(account.getAccountName())
                    .build();
        }
    }


    @Getter
    @NoArgsConstructor
    public static class TopArtist {
        private Long account_id;
        private String account_nickname;
        private String account_profile;
        private String account_job;
        private Boolean is_follow = false;
        private List<String> artWorks ;

        @Builder
        public TopArtist(final Account account) {
            this.account_id = account.getId();
            this.account_nickname = account.getNickname();
            this.account_profile = account.getProfileImg();
            this.account_job = account.getJob();
            this.artWorks = account.getArtWorksList().stream()
                    .map(ArtWorks::getThumbnail)
                    .limit(2)
                    .collect(Collectors.toList());

        }
        public void setIsFollow() {
            this.is_follow = true;
        }



    }

    @Getter
    @NoArgsConstructor
    public static class MyPost {
        private Long post_id;
        private String title;
        private String content;
        private Long answer_count;
        private Long like_count;
        private Timestamp create_time;
        private Timestamp modify_time;
        private String profileImg;
        private String category;
        private List<PostTagDto> hash_tag;

        @Builder
        public MyPost(final Long post_id, final String title, final String content,
                      final Long like_count, final Timestamp create_time,
                      final Timestamp modify_time, final String profileImg, final String category, final List<PostTagDto> tagList) {
            this.post_id = post_id;
            this.title = title;
            this.content = content;
            this.like_count = like_count;
            this.create_time = create_time;
            this.modify_time = modify_time;
            this.profileImg = profileImg;
            this.category = category;
            this.hash_tag = tagList;
        }

        public void setAnswer_count(Long answer_count) {
            this.answer_count = answer_count;
        }

        public static MyPost from(Post post) {
            return MyPost.builder()
                    .post_id(post.getId())
                    .title(post.getTitle())
                    .content(post.getContent())
                    .like_count((long) post.getPostLikeList().size())
                    .create_time(post.getCreated())
                    .modify_time(post.getModified())
                    .profileImg(post.getAccount().getProfileImg())
                    .category(post.getCategory())
                    .tagList(post.getPostTagList().stream()
                            .map(tag -> new PostTagDto(tag.getHashTag()))
                            .collect(Collectors.toList()))
                    .build();
        }

    }

    @Getter
    @NoArgsConstructor
    public static class MyAnswer {
        private Long post_answer_id;
        private String content;
        private Long like_count;
        private Timestamp create_time;
        private Timestamp modify_time;
        private String profileImg;
        private String post_title;
        private Boolean is_selected;
        private Long post_id;
        private Boolean post_is_selected;

        @Builder
        public MyAnswer(final Long post_answer_id, final String content, final Long like_count,
                        final Timestamp create_time, final Timestamp modify_time, final String profileImg,
                        final String post_title, final Boolean is_selected,final Long post_id,final Boolean post_is_selected) {
            this.post_answer_id = post_answer_id;
            this.content = content;
            this.like_count = like_count;
            this.create_time = create_time;
            this.modify_time = modify_time;
            this.profileImg = profileImg;
            this.post_title = post_title;
            this.is_selected = is_selected;
            this.post_id = post_id;
            this.post_is_selected = post_is_selected;
        }

    }

    @Getter
    @NoArgsConstructor
    public static class MyComment {
        private Long post_comment_id;
        private String content;
        private Long like_count;
        private Timestamp create_time;
        private Timestamp modify_time;
        private String profileImg;
        private Long post_id;
        private String post_title;

        @Builder
        public MyComment(final Long post_comment_id, final String content, final Long like_count,
                         final Timestamp create_time, final Timestamp modify_time, final String profileImg,
                         final Long post_id,final String post_title) {
            this.post_comment_id = post_comment_id;
            this.content = content;
            this.like_count = like_count;
            this.create_time = create_time;
            this.modify_time = modify_time;
            this.profileImg = profileImg;
            this.post_id = post_id;
            this.post_title =post_title;
        }
    }

}




