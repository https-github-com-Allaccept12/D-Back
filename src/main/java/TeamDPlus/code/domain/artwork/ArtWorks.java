package TeamDPlus.code.domain.artwork;


import TeamDPlus.code.domain.BaseEntity;
import TeamDPlus.code.domain.account.Account;
import TeamDPlus.code.domain.artwork.image.ArtWorkImage;
import TeamDPlus.code.dto.request.ArtWorkRequestDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ArtWorks extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "artwork_id")
    private Long id;

    @Column(nullable = false)
    private String scope;

    @Column(nullable = false)
    private String title;

    @Lob
    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String category;

    @Column(columnDefinition = "BIGINT default 0")
    private Long view;

    private Timestamp workStart;

    private Timestamp workEnd;

    private Boolean isMaster;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

//    @OneToMany(mappedBy = "artWorks",cascade = CascadeType.REMOVE)
//    private final List<ArtWorkImage> artWorkImage = new ArrayList<>();

    @Builder
    public ArtWorks(final String scope,final String title,final String content,final String category,
                    final Long view,final Timestamp workStart,final Timestamp workEnd,final Account account, final boolean isMaster) {
        this.scope = scope;
        this.title = title;
        this.content = content;
        this.category = category;
        this.view = view;
        this.workStart = workStart;
        this.workEnd = workEnd;
        this.account = account;
        this.isMaster = isMaster;
    }

    public void updateArtWork(ArtWorkRequestDto.ArtWorkUpdate dto) {
        this.scope = dto.getScope();
        this.title = dto.getTitle();
        this.content = dto.getContent();
        this.workStart = dto.getWork_start();
        this.workEnd = dto.getWork_end();
    }
    public void updateArtWorkIsMaster() {
        this.isMaster = !isMaster; //false 면 트루 , 트루면 false
    }

    public static ArtWorks of(Account account, ArtWorkRequestDto.ArtWorkCreate dto) {
        return ArtWorks.builder()
                .account(account)
                .category(dto.getCategory())
                .content(dto.getContent())
                .scope(dto.getScope())
                .title(dto.getTitle())
                .workStart(dto.getWork_start())
                .workEnd(dto.getWork_end())
                .isMaster(dto.is_master())
                .build();
    }




}