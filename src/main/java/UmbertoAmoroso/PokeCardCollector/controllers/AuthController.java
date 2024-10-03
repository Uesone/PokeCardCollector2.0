package UmbertoAmoroso.PokeCardCollector.controllers;

import UmbertoAmoroso.PokeCardCollector.dto.LoginRequestDTO;
import UmbertoAmoroso.PokeCardCollector.dto.LoginResponseDTO;
import UmbertoAmoroso.PokeCardCollector.dto.RegisterRequestDTO;
import UmbertoAmoroso.PokeCardCollector.entities.Utente;
import UmbertoAmoroso.PokeCardCollector.security.JwtTokenProvider;
import UmbertoAmoroso.PokeCardCollector.services.AuthService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private AuthService authService;

    // Login
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequestDTO loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        Utente userDetails = (Utente) authentication.getPrincipal();

        String jwt = jwtTokenProvider.generateToken(userDetails.getId());

        return ResponseEntity.ok(new LoginResponseDTO(jwt));
    }

    // Registrazione
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequestDTO registerRequest) {
        Utente newUser = authService.registerUser(registerRequest);
        return ResponseEntity.ok(newUser);
    }
}
