package com.example.TeamDPlus.dto.request;

import com.example.TeamDPlus.dto.common.CommonDto.ImgUrlDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import java.util.List;

public class ArtWorkRequestDto {


    @Getter
    @NoArgsConstructor
    public static class ArtWorkCreate {
        private Boolean scope;

        @NotEmpty(message = "작품 제목을 입력해주세요")
        @Max(value = 20, message = "제목은 20자리 이내로 작성해주세요")
        private String title;

        @NotEmpty(message = "작품 설명을 해주세요.")
        private String content;

        @NotEmpty(message = "카테고리를 설정해주세요.")
        private String category;

        @NotEmpty(message = "작품의 작업시작 기간을 입력해주세요")
        private String work_start;

        @NotEmpty(message = "작품의 작업마무리 기간을 입력해주세요")
        private String work_end;

        private Boolean master;

        private String specialty;

        @NotEmpty(message = "작품 판권 설정을 해주세요.")
        private String copyright;

        @NotEmpty(message = "작품 섬네일을 지정해주세요.")
        private String thumbnail;

    }
    @Getter
    @NoArgsConstructor
    public static class ArtWorkUpdate {

        private Boolean scope;

        @NotEmpty(message = "작품 제목을 입력해주세요")
        @Max(value = 20, message = "제목은 20자리 이내로 작성해주세요")
        private String title;

        @NotEmpty(message = "작품 설명을 해주세요.")
        private String content;

        private List<ImgUrlDto> delete_img;

        private String thumbnail;

        @NotEmpty(message = "카테고리를 설정해주세요.")
        private String category;

        @NotEmpty(message = "작품의 작업시작 기간을 입력해주세요")
        private String work_start;

        @NotEmpty(message = "작품의 작업마무리 기간을 입력해주세요")
        private String work_end;

        private Boolean master;

        private String specialty;

        @NotEmpty(message = "작품 판권 설정을 해주세요.")
        private String copyright;


    }

    @Getter
    @NoArgsConstructor
    public static class ArtWorkComment {

        @NotEmpty(message = "댓글 내용을 입력해주세요.")
        private String content;

    }

    @Getter
    @NoArgsConstructor
    public static class ArtWorkPortFolioUpdate {
        private List<Long> artwork_feed;
    }



}
