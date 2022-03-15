package TeamDPlus.code.domain.account;

import TeamDPlus.code.dto.response.AccountResponseDto;

import java.awt.print.Pageable;
import java.util.List;

public interface AccountRepositoryCustom {

    List<AccountResponseDto.TopArtist> findTopArtist();
}
