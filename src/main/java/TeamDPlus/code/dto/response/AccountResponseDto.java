package TeamDPlus.code.dto.response;

import TeamDPlus.code.domain.account.Account;
import TeamDPlus.code.domain.account.history.History;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

public class AccountResponseDto {


    @Getter
    @NoArgsConstructor
    public static class AccountMyPageMain {

        private Long account_id;
        private String nickname;
        private String profile_img;
        private Long exp;
        private int career;
        private String tendency;
        private String title_content;
        private String sub_content;
        private String linked_in;
        private String brunch;
        private String insta;
        private String work_email;
        private String work_time;
        private Long follower_count;
        private Long following_count;
        private boolean is_follow;
        private List<HistoryResponseDto.History> history;
        private List<ArtWorkResponseDto.ArtWorkFeed> artwork_feed;
        private SpecialtyResponseDto.specialty specialty;
        private boolean is_mypage;

        @Builder
        public AccountMyPageMain(final Long account_id,final String nickname,final String profile_img,final Long exp,
                                 final int career,final String tendency,final String title_content,final String sub_content,
                                 final String linked_in,final String brunch,final String insta, String work_email,
                                 final String work_time,final Long follower_count,final Long following_count,final boolean is_follow,
                                 final List<HistoryResponseDto.History> history,final List<ArtWorkResponseDto.ArtWorkFeed> artwork_feed,
                                 final SpecialtyResponseDto.specialty specialty, final boolean is_mypage) {
            this.account_id = account_id;
            this.nickname = nickname;
            this.profile_img = profile_img;
            this.exp = exp;
            this.career = career;
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
            this.history = history;
            this.artwork_feed = artwork_feed;
            this.specialty = specialty;
            this.is_mypage = is_mypage;
        }
        public static AccountMyPageMain from(final Account account, final List<History> history,
                                             final List<ArtWorkResponseDto.ArtWorkFeed> artwork_feed,
                                             final Long follower, final Long following, final boolean is_follow,final boolean is_mypage) {
            return AccountMyPageMain.builder()
                    .account_id(account.getId())
                    .nickname(account.getNickname())
                    .profile_img(account.getProfileImg())
                    .exp(account.getExp())
                    .career(account.getCareer())
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
                    .history(history.stream()
                            .map(h -> HistoryResponseDto.History.builder()
                                    .company_name(h.getCompanyName())
                                    .company_department(h.getCompanyDepartment())
                                    .company_position(h.getCompanyPosition())
                                    .achievements(h.getAchievements())
                                    .work_start(h.getWorkStart())
                                    .work_end(h.getWorkEnd())
                                    .history_id(h.getId())
                                    .build())
                            .collect(Collectors.toList()))
                    .artwork_feed(artwork_feed)
                    .specialty(new SpecialtyResponseDto.specialty(account.getSpecialty()))
                    .is_mypage(is_mypage)
                    .build();
        }
    }
}




