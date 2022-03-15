package TeamDPlus.code.domain.account;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account,Long>,AccountRepositoryCustom {
    Optional<Account> findByEmail(String email);
    Optional<Account> findByRefreshToken(String refreshToken);
    Optional<Account> findByNickname(String nickname);
}
