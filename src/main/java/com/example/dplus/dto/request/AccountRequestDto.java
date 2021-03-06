package com.example.dplus.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;

public class AccountRequestDto {


    @Getter
    @NoArgsConstructor
    public static class UpdateAccountIntro {

        @NotEmpty(message = "타이틀 내용을 입력해주세요.")
        private String title_content;
        @NotEmpty(message = "내용을 입력해주세요")
        private String sub_content;
    }

    @Getter
    @NoArgsConstructor
    public static class UpdateSpecialty {
        private String specialty;
        private String other_specialty;
    }

    @Getter
    @NoArgsConstructor
    public static class InitProfileSetting {

        @NotEmpty(message = "닉네임을 입력해주세요.")
        @Max(value = 10, message = "닉네임은 10자리 이하로 입력해주세요")
        private String nickname;
        @NotEmpty(message = "직업을 선택해주세요")
        private String delete_profile_img;
        private String job;
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

    @Getter
    @NoArgsConstructor
    public static class AccountVisit {
        private Long account_id;
    }

    @Getter
    @NoArgsConstructor
    public static class AccountMasterPiece {
        private Long prev_artwork_id;
    }


}
