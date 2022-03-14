package TeamDPlus.code.domain.post.answer;

import TeamDPlus.code.domain.BaseEntity;
import TeamDPlus.code.domain.post.Post;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostAnswer extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "post_answer_id")
    private Long id;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @Lob
    @Column(nullable = false)
    private String content;

}
