package TeamDPlus.code.jwt;

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
                .orElseThrow(() -> new UsernameNotFoundException("Can't find " + userPk));

        return new UserDetailsImpl(user);
    }
}
