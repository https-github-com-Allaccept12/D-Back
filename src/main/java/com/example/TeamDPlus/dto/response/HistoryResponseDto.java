package com.example.TeamDPlus.dto.response;

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
        public History(com.example.TeamDPlus.domain.account.history.History historyEntity) {
            this.history_id = historyEntity.getId();
            this.company_name = historyEntity.getCompanyName();
            this.company_department = historyEntity.getCompanyDepartment();
            this.company_position = historyEntity.getCompanyPosition();
            this.work_start = historyEntity.getWorkStart();
            this.work_end = historyEntity.getWorkEnd();
            this.achievements = historyEntity.getAchievements();
        }
    }

}
