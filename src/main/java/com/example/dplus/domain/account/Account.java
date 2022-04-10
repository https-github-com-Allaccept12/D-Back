package com.example.dplus.domain.account;


import com.example.dplus.domain.BaseEntity;
import com.example.dplus.domain.artwork.ArtWorks;
import com.example.dplus.dto.request.AccountRequestDto.InitProfileSetting;
import com.example.dplus.dto.request.AccountRequestDto.UpdateAccountIntro;
import com.example.dplus.dto.request.AccountRequestDto.UpdateSpecialty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Account extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // h2는 auto, mysql는 IDENTITY
    @Column(name = "account_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String accountName;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String nickname;

    private String titleContent;

    private String subContent;

    private String profileImg;

    @Column(nullable = false)
    private int career;

    private String phoneNumber;

    private String workTime;

    private String workEmail;

    private String tendency;

    private Long exp;

    private String linkedIn;

    private String brunch;

    private String instagram;

    private String interest;

    private String job;

    private int artWorkCreateCount;

    private int postCreateCount;

    private String specialty;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rank_id",nullable = false)
    private Rank rank;

    @OneToMany(mappedBy = "account")
    private List<ArtWorks> artWorksList = new ArrayList<>();

    private String other;

    @Builder
    public Account(final String accountName, final String email, final String nickname, final String profileImg,
                   final String other, final Rank rank, final String specialty)  {
        this.accountName = accountName;
        this.email = email;
        this.nickname = nickname;
        this.profileImg = profileImg;
        this.rank = rank;
        this.specialty = specialty;
        this.other = other;
    }

    @PrePersist
    public void prePersist() {
        this.exp = this.exp == null ? 0 : this.exp;
    }

    public void initTendency(final String requestTendency) {
        this.tendency = requestTendency;
    }

    public void updateExp(final int score) {
        this.exp += (long) score;
    }
    public void updateInterest(final String interest) {
        this.interest = interest;
    }

    public void upArtworkCountCreate() {
        this.artWorkCreateCount += 1;
    }
    public void upPostCountCreate() {
        this.postCreateCount += 1;
    }

    public void updateProfileImg(String profileImg) {
        this.profileImg = profileImg;
    }

    public void setInitProfile(final InitProfileSetting dto) {
        this.nickname = dto.getNickname();
        this.job = dto.getJob();
        this.titleContent = dto.getIntro_content();
        this.workEmail = dto.getWork_email();
        this.workTime = dto.getWork_time();
        this.linkedIn = dto.getLinked_in();
        this.brunch = dto.getBrunch();
        this.instagram = dto.getInsta();
        this.phoneNumber = dto.getPhone_number();
    }
    public void updateIntro(final UpdateAccountIntro dto) {
        this.titleContent = dto.getTitle_content();
        this.subContent = dto.getSub_content();
    }
    public void updateSpecialty(final UpdateSpecialty dto) {
        this.specialty = dto.getSpecialty();
        this.other = dto.getOther_specialty();
    }



}













