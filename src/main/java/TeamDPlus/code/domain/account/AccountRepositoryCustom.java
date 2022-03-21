package TeamDPlus.code.domain.account;

import TeamDPlus.code.dto.response.AccountResponseDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AccountRepositoryCustom {

    List<AccountResponseDto.TopArtist> findTopArtist(Pageable pageable);

    void accountCreateCountInitialization();
}
