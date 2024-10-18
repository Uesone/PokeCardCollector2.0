package UmbertoAmoroso.PokeCardCollector.entities;

<<<<<<< Updated upstream
=======
import UmbertoAmoroso.PokeCardCollector.enums.Role;
>>>>>>> Stashed changes
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Utente {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "utente", cascade = CascadeType.ALL)
    private List<Collezione> collezioni;

    // Getters and Setters
}