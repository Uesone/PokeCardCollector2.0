package UmbertoAmoroso.PokeCardCollector.services;

import UmbertoAmoroso.PokeCardCollector.entities.Utente;
import UmbertoAmoroso.PokeCardCollector.repositories.UtenteRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UtenteRepository utenteRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Utente utente = utenteRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Utente non trovato con email: " + email));

        return org.springframework.security.core.userdetails.User
                .withUsername(utente.getEmail())
                .password(utente.getPassword())
                .roles(utente.getRole().name()) // Assicura che i ruoli siano corretti
                .build();
    }

    // Metodo per ottenere un'istanza completa di Utente
    public Utente loadFullUserByUsername(String email) throws UsernameNotFoundException {
        return utenteRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Utente non trovato con email: " + email));
    }
}
