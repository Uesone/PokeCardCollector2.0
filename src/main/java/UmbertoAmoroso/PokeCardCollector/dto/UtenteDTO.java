package UmbertoAmoroso.PokeCardCollector.dto;

import UmbertoAmoroso.PokeCardCollector.entities.Utente;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class UtenteDTO {
    private UUID id;
    private String email;
    private String role;

    // Costruttore che accetta un'istanza di Utente
    public UtenteDTO(Utente utente) {
        this.id = utente.getId();
        this.email = utente.getEmail();
        // Assegna "USER" se il ruolo è null, altrimenti usa il ruolo esistente
        this.role = (utente.getRole() != null) ? utente.getRole().name() : "USER";
    }
}
