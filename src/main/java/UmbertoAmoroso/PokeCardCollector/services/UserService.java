package UmbertoAmoroso.PokeCardCollector.services;

import UmbertoAmoroso.PokeCardCollector.entities.Utente;
import UmbertoAmoroso.PokeCardCollector.repositories.UtenteRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
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
        Object principal = authentication.getPrincipal();
        String email = switch (principal) {
            case UserDetails userDetails -> userDetails.getUsername(); // L'username è l'email

            case Utente utente -> utente.getEmail(); // Se principal è già un Utente

            case String string -> string; // Se principal è una stringa contenente l'email

            case null, default -> throw new RuntimeException("Tipo di autenticazione non supportato");
        };

        // Gestisci diversi tipi di principal

        // Cerca l'utente nel database utilizzando l'email
        return utenteRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));
    }

}