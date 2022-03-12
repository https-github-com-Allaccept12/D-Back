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
        private String scope;

        private String title;

        private String content;

        private List<CommonDto.ImgUrlDto> img;

        private String category;

        private Timestamp work_start;

        private Timestamp work_end;

        private boolean is_master;

        private Specialty specialty;


    }

    @Getter
    @NoArgsConstructor
    public static class ArtWorkComment {

        private String content;

    }

    @Getter
    @NoArgsConstructor
    public static class ArtWorkPortFolioUpdate {

        private Long artwork_id;
        private String scope;
        private String img;
        private String content;
        private String category;
        private Timestamp create_time;
        private Timestamp modify_time;
    }



}
