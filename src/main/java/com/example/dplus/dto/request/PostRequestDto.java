package com.example.dplus.dto.request;


import com.example.dplus.domain.post.PostBoard;
import com.example.dplus.dto.common.CommonDto.PostTagDto;
import com.example.dplus.dto.common.CommonDto.uploadImgDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import java.util.List;

public class PostRequestDto {


    @Getter
    @NoArgsConstructor
    public static class PostCreate {

        @NotEmpty(message = "제목을 입력해주세요.")
        @Max(value = 20, message = "제목은 20자리 이내로 작성해주세요")
        private String title;

        @NotEmpty(message = "카테고리를 설정해주세요.")
        private String category;

        @NotEmpty(message = "내용을 입력해주세요.")
        private String content;

        private List<uploadImgDto> img; // filename

        private List<PostTagDto> hash_tag;

        private Boolean is_selected;

        private PostBoard board;

    }

    @Getter
    @NoArgsConstructor
    public static class PostUpdate {

        @NotEmpty(message = "제목을 입력해주세요.")
        @Max(value = 20, message = "제목은 20자리 이내로 작성해주세요")
        private String title;

        @NotEmpty(message = "카테고리를 설정해주세요.")
        private String category;

        @NotEmpty(message = "내용을 입력해주세요.")
        private String content;

        private List<uploadImgDto> delete_img;

        private List<PostTagDto> hashTag;

        private Boolean is_selected;

        private PostBoard board;

    }

    @Getter
    @NoArgsConstructor
    public static class PostComment {

        @NotEmpty(message = "댓글 내용을 입력해주세요.")
        private String content;

    }

    @Getter
    @NoArgsConstructor
    public static class PostAnswer {

        @NotEmpty(message = "답글 내용을 입력해주세요.")
        private String content;

    }

}

