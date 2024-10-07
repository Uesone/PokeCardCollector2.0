package UmbertoAmoroso.PokeCardCollector.controllers;

import UmbertoAmoroso.PokeCardCollector.dto.LoginRequestDTO;
import UmbertoAmoroso.PokeCardCollector.dto.LoginResponseDTO;
import UmbertoAmoroso.PokeCardCollector.dto.RegisterRequestDTO;
import UmbertoAmoroso.PokeCardCollector.entities.Utente;
import UmbertoAmoroso.PokeCardCollector.security.JwtTokenProvider;
import UmbertoAmoroso.PokeCardCollector.services.AuthService;
import UmbertoAmoroso.PokeCardCollector.services.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private AuthService authService;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    // Login
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequestDTO loginRequest) {
        // Autentica l'utente
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Ottieni i dettagli dell'utente autenticato
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Utente utente = userDetailsService.loadFullUserByUsername(userDetails.getUsername());

        // Ottieni i ruoli dell'utente come lista di stringhe
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        // Genera il token JWT con i ruoli dell'utente
        String jwt = jwtTokenProvider.generateToken(utente.getId(), roles);

        return ResponseEntity.ok(new LoginResponseDTO(jwt));
    }

    // Registrazione
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequestDTO registerRequest) {
        Utente newUser = authService.registerUser(registerRequest);
        return ResponseEntity.ok(newUser);
    }
}
