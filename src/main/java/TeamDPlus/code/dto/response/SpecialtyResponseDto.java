package TeamDPlus.code.dto.response;

import TeamDPlus.code.domain.account.Specialty;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class SpecialtyResponseDto {

    @Getter
    public static class specialty {

        private final Specialty specialty;

        public specialty(final Specialty specialty) {
            this.specialty = specialty;
        }
    }

}
