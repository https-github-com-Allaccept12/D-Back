package TeamDPlus.code.dto.request;


import TeamDPlus.code.dto.ImageUrlDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Getter
@NoArgsConstructor
public class ArtWorkUpdateRequestDto {

    private String scope;

    private String title;

    private String content;

    private List<ImageUrlDto> img;

    private Timestamp work_start;

    private Timestamp work_end;

}
