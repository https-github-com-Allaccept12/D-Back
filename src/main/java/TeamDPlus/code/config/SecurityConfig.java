package TeamDPlus.code.config;

import TeamDPlus.code.jwt.JwtAuthenticationFilter;
import TeamDPlus.code.jwt.JwtTokenProvider;
import TeamDPlus.code.service.account.OAuth2DetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;
import org.springframework.web.filter.CorsFilter;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity // 스프링 Security 지원을 가능하게 함
@EnableGlobalMethodSecurity(securedEnabled = true) // @Secured 어노테이션 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;
    private final CorsFilter corsFilter;
    private final OAuth2DetailsService oAuth2DetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) {
        // h2-console 사용에 대한 허용 (CSRF, FrameOptions 무시)
        web
                .ignoring()
                .antMatchers("/h2-console/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                    .httpBasic().disable() // rest api 만을 고려하여 기본 설정은 해제하겠습니다.
                    .csrf().disable() // csrf 보안 토큰 disable처리.
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 토큰 기반 인증이므로 세션 역시 사용하지 않습니다.
                .and()
                    .authorizeRequests() // 요청에 대한 사용권한 체크
                    .antMatchers("/oauth2/**").permitAll()
                    .antMatchers("/user/**").permitAll()
                    .antMatchers("/").permitAll()
                    .anyRequest().authenticated() // 그외 나머지 요청은 사용권한 체크
                .and()
                    .oauth2Login()
                    .userInfoEndpoint()
                    .userService(oAuth2DetailsService);
        http.addFilterBefore(corsFilter,
                SecurityContextPersistenceFilter.class);
        http.addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider),
                UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}