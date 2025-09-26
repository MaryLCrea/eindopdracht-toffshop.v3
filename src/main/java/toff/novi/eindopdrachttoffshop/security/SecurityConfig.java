package toff.novi.eindopdrachttoffshop.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import toff.novi.eindopdrachttoffshop.repositories.UserRepository;

@Configuration
public class SecurityConfig {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    public SecurityConfig(JwtService jwtService, UserRepository userRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public MyUserDetailsService userDetailsService() {
        return new MyUserDetailsService(userRepository);
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return new org.springframework.security.authentication.ProviderManager(authProvider);
    }

    @Bean
    public JwtRequestFilter jwtRequestFilter() {
        return new JwtRequestFilter(jwtService, userDetailsService());
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http

                .authorizeHttpRequests(auth -> auth

                      .requestMatchers(HttpMethod.POST, "/auth").permitAll()
                      .requestMatchers(HttpMethod.POST, "/users").permitAll()
                      .requestMatchers(HttpMethod.GET, "/users").hasRole("ADMIN")
                      .requestMatchers(HttpMethod.GET, "/users/*").authenticated()
                      .requestMatchers(HttpMethod.DELETE, "/users/*").authenticated()
                      .requestMatchers(HttpMethod.PUT, "/users/*").authenticated()

                      .requestMatchers(HttpMethod.POST, "/carts/user/*/items").hasRole("ADMIN")
                      .requestMatchers(HttpMethod.POST, "/carts/me/items").authenticated()
                      .requestMatchers(HttpMethod.GET, "/carts/user/*").hasRole("ADMIN")
                      .requestMatchers(HttpMethod.PUT, "/carts/user/*/items/*").hasRole("ADMIN")
                      .requestMatchers(HttpMethod.DELETE, "/user/{userId}/items/**").hasAnyRole("CUSTOMER", "ADMIN")
                      .requestMatchers(HttpMethod.DELETE, "/user/{userId}/clear").hasAnyRole("CUSTOMER", "ADMIN")

                      .requestMatchers(HttpMethod.POST, "/contacts").permitAll()
                      .requestMatchers(HttpMethod.GET, "/contacts/unread").hasRole("ADMIN")
                      .requestMatchers(HttpMethod.GET, "/contacts").authenticated()
                      .requestMatchers(HttpMethod.GET, "/contacts/*").authenticated()
                      .requestMatchers(HttpMethod.GET, "/contacts/search").authenticated()
                      .requestMatchers(HttpMethod.PATCH, "/contacts/*/unread").authenticated()
                      .requestMatchers(HttpMethod.DELETE, "/contacts/*").authenticated()

                      .requestMatchers(HttpMethod.GET, "/order-items/status/*").hasRole("ADMIN")
                      .requestMatchers(HttpMethod.GET, "/order-items/user/*").authenticated()
                      .requestMatchers(HttpMethod.GET, "/order-items/user/*/status/*").authenticated()
                      .requestMatchers(HttpMethod.GET, "/order-items/*").authenticated()

                      .anyRequest().denyAll()
                )
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(csrf -> csrf.disable())
                .addFilterBefore(jwtRequestFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
