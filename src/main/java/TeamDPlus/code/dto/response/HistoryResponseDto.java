package TeamDPlus.code.dto.response;

import lombok.Builder;
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

        @Builder
        public History(Long history_id, String history_name, String history_title, String history_content) {
            this.history_id = history_id;
            this.history_name = history_name;
            this.history_title = history_title;
            this.history_content = history_content;
        }
    }

}
