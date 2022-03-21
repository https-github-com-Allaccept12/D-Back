package TeamDPlus.code.dto.response;

import TeamDPlus.code.domain.account.Account;
import TeamDPlus.code.domain.account.Specialty;
import TeamDPlus.code.domain.account.history.History;
import TeamDPlus.code.dto.common.CommonDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

public class AccountResponseDto {


    @Getter
    @NoArgsConstructor
    public static class AccountInfo {

        private Long account_id;
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
        private boolean is_follow;
        private Specialty specialty;
        private boolean is_mypage;

        @Builder
        public AccountInfo(final Long account_id,final String nickname,final String profile_img,final Long exp,
                                 final String tendency,final String title_content,final String sub_content,
                                 final String linked_in,final String brunch,final String insta, String work_email,
                                 final String work_time,final Long follower_count,final Long following_count,final boolean is_follow,
                                 final Specialty specialty, final boolean is_mypage) {
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
        }
        public static AccountInfo from(final Account account, final Long follower, final Long following,
                                       final boolean is_follow,final boolean is_mypage) {
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
                    .build();
        }
    }


    @Getter
    @NoArgsConstructor
    public static class TopArtist {
        private Long account_id;
        private String account_nickname;
        private String account_profile;
        private String img_url_fir;
        private String img_url_sec;
        private String account_job;
        private boolean is_follow = false;

        @Builder
        public TopArtist(final Long account_id, final String account_nickname, final String account_profile,
                         final String account_job,final String img_url_fir, final  String img_url_sec) {
            this.account_id = account_id;
            this.account_nickname = account_nickname;
            this.account_profile = account_profile;
            this.account_job = account_job;
            this.img_url_fir = img_url_fir;
            this.img_url_sec = img_url_sec;
        }
        public void setIsFollow() {
            this.is_follow = true;
        }

    }


}




