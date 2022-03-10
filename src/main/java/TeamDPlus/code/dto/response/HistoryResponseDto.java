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
        public History(final Long history_id,final String history_name,final String history_title,final String history_content) {
            this.history_id = history_id;
            this.history_name = history_name;
            this.history_title = history_title;
            this.history_content = history_content;
        }
    }

}
