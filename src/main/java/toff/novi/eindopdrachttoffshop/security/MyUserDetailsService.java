//package toff.novi.eindopdrachttoffshop.security;
//
//
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import toff.novi.eindopdrachttoffshop.models.User;
//import toff.novi.eindopdrachttoffshop.repositories.UserRepository;
//
//import java.util.stream.Collectors;
//
//public class MyUserDetailsService implements UserDetailsService {
//
//    private final UserRepository userRepository;
//
//    public MyUserDetailsService(UserRepository repos) {
//        this.userRepository = repos;
//    }
//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        User user = userRepository.findByEmail(email)
//                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
//        return new org.springframework.security.core.userdetails.User(
//                user.getEmail(),
//                user.getPassword(),
//                user.getRoles().stream()
//                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getRolename()))
//                        .collect(Collectors.toList())
//        );
//    }
//    }

    package toff.novi.eindopdrachttoffshop.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import toff.novi.eindopdrachttoffshop.models.User;
import toff.novi.eindopdrachttoffshop.repositories.UserRepository;

public class MyUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public MyUserDetailsService(UserRepository repos) {
        this.userRepository = repos;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new MyUserDetails(user); // Gebruik jouw eigen UserDetails class
    }
}



