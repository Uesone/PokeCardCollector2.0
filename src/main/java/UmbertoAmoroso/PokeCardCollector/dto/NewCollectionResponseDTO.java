package UmbertoAmoroso.PokeCardCollector.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor


public class NewCollectionResponseDTO {
    // Getters and Setters
    private String message;
    private boolean success;

    public NewCollectionResponseDTO(UUID id, String name) {
    }

}
