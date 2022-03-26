package com.example.dplus.dto.request;

import com.example.dplus.domain.account.Specialty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class AccountRequestDto {


    @Getter
    @NoArgsConstructor
    public static class AccountVisit {
        private Long account_id;
    }

    @Getter
    @NoArgsConstructor
    public static class UpdateAccountInfo {

        @NotEmpty(message = "닉네임을 입력해주세요.")
        @Max(value = 10, message = "닉네임은 10자리 이하로 입력해주세요")
        private String nickname;
        private String work_email;
        private String work_time;
        private String linked_in;
        private String brunch;
        private String insta;
        private String phone_number;
        @NotNull(message = "현재 직업을 선택해주세요")
        private String job;

    }

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
        private Specialty specialty;
    }

    @Getter
    @NoArgsConstructor
    public static class InitProfileSetting {

        @NotEmpty(message = "닉네임을 입력해주세요.")
        @Max(value = 10, message = "닉네임은 10자리 이하로 입력해주세요")
        private String nickname;
        @NotEmpty(message = "직업을 선택해주세요")
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

    @Getter
    @NoArgsConstructor
    public static class setAccountMasterPiece {
        private String img_url_fir;
        private String img_url_sec;
    }
}
