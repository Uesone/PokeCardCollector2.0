package UmbertoAmoroso.PokeCardCollector.dto;

import UmbertoAmoroso.PokeCardCollector.entities.CollezioneCarta;
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
    private boolean IsHolo;
    private String condition;

    // Costruttore che accetta un oggetto CollezioneCarta
    public CollezioneCartaDTO(CollezioneCarta collezioneCarta) {
        this.id = collezioneCarta.getId();
        this.apiId = collezioneCarta.getApiId();
        this.quantity = collezioneCarta.getQuantity();
        this.IsHolo = collezioneCarta.holo();
        this.condition = collezioneCarta.getCondition();
    }
}
