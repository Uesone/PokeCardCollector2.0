package UmbertoAmoroso.PokeCardCollector.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PokemonCardData {
    private String id;
    private String name;
    private ImageUrls images;
    private CardSet set;
    private String rarity;
    // Aggiungi altri campi come 'hp', 'types', 'attacks', ecc., se necessari
}
