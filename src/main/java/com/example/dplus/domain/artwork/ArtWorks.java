package com.example.dplus.domain.artwork;


import com.example.dplus.domain.BaseEntity;
import com.example.dplus.domain.account.Account;
import com.example.dplus.dto.request.ArtWorkRequestDto;
import com.example.dplus.dto.request.ArtWorkRequestDto.ArtWorkCreate;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ArtWorks extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    private Long view;

    private String workStart;

    private String workEnd;

    private Boolean isMaster;

    private String specialty;

    private String thumbnail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id",nullable = false)
    private Account account;


    @Builder
    public ArtWorks(final Boolean scope, final String title, final String content, final String category,
                    final String workStart, final String workEnd, final Account account,
                    final Boolean isMaster, final String specialty, final String copyright, final String thumbnail) {
        this.scope = scope;
        this.title = title;
        this.content = content;
        this.category = category;
        this.workStart = workStart;
        this.workEnd = workEnd;
        this.account = account;
        this.isMaster = isMaster;
        this.specialty = specialty;
        this.copyright = copyright;
        this.thumbnail = thumbnail;

    }

    @PrePersist
    public void prePersist() {
        this.view = this.view == null ? 0 : this.view;
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
        this.isMaster = dto.getIs_master();
        this.copyright = dto.getCopyright();
    }

    public void updateArtWorkIsMaster(boolean isMaster) {
        this.isMaster = isMaster;
    }

    public void updateArtWorkIsScope(boolean isScope) {
        this.scope = isScope;
    }

    public void updateArtWorkThumbnail(String thumbnail) {
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
                .isMaster(dto.getIs_master())
                .specialty(dto.getSpecialty())
                .copyright(dto.getCopyright())
                .thumbnail(dto.getThumbnail())
                .build();
    }

}








