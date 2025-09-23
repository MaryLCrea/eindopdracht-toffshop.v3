package toff.novi.eindopdrachttoffshop.controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import toff.novi.eindopdrachttoffshop.dtos.AuthDto;
import toff.novi.eindopdrachttoffshop.security.JwtService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authManager;
    private final JwtService jwtService;

    public AuthController(AuthenticationManager authManager, JwtService jwtService) {
        this.authManager = authManager;
        this.jwtService = jwtService;
    }

    @PostMapping
    public ResponseEntity<Object> signIn(@RequestBody AuthDto authDto) {
        UsernamePasswordAuthenticationToken up =
                new UsernamePasswordAuthenticationToken(authDto.email, authDto.password);

        try {
            Authentication auth = authManager.authenticate(up);
            UserDetails ud = (UserDetails) auth.getPrincipal();

            List<String> roles = ud.getAuthorities()
                    .stream()
                    .map(a -> {
                        String r = a.getAuthority();
                        return r.startsWith("ROLE_") ? r.substring(5) : r;
                    })
                    .collect(Collectors.toList());

            String token = jwtService.generateToken(ud, roles);

            return ResponseEntity.ok()
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                    .body("Token generated successfully");

        } catch (AuthenticationException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }
}