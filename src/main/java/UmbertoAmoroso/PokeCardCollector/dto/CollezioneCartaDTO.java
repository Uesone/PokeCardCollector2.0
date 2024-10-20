package UmbertoAmoroso.PokeCardCollector.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class CollezioneCartaDTO {
    // Getters and Setters
    private String apiId;
    private int quantity;
    private boolean holo;
    private String condition;

}
