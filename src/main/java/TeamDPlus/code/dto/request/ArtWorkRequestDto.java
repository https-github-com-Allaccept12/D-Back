package TeamDPlus.code.dto.request;
import TeamDPlus.code.dto.common.CommonDto;
import lombok.Builder;
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

        private boolean is_master;

        @Builder
        public ArtWorkCreate(final String scope,final  String title,final  String content,
                             final List<CommonDto.ImgUrlDto> img,final  String category,
                             final Timestamp work_start,final Timestamp work_end, final boolean is_master) {
            this.scope = scope;
            this.title = title;
            this.content = content;
            this.img = img;
            this.category = category;
            this.work_start = work_start;
            this.work_end = work_end;
            this.is_master = is_master;
        }



    }



}
