package com.example.dplus.domain.artwork;


import com.example.dplus.domain.BaseEntity;
import com.example.dplus.domain.account.Account;
import com.example.dplus.dto.request.ArtWorkRequestDto;
import com.example.dplus.dto.request.ArtWorkRequestDto.ArtWorkCreate;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

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
    private Boolean scope;

    @Column(nullable = false)
    private String title;

    @Lob
    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private String copyright;

    @Column(columnDefinition = "BIGINT default 0")
    private Long view;

    private String workStart;

    private String workEnd;

    @Column(columnDefinition = "TINYINT default 0")
    private Boolean isMaster;

    @Embedded
    private String specialty;

    private String thumbnail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @Builder
    public ArtWorks(final Boolean scope,final String title,final String content,final String category,
                    final Long view,final String workStart,final String workEnd,final Account account,
                    final Boolean isMaster, final String specialty,final String copyright) {
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
        this.copyright = copyright;
    }

    public void addViewCount() {
        this.view += 1L;
    }

    public void updateArtWork(ArtWorkRequestDto.ArtWorkUpdate dto) {
        this.scope = dto.getScope();
        this.title = dto.getTitle();
        this.content = dto.getContent();
        this.category = dto.getCategory();
        this.workStart = dto.getWork_start();
        this.workEnd = dto.getWork_end();
        this.isMaster = dto.getMaster();
        this.copyright = dto.getCopyright();
    }
    public void updateArtWorkIsMaster(boolean isMaster) {
        this.isMaster = isMaster;
    }
    public void updateArtWorkIsScope(boolean isScope) {
        this.scope = isScope;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public void updateArtoWorkThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public static ArtWorks of(Account account, ArtWorkCreate dto) {
        return ArtWorks.builder()
                .account(account)
                .category(dto.getCategory())
                .content(dto.getContent())
                .scope(dto.getScope())
                .title(dto.getTitle())
                .workStart(dto.getWork_start())
                .workEnd(dto.getWork_end())
                .isMaster(dto.getMaster())
                .specialty(dto.getSpecialty())
                .copyright(dto.getCopyright())
                .view(0L)
                .build();
    }

}







