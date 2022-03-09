package TeamDPlus.code.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

public class HistoryRequestDto {

    @Getter
    @NoArgsConstructor
    public static class HistoryUpdate {

        private String history_name;

        private String history_title;

        private String history_content;
    }

}
