package TeamDPlus.code.domain.post.answer;

import TeamDPlus.code.domain.account.Account;
import TeamDPlus.code.domain.post.Post;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostAnswerComment {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "answer_comment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private PostAnswer postAnswer;

    @Lob
    @Column(nullable = false)
    private String content;

    @Builder
    public PostAnswerComment(final Account account, final PostAnswer postAnswer, final String content) {
        this.account = account;
        this.postAnswer = postAnswer;
        this.content = content;
    }

    public void updateComment(final String content) {
        this.content = content;
    }
}
