package UmbertoAmoroso.PokeCardCollector.services;

import UmbertoAmoroso.PokeCardCollector.entities.Utente;
import UmbertoAmoroso.PokeCardCollector.repositories.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UtenteRepository utenteRepository;

    // Metodo per ottenere l'utente autenticato corrente
    public Utente getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Verifica che l'utente sia autenticato
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();

            // Se il principal è di tipo UserDetails, otteniamo l'email (username)
            String email = null;
            if (principal instanceof UserDetails) {
                email = ((UserDetails) principal).getUsername(); // Username è l'email in questo caso
            } else if (principal instanceof String) {
                email = (String) principal;
            }

            // Se è stata trovata un'email valida, otteniamo l'utente dal database
            if (email != null) {
                return utenteRepository.findByEmail(email)
                        .orElseThrow(() -> new RuntimeException("User not found"));
            }
        }

        throw new RuntimeException("User not authenticated");
    }

    // Metodo per ottenere tutti gli utenti (solo admin)
    public List<Utente> getAllUsers() {
        return utenteRepository.findAll();
    }

    // Metodo per eliminare un utente (solo admin)
    public void deleteUser(UUID userId) {
        Utente utente = utenteRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        utenteRepository.delete(utente);
    }
}
