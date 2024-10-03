package UmbertoAmoroso.PokeCardCollector.dto;

import jakarta.validation.constraints.NotEmpty;


public record NewCardDTO(
        @NotEmpty String apiId,
        int quantity,
        boolean foil,
        String condition
) {}