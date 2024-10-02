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

    @ManyToOne
    @JoinColumn(name = "collezione_id", nullable = false)
    private Collezione collezione;

    private String apiId; //  API esterne

    private int quantity;
    private boolean foil;
    private String condition;


}