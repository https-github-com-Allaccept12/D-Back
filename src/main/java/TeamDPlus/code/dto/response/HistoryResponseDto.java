package TeamDPlus.code.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

public class HistoryResponseDto {

    @Getter
    @NoArgsConstructor
    public static class History {

        private Long history_id;
        private String history_name;
        private String history_title;
        private String history_content;
    }

}
