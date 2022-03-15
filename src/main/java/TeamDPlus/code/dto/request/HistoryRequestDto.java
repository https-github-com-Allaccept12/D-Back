package TeamDPlus.code.dto.request;

import TeamDPlus.code.domain.account.Account;
import TeamDPlus.code.domain.account.history.History;
import TeamDPlus.code.dto.response.HistoryResponseDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class HistoryRequestDto {

    @Getter
    @NoArgsConstructor
    public static class HistoryUpdateList {
        private List<HistoryUpdate> history;
    }

    @Getter
    @NoArgsConstructor
    public static class HistoryUpdate {

        private String company_name;

        private String company_department;

        private String company_position;

        private String work_start;

        private String work_end;

        private String achievements;

        @Builder
        public HistoryUpdate(String company_name, String company_department, String company_position, String work_start, String work_end, String achievements) {
            this.company_name = company_name;
            this.company_department = company_department;
            this.company_position = company_position;
            this.work_start = work_start;
            this.work_end = work_end;
            this.achievements = achievements;
        }

        public static History toEntity(HistoryRequestDto.HistoryUpdate dto, Account account) {
            return History.builder()
                    .companyName(dto.company_name)
                    .companyDepartment(dto.company_department)
                    .companyPosition(dto.company_position)
                    .achievements(dto.getAchievements())
                    .workStart(dto.getAchievements())
                    .workEnd(dto.getWork_end())
                    .account(account)
                    .build();

        }
    }

}
