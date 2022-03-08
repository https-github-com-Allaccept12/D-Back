package TeamDPlus.code.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

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
        private String content;
        private String linked_in;
        private String brunch;
        private String insta;
        private String work_email;
        private String work_time;
        private List<HistoryResponseDto.History> history;
        private List<CareerFeedResponseDto.CareerFeed> career_feed;
        private SpecialtyResponseDto.specialty specialty;

        @Builder
        public AccountMyPageMain(Long account_id, String nickname, String profile_img, Long exp, int career, String tendency, String content,
                                 String linked_in, String brunch, String insta, String work_email,
                                 String work_time, List<HistoryResponseDto.History> history, List<CareerFeedResponseDto.CareerFeed> career_feed,
                                 SpecialtyResponseDto.specialty specialty) {
            this.account_id = account_id;
            this.nickname = nickname;
            this.profile_img = profile_img;
            this.exp = exp;
            this.career = career;
            this.tendency = tendency;
            this.content = content;
            this.linked_in = linked_in;
            this.brunch = brunch;
            this.insta = insta;
            this.work_email = work_email;
            this.work_time = work_time;
            this.history = history;
            this.career_feed = career_feed;
            this.specialty = specialty;
        }
    }
}
