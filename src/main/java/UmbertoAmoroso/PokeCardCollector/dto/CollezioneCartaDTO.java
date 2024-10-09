package UmbertoAmoroso.PokeCardCollector.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CollezioneCartaDTO {
    private UUID id;
    private String apiId;
    private int quantity;
    private boolean isHolo;
    private String condition;
}
