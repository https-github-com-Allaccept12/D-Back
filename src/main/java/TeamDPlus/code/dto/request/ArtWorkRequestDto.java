package TeamDPlus.code.dto.request;

import TeamDPlus.code.dto.common.CommonDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

public class ArtWorkRequestDto {

    @Getter
    @NoArgsConstructor
    public static class ArtWorkUpdate {
        private String scope;

        private String title;

        private String content;

        private List<CommonDto.ImgUrlDto> img;

        private Timestamp work_start;

        private Timestamp work_end;
    }

    @Getter
    @NoArgsConstructor
    public static class ArtWorkCreate {
        private String scope;

        private String title;

        private String content;

        private List<CommonDto.ImgUrlDto> img;

        private String category;

        private Timestamp work_start;

        private Timestamp work_end;
    }


}
