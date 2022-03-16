package TeamDPlus.code.domain.artwork;


import TeamDPlus.code.domain.BaseEntity;
import TeamDPlus.code.domain.account.Account;
import TeamDPlus.code.domain.account.Specialty;
import TeamDPlus.code.domain.artwork.image.ArtWorkImage;
import TeamDPlus.code.dto.request.ArtWorkRequestDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@DynamicInsert
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ArtWorks extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "artwork_id")
    private Long id;

    @Column(nullable = false)
    private boolean scope;

    @Column(nullable = false)
    private String title;

    @Lob
    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String category;

    @Column(columnDefinition = "BIGINT default 0")
    private Long view;

    private String workStart;

    private String workEnd;

    @Column(columnDefinition = "TINYINT default 0")
    private Boolean isMaster;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @Embedded
    private Specialty specialty;

    @Builder
    public ArtWorks(final boolean scope,final String title,final String content,final String category,
                    final Long view,final String workStart,final String workEnd,final Account account,
                    final boolean isMaster, final Specialty specialty) {
        this.scope = scope;
        this.title = title;
        this.content = content;
        this.category = category;
        this.view = view;
        this.workStart = workStart;
        this.workEnd = workEnd;
        this.account = account;
        this.isMaster = isMaster;
        this.specialty = specialty;
    }

    public void addViewCount() {
        this.view += 1L;
    }

    public void updateArtWork(ArtWorkRequestDto.ArtWorkCreateAndUpdate dto) {
        this.scope = dto.isScope();
        this.title = dto.getTitle();
        this.content = dto.getContent();
        this.category = dto.getCategory();
        this.workStart = dto.getWork_start();
        this.workEnd = dto.getWork_end();
    }
    public void updateArtWorkIsMaster() {
        this.isMaster = !isMaster; //false 면 트루 , 트루면 false
    }
    public void updateArtWorkIsScope() {
        this.scope = !scope;
    }

    public static ArtWorks of(Account account, ArtWorkRequestDto.ArtWorkCreateAndUpdate dto) {
        return ArtWorks.builder()
                .account(account)
                .category(dto.getCategory())
                .content(dto.getContent())
                .scope(dto.isScope())
                .title(dto.getTitle())
                .workStart(dto.getWork_start())
                .workEnd(dto.getWork_end())
                .isMaster(dto.is_master())
                .specialty(dto.getSpecialty())
                .build();
    }




}








