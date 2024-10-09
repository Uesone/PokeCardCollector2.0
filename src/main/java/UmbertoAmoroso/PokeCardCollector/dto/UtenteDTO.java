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
        this.role = utente.getRole().name(); // Assumendo che `role` sia un Enum in `Utente`
    }
}
