package TeamDPlus.code.dto;

import TeamDPlus.code.dto.ImageUrlDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

public class ArtWorkDto {

    @Getter
    @NoArgsConstructor
    public static class ArtWorkUpdate {
        private String scope;

        private String title;

        private String content;

        private List<ImageUrlDto> img;

        private Timestamp work_start;

        private Timestamp work_end;
    }


}
