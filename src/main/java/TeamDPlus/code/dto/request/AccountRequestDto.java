package TeamDPlus.code.dto.request;

import TeamDPlus.code.domain.account.Specialty;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class AccountRequestDto {

    @Getter
    @NoArgsConstructor
    public static class ProfileUpdate {
        private String nickname;

        private String title_content;

        private String sub_content ;

        private String work_email;

        private String work_time;

        private String linked_in;

        private String brunch;

        private String insta;

        private String web_page;

        private int career;

        private String phone_number;
    }

    @Getter
    @NoArgsConstructor
    public static class SpecialtyUpdate {

        private Specialty specialty;
    }

    @Getter
    @NoArgsConstructor
    public static class Follow {

        private Long follower_id;
    }



}
