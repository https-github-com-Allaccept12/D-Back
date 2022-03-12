package TeamDPlus.code.domain.account.history;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HistoryRepository extends JpaRepository<History,Long> {

    List<History> findAllByAccountId(Long accountId);

    void deleteAllByAccountId(Long accountId);

}
