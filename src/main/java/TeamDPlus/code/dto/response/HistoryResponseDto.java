package TeamDPlus.code.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class HistoryResponseDto {

    @Getter
    @NoArgsConstructor
    public static class History {

        private Long history_id;
        private String company_name;

        private String company_department;

        private String company_position;

        private String work_start;

        private String work_end;

        private String achievements;

        @Builder
        public History(Long history_id, String company_name, String company_department,
                       String company_position, String work_start, String work_end, String achievements) {
            this.history_id = history_id;
            this.company_name = company_name;
            this.company_department = company_department;
            this.company_position = company_position;
            this.work_start = work_start;
            this.work_end = work_end;
            this.achievements = achievements;
        }
    }

}
