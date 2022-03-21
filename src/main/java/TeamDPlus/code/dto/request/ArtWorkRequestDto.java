package TeamDPlus.code.dto.request;
import TeamDPlus.code.domain.account.Specialty;
import TeamDPlus.code.dto.common.CommonDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

public class ArtWorkRequestDto {


    @Getter
    @NoArgsConstructor
    public static class ArtWorkCreateAndUpdate {
        private boolean scope;

        private String title;

        private String content;

        private List<CommonDto.ImgUrlDto> img;

        private String category;

        private String work_start;

        private String work_end;

        private boolean master;

        private Specialty specialty;

        private String copyright;

    }

    @Getter
    @NoArgsConstructor
    public static class ArtWorkComment {

        private String content;

    }

    @Getter
    @NoArgsConstructor
    public static class ArtWorkPortFolioUpdate {
        private List<Long> artwork_feed;
    }



}
