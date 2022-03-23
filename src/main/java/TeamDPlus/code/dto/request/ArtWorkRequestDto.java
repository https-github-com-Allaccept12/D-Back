package TeamDPlus.code.dto.request;
import TeamDPlus.code.domain.account.Specialty;
import TeamDPlus.code.dto.common.CommonDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class ArtWorkRequestDto {


    @Getter
    @NoArgsConstructor
    public static class ArtWorkCreateAndUpdate {
        private boolean scope;

        @NotEmpty(message = "작품 제목을 입력해주세요")
        @Max(value = 20, message = "제목은 20자리 이내로 작성해주세요")
        private String title;

        @NotEmpty(message = "작품 설명을 해주세요.")
        private String content;

        private List<CommonDto.ImgUrlDto> img = new ArrayList<>();

        private String thumbnail;

        @NotEmpty(message = "카테고리를 설정해주세요.")
        private String category;

        @NotEmpty(message = "작품의 작업시작 기간을 입력해주세요")
        private String work_start;

        @NotEmpty(message = "작품의 작업마무리 기간을 입력해주세요")
        private String work_end;

        private boolean master;

        private Specialty specialty;

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
