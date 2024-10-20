package UmbertoAmoroso.PokeCardCollector.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter



public class CollezioneDTO {
    // Getters and Setters
    private String name;
    private String description;
    private List<CollezioneCartaDTO> cards;

}
