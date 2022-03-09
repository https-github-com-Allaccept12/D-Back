package TeamDPlus.code.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class LoginResponseDto {
    private Long account_id;
    private String profile_img;
    private String token;

    @Builder
    public LoginResponseDto(final Long account_id, final String profile_img, final String token) {
        this.account_id = account_id;
        this.profile_img = profile_img;
        this.token = token;
    }

}
