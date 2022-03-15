package TeamDPlus.code.dto.request;

import TeamDPlus.code.domain.account.Specialty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class AccountRequestDto {


    @Getter
    @NoArgsConstructor
    public static class AccountIsMyPage {
        private Long account_id;
    }

    @Getter
    @NoArgsConstructor
    public static class UpdateAccountInfo {
        private String nickname;
        private String work_email;
        private String work_time;
        private String linked_in;
        private String brunch;
        private String insta;
        private String phone_number;
        private String job;

    }

    @Getter
    @NoArgsConstructor
    public static class UpdateAccountIntro {
        private String title_content;
        private String sub_content;
    }

    @Getter
    @NoArgsConstructor
    public static class UpdateSpecialty {
        private Specialty specialty;
    }

    @Getter
    @NoArgsConstructor
    public static class InitProfileSetting {

        private String nickname;
        private String job;
        private String profile_img;
        private String intro_content;
        private String work_email;
        private String work_time;
        private String linked_in;
        private String brunch;
        private String insta;
        private String phone_number;
    }

    @Getter
    @NoArgsConstructor
    public static class InitTendencySetting {
        private String tendency;
    }

    @Getter
    @NoArgsConstructor
    public static class InitInterestSetting {
        private String interest;
    }





}
