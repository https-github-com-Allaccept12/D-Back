package TeamDPlus.code.domain.account.history;


import TeamDPlus.code.domain.account.Account;
import TeamDPlus.code.dto.request.HistoryRequestDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class History  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "history_id")
    private Long id;

    private String companyName;

    private String companyDepartment;

    private String companyPosition;

    private String workStart;

    private String workEnd;
    @Lob
    private String achievements;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @Builder
    public History(String companyName, String companyDepartment, String companyPosition,
                   String workStart, String workEnd, String achievements, Account account) {
        this.companyName = companyName;
        this.companyDepartment = companyDepartment;
        this.companyPosition = companyPosition;
        this.workStart = workStart;
        this.workEnd = workEnd;
        this.achievements = achievements;
        this.account = account;
    }

    public void updateHistory(final HistoryRequestDto.HistoryUpdate dto) {
        this.companyName = dto.getCompany_name();
        this.companyDepartment = dto.getCompany_department();
        this.companyPosition = dto.getCompany_position();
        this.workStart = dto.getWork_start();
        this.workEnd = dto.getWork_end();
        this.achievements = dto.getAchievements();
    }

    public static History toEntity(HistoryRequestDto.HistoryUpdate dto, Account account) {
        return History.builder()
                .companyName(dto.getCompany_name())
                .companyDepartment(dto.getCompany_department())
                .companyPosition(dto.getCompany_position())
                .achievements(dto.getAchievements())
                .workStart(dto.getAchievements())
                .workEnd(dto.getWork_end())
                .account(account)
                .build();
    }

}









