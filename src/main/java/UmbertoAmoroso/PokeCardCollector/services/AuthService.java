package UmbertoAmoroso.PokeCardCollector.services;

import UmbertoAmoroso.PokeCardCollector.dto.LoginRequestDTO;
import UmbertoAmoroso.PokeCardCollector.dto.LoginResponseDTO;
import UmbertoAmoroso.PokeCardCollector.dto.RegisterRequestDTO;
import UmbertoAmoroso.PokeCardCollector.entities.Utente;
import UmbertoAmoroso.PokeCardCollector.repositories.UtenteRepository;
import UmbertoAmoroso.PokeCardCollector.security.JwtTokenProvider;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UtenteRepository utenteRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;  // Aggiunto per la codifica delle password

    // Metodo per gestire il login
    public LoginResponseDTO login(LoginRequestDTO loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
        );

        Utente utente = utenteRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String token = jwtTokenProvider.createToken(utente.getEmail());
        return new LoginResponseDTO(token, utente.getRole());
    }

    // Metodo per gestire la registrazione
    public void register(RegisterRequestDTO registerRequest) {
        boolean userExists = utenteRepository.findByEmail(registerRequest.getEmail()).isPresent();
        if (userExists) {
            throw new RuntimeException("Email already in use");
        }

        Utente newUser = new Utente();
        newUser.setEmail(registerRequest.getEmail());
        newUser.setPassword(passwordEncoder.encode(registerRequest.getPassword()));  // Cripta la password
        newUser.setRole("USER");  // Imposta il ruolo predefinito come USER

        utenteRepository.save(newUser);  // Salva il nuovo utente nel database
    }
}
