package TeamDPlus.code.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class LoginResponseDto {
    private Long account_id;
    private String profile_img;
    private String access_token;
    private String refresh_token;
    private Boolean isSignUp;

    @Builder
    public LoginResponseDto(final Long account_id, final String profile_img, final String access_token, final String refresh_token, final Boolean isSignUp) {
        this.account_id = account_id;
        this.profile_img = profile_img;
        this.access_token = access_token;
        this.refresh_token = refresh_token;
        this.isSignUp = isSignUp;
    }

}
