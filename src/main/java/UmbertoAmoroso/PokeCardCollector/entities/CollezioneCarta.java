package UmbertoAmoroso.PokeCardCollector.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CollezioneCarta {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String apiId;  // L'ID della carta da Pokémon TCG API

    @ManyToOne
    @JoinColumn(name = "collezione_id")
    private Collezione collezione;

    @Column(nullable = false)
    private int quantity;  // Quantità della carta nella collezione

    @Column(nullable = false)
    private boolean holo;  // Indica se la carta è olografica

    @Column(nullable = false)
    private String condition;  // Condizione della carta (es. "Mint", "Near Mint", ecc.)

    public boolean holo() {
        return holo;
    }
}
