package UmbertoAmoroso.PokeCardCollector.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Setter
@Getter
@Entity
public class Utente {

    // Getters and Setters
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String email;
    private String password;
    private String role;

    @OneToMany(mappedBy = "utente")
    private List<Collezione> collezioni;

}
