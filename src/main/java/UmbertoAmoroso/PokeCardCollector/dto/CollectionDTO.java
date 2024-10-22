package UmbertoAmoroso.PokeCardCollector.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CollectionDTO {
    private Long id;
    private String name;  // Campo per il nome della collezione
    private List<CardDTO> cards;
}
