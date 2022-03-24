package TeamDPlus.code.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NotificationResponseDto {

    private int status;
    private String message;

    @Builder
    public NotificationResponseDto(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
