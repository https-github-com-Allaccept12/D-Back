package TeamDPlus.code.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class PostCommentDto {
    private Long account_id;
    private Long comment_id;
    private String content;
    private LocalDateTime modify_time;
}
