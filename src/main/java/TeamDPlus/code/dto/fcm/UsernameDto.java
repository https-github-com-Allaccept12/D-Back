package TeamDPlus.code.dto.fcm;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UsernameDto {
    private String username;

    @Builder
    public UsernameDto(String username) {
        this.username = username;
    }
}
