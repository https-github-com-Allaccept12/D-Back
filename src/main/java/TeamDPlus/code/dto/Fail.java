package TeamDPlus.code.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Fail {

    private String result = "fail";
    private String msg;

    public Fail(String msg) {
        this.msg = msg;
    }
}
