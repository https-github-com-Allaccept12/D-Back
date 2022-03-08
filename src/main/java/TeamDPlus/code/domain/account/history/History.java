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
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "history_id")
    private Long id;

    @Column(nullable = false)
    private String historyName;

    @Column(nullable = false)
    private String historyTitle;

    @Lob
    @Column(nullable = false)
    private String historyContent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @Builder
    public History(final String historyName,final String historyTitle,final String historyContent,final Account account) {
        this.historyName = historyName;
        this.historyTitle = historyTitle;
        this.historyContent = historyContent;
        this.account = account;
    }

    public void updateHistory(final HistoryRequestDto.HistoryUpdate dto) {
        this.historyContent = dto.getHistory_content();
        this.historyName = dto.getHistory_name();
        this.historyTitle = dto.getHistory_title();
    }

}









