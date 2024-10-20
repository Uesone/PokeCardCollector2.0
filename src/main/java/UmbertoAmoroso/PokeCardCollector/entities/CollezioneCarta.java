package UmbertoAmoroso.PokeCardCollector.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
@Entity
public class CollezioneCarta {

    // Getters and Setters
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String apiId;
    private int quantity;
    private boolean holo;
    private String condition;

    @ManyToOne
    @JoinColumn(name = "collezione_id")
    private Collezione collezione;

}
