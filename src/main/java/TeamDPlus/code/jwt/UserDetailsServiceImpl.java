package TeamDPlus.code.jwt;

import TeamDPlus.code.advice.ApiRequestException;
import TeamDPlus.code.domain.account.Account;
import TeamDPlus.code.domain.account.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final AccountRepository accountRepository;

    public UserDetails loadUserByUsername(String userPk) throws UsernameNotFoundException {
        Account user = accountRepository.findById(Long.parseLong(userPk))
                .orElseThrow(() -> new ApiRequestException("존재하지 않거나 로그인 하지 않았습니다"));
        return new UserDetailsImpl(user);
    }
}
