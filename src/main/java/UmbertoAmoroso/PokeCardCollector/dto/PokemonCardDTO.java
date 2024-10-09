package UmbertoAmoroso.PokeCardCollector.dto;

import UmbertoAmoroso.PokeCardCollector.entities.CollezioneCarta;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class PokemonCardDTO {
    private String name;
    private String imageUrl;
    private String set;
    private String rarity;
    private UUID id;
    private String apiId;
    private int quantity;
    private boolean holo;
    private String condition;

    // Costruttore per dati dell'API
    public PokemonCardDTO(String name, String imageUrl, String set, String rarity, UUID id, String apiId, int quantity, boolean holo, String condition) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.set = set;
        this.rarity = rarity;
        this.id = id;
        this.apiId = apiId;
        this.quantity = quantity;
        this.holo = holo;
        this.condition = condition;
    }

    // Costruttore per combinare CollezioneCarta e dati dell'API
    public PokemonCardDTO(CollezioneCarta collezioneCarta, PokemonCardDTO apiData) {
        this.id = collezioneCarta.getId();
        this.apiId = collezioneCarta.getApiId();
        this.quantity = collezioneCarta.getQuantity();
        this.holo = collezioneCarta.holo();
        this.condition = collezioneCarta.getCondition();

        // Dati provenienti dall'API
        this.name = apiData.getName();
        this.imageUrl = apiData.getImageUrl();
        this.set = apiData.getSet();
        this.rarity = apiData.getRarity();
    }
}
