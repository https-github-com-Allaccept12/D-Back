package TeamDPlus.code.dto.fcm;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class FCMToken {
    private String username;
    private String nickname;

    @Builder
    public FCMToken(String username, String nickname) {
        this.username = username;
        this.nickname = nickname;
    }
}
