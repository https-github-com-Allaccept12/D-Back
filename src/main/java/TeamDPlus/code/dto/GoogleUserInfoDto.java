package TeamDPlus.code.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GoogleUserInfoDto {
    private Long id;
    private String name;
    private String profile_img;
    private String email;
}
