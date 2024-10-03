package UmbertoAmoroso.PokeCardCollector.services;

import UmbertoAmoroso.PokeCardCollector.entities.Utente;
import UmbertoAmoroso.PokeCardCollector.repositories.UtenteRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Service
public class UserService {

    @Autowired
    private UtenteRepository utenteRepository;

    public Utente getCurrentUser(Authentication authentication) {
        String userEmail = authentication.getName();
        return utenteRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));
    }
}
