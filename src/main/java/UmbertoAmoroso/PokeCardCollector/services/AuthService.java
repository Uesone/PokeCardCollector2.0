package UmbertoAmoroso.PokeCardCollector.services;
import UmbertoAmoroso.PokeCardCollector.dto.RegisterRequestDTO;
import UmbertoAmoroso.PokeCardCollector.entities.Utente;
import UmbertoAmoroso.PokeCardCollector.repositories.UtenteRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Service
public class AuthService {

    @Autowired
    private UtenteRepository utenteRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Registra un nuovo utente
    public Utente registerUser(RegisterRequestDTO registerRequest) {
        // Controlla se l'email è già in uso
        if (utenteRepository.existsByEmail(registerRequest.getEmail())) {
            throw new RuntimeException("Email già in uso");
        }

        Utente newUser = new Utente();
        newUser.setEmail(registerRequest.getEmail());
        newUser.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        newUser.setRole(registerRequest.getRole());

        return utenteRepository.save(newUser);
    }
}
