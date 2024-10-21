package UmbertoAmoroso.PokeCardCollector.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "collection_cards")
@Getter
@Setter
@NoArgsConstructor
public class CollectionCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "collection_id", nullable = false)
    private Collection collection;

    @Column(nullable = false)
    private String cardId; // Questo sarà l'id della carta proveniente dall'API Pokémon

    @Column(nullable = false)
    private String imageUrl; // URL dell'immagine della carta
}