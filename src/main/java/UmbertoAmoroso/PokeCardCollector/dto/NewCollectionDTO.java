package UmbertoAmoroso.PokeCardCollector.dto;

import jakarta.validation.constraints.NotEmpty;



public record NewCollectionDTO(
        @NotEmpty String name,
        @NotEmpty String description
) {}