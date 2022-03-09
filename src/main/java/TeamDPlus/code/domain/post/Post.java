package TeamDPlus.code.domain.post;


import TeamDPlus.code.domain.BaseEntity;
import TeamDPlus.code.domain.account.Account;
import TeamDPlus.code.dto.request.PostRequestDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "post_id")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Lob
    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @Builder
    public Post(final String title,final String content,final String category,final Account account) {
        this.title = title;
        this.content = content;
        this.category = category;
        this.account = account;
    }

    public void updatePost(PostRequestDto.PostUpdate dto) {
        this.title = dto.getTitle();
        this.category = dto.getCategory();
        this.content = dto.getContent();
    }




}
