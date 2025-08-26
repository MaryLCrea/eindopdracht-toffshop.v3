package toff.novi.eindopdrachttoffshop.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import toff.novi.eindopdrachttoffshop.repositories.UserRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfig  {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    public SecurityConfig(JwtService service, UserRepository userRepos) {
        this.jwtService = service;
        this.userRepository = userRepos;
    }

    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService udService, PasswordEncoder passwordEncoder) {
        var auth = new DaoAuthenticationProvider();
        auth.setPasswordEncoder(passwordEncoder);
        auth.setUserDetailsService(udService);
        return new ProviderManager(auth);
    }

    public UserDetailsService userDetailsService(PasswordEncoder encoder) {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();

        UserDetails user1 = User
                .withUsername("Pieterje")
                .password(encoder.encode("Puk"))
                .roles("USER")
                .build();

        manager.createUser(user1);
        return manager;
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new MyUserDetailsService(this.userRepository);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, "/auth").permitAll()
                        .requestMatchers(HttpMethod.POST, "/users/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/contacts/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/order-items/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/image/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/carts/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/products/**").permitAll()

                        .requestMatchers(HttpMethod.GET, "/home/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/users/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/contacts/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/order-items/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/image/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/carts/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/products/**").permitAll()

                        .requestMatchers(HttpMethod.PUT, "/auth/**").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/users/**").permitAll()
                        .requestMatchers(HttpMethod.PATCH, "/contacts/**").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/order-items/**").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/image/**").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/carts/**").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/products/**").permitAll()


                        .requestMatchers("/order_items").hasRole("ADMIN")
                        .requestMatchers("/carts").authenticated()
                        .requestMatchers("/contacts", "/ADMIN/*").authenticated()
//                        .anyRequest().denyAll()
                )
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(csrf -> csrf.disable())
                .addFilterBefore(new JwtRequestFilter(jwtService, userDetailsService()), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}























//package toff.novi.eindopdrachttoffshop.security;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//public class SecurityConfig {
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public UserDetailsService userDetailsService(PasswordEncoder encoder) {
//        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//
//        UserDetails user1 = User
//                .withUsername("Pietje")
//                .password(encoder.encode("Puk"))
//                .roles("USER")
//                .build();
//
//        manager.createUser(user1);
//
//        UserDetails user2 = User
//                .withUsername("Annie")
//                .password(encoder.encode("Konijn"))
//                .roles("ADMIN")
//                .build();
//
//        manager.createUser(user2);
//
//        return manager;
//
//    }
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http)throws Exception {
//        http
//                .httpBasic(Customizer.withDefaults())
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/secret").hasRole("ADMIN")
//                        .requestMatchers("/hello").permitAll())
//                .sessionManagement(sess->sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .csrf(csrf ->csrf.disable());
//                  return http.build();
//
//
//    }
//}
//} filterChain(HttpSecurity http) throws Exception {
//        http
//                .formLogin(form -> form.disable())  // Disable default login form
//                .httpBasic(basic -> basic.disable())  // Disable basic auth
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/", "/test", "/create-test-user", "/auth", "/test.html").permitAll()
//                        .anyRequest().authenticated()
//                )
//                .csrf(csrf -> csrf.disable());
//
//        return http.build();
//    }
//}
