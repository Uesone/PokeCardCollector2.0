package UmbertoAmoroso.PokeCardCollector.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CardDTO {
    private Long id;
    private String cardId; // ID della carta dall'API Pokémon
    private String imageUrl; // URL dell'immagine della carta
}