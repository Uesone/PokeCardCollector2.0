package UmbertoAmoroso.PokeCardCollector.controllers;

import UmbertoAmoroso.PokeCardCollector.dto.LoginRequestDTO;
import UmbertoAmoroso.PokeCardCollector.dto.LoginResponseDTO;
import UmbertoAmoroso.PokeCardCollector.dto.RegisterRequestDTO;
import UmbertoAmoroso.PokeCardCollector.dto.UtenteDTO;
import UmbertoAmoroso.PokeCardCollector.entities.Utente;
import UmbertoAmoroso.PokeCardCollector.security.JwtTokenProvider;
import UmbertoAmoroso.PokeCardCollector.services.AuthService;
import UmbertoAmoroso.PokeCardCollector.services.CustomUserDetailsService;
import UmbertoAmoroso.PokeCardCollector.services.UserService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

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

    @Autowired
    private UserService userService;

    // Endpoint di login
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequestDTO loginRequest) {
        // Autentica l'utente tramite Spring Security
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        // Imposta l'autenticazione nel SecurityContext
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Ottieni i dettagli dell'utente autenticato
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        // Carica l'entità Utente dal repository utilizzando l'email
        Utente utente = userService.getCurrentUser(authentication); // Usa il servizio per ottenere l'utente

        // Ottieni i ruoli dell'utente autenticato
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        // Genera il token JWT
        String jwt = jwtTokenProvider.generateToken(utente.getId(), roles);

        // Restituisci il token JWT in risposta
        return ResponseEntity.ok(new LoginResponseDTO(jwt));
    }


    // Endpoint di registrazione
    @PostMapping("/register")
    public ResponseEntity<UtenteDTO> registerUser(@RequestBody RegisterRequestDTO registerRequest) {
        Utente newUtente = authService.registerUser(registerRequest);
        return ResponseEntity.ok(new UtenteDTO(newUtente));
    }

    // Endpoint per ottenere i dettagli dell'utente loggato
    @GetMapping("/me")
    public ResponseEntity<UtenteDTO> getUserDetails(Authentication authentication) {
        Utente utente = userService.getCurrentUser(authentication);
        return ResponseEntity.ok(new UtenteDTO(utente));
    }
}
