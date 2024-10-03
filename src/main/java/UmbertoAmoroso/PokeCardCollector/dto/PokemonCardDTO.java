package UmbertoAmoroso.PokeCardCollector.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class PokemonCardDTO {
    private String id;
    private String name;
    private String imageUrl;
    private String set;
    private String rarity;


}
