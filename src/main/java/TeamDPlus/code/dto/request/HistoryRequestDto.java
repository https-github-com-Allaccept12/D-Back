package TeamDPlus.code.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

public class HistoryRequestDto {

    @Getter
    @NoArgsConstructor
    public static class HistoryUpdate {

        private String company_name;

        private String company_department;

        private String company_position;

        private String work_start;

        private String work_end;

        private String Achievements;
    }

}
