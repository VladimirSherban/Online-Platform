package platform.security;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import platform.security.service.impl.UserDetailsServiceImpl;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 * Конфигурация безопастности для SpringSecurity
 * Более тонкие настройки сделаны в CRUD сервисах для контроллеров, анатациями в контроллерах, а так же PermissionService
 */
@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class WebSecurityConfig {

    private final UserDetailsServiceImpl userDetails;

    private static final String[] AUTH_WHITELIST = {
            "/swagger-resources/**",
            "/swagger-ui.html",
            "/v3/api-docs",
            "/webjars/**",
            "/login",
            "/register",
            "/ads"
    };

//    private static final String[] AUTH_WHITELIST = {
//            "/swagger-resources/**",
//            "/swagger-ui.html",
//            "/v3/api-docs",
//            "/webjars/**",
//            "/login",
//            "/register",
//            "/path/t*/*",
//            "/users/avatar/*", // считаю. что эндпоинт должен быть без авторизации
//            "/ads",
//            "/ads/filter",
//            "/ads/*/comments",
//            "/ads/*/comments/*",
//            "/ads/image/*",
//            "/actuator/**" /*TODO  можно закрыть за суперюзером*/
//    };

    @Primary
    @Bean
    public AuthenticationManagerBuilder configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetails);
//                .dataSource(dataSource)
//                .usersByUsernameQuery(
//                        "select email as username, password, 'true' from users where email=?")
//                .authoritiesByUsernameQuery(
//                        "select email as username, role as authority from users where email=?");

        return auth;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeHttpRequests(auth -> auth.mvcMatchers(AUTH_WHITELIST).permitAll()
                        .antMatchers(HttpMethod.OPTIONS).permitAll()
                        .mvcMatchers("/ads/**", "/users/**").authenticated())
                .cors().and().httpBasic(withDefaults());
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
