package UmbertoAmoroso.PokeCardCollector.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class PokemonCardApiResponse {
    // Getters and Setters
    private List<PokemonCardData> data;

}
