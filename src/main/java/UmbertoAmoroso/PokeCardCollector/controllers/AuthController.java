package UmbertoAmoroso.PokeCardCollector.controllers;

import UmbertoAmoroso.PokeCardCollector.dto.AuthRequest;
import UmbertoAmoroso.PokeCardCollector.dto.AuthResponse;
import UmbertoAmoroso.PokeCardCollector.entities.User;
import UmbertoAmoroso.PokeCardCollector.repositories.UserRepository;
import UmbertoAmoroso.PokeCardCollector.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Metodo per la registrazione, solo per utenti con ruolo USER
    @PostMapping("/register")
    public String register(@RequestBody AuthRequest authRequest) {
        User user = new User();
        user.setUsername(authRequest.getUsername());
        user.setPassword(passwordEncoder.encode(authRequest.getPassword()));
        user.setEmail(authRequest.getEmail());  // Salva l'email dell'utente
        user.setRole(User.Role.USER);
        userRepository.save(user);
        return "User registered successfully as USER";
    }

    // Metodo per la registrazione di admin, accessibile solo via Postman
    @PostMapping("/register/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String registerAdmin(@RequestBody AuthRequest authRequest) {
        User user = new User();
        user.setUsername(authRequest.getUsername());
        user.setPassword(passwordEncoder.encode(authRequest.getPassword()));
        user.setEmail(authRequest.getEmail());
        user.setRole(User.Role.ADMIN);
        userRepository.save(user);
        return "Admin registered successfully";
    }


    // Metodo per il login, accettando username o email
    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest authRequest) throws Exception {
        // Cerca l'utente per username o email
        User user = userRepository.findByUsernameOrEmail(authRequest.getUsername(), authRequest.getEmail())
                .orElseThrow(() -> new Exception("User not found"));

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), authRequest.getPassword()));

        // Genera il token includendo sia username che role
        String token = jwtUtil.generateToken(user.getUsername(), user.getRole().name());
        return new AuthResponse(token, user.getUsername(), user.getRole().name());
    }

    // Metodo per ottenere i dettagli dell'utente autenticato
    @GetMapping("/me")
    public AuthResponse getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            // Recupera l'utente dal repository utilizzando il nome utente del Principal
            User user = userRepository.findByUsername(userDetails.getUsername())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found"));

            return new AuthResponse(null, user.getUsername(), user.getRole().name());
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
    }
}
