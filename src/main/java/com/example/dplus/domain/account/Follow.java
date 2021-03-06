package com.example.dplus.domain.account;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@Table(
//        name = "follow_follwing_group",
//        indexes = {@Index(columnList = )}
//)
public class Follow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "follow_id")
    private Long id;

    @Column(nullable = false)
    private Long followerId;

    @Column(nullable = false)
    private Long followingId;


    @Builder
    public Follow(final Long followerId,final Long followingId) {
        this.followerId = followerId;
        this.followingId = followingId;
    }
}
