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
    private int view;

    private Timestamp workStart;

    private Timestamp workEnd;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @OneToMany(mappedBy = "artWorks",cascade = CascadeType.REMOVE)
    private final List<ArtWorkImage> artWorkImage = new ArrayList<>();

    @Builder
    public ArtWorks(final String scope,final String title,final String content,final String category,
                    final int view,final Timestamp workStart,final Timestamp workEnd,final Account account) {
        this.scope = scope;
        this.title = title;
        this.content = content;
        this.category = category;
        this.view = view;
        this.workStart = workStart;
        this.workEnd = workEnd;
        this.account = account;
    }

    public void updateArtWork(ArtWorkRequestDto.ArtWorkUpdate dto) {
        this.scope = dto.getScope();
        this.title = dto.getTitle();
        this.content = dto.getContent();
        this.workStart = dto.getWork_start();
        this.workEnd = dto.getWork_end();
    }






}








