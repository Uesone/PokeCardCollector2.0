package UmbertoAmoroso.PokeCardCollector.dto;

import UmbertoAmoroso.PokeCardCollector.entities.Collezione;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CollezioneDTO {
    private UUID id;
    private String name;
    private String description;
    private List<CollezioneCartaDTO> carte;

    // Costruttore che accetta un oggetto Collezione e lo converte in CollezioneDTO
    public CollezioneDTO(Collezione collezione) {
        this.id = collezione.getId();
        this.name = collezione.getName();
        this.description = collezione.getDescription();
        this.carte = collezione.getCarte().stream()
                .map(CollezioneCartaDTO::new)
                .collect(Collectors.toList());
    }
}
