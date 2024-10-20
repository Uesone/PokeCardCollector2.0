package UmbertoAmoroso.PokeCardCollector.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter

@AllArgsConstructor
@NoArgsConstructor

public class CardDTO {
    // Getters and Setters
    private String apiId;
    private String name;
    private String set;
    private String rarity;
    private String imageUrl;
    private boolean holo;

}
