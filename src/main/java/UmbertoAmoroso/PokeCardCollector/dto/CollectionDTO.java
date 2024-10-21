package UmbertoAmoroso.PokeCardCollector.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CollectionDTO {
    private Long id;
    private String name;
    private List<CardDTO> cards;
}
