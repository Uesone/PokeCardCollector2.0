package UmbertoAmoroso.PokeCardCollector.controllers;

import UmbertoAmoroso.PokeCardCollector.services.PokemonCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/pokemon")
public class PokemonCardController {

    @Autowired
    private PokemonCardService pokemonCardService;

    @GetMapping("/search")
    public List<PokemonCardDTO> searchCards(
            @RequestParam String query,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int pageSize) {
        List<PokemonCardDTO> cards = pokemonCardService.searchPokemonCards(query, page, pageSize);
        return cards.stream()
                .map(card -> {
                    PokemonCardDTO minimalCard = new PokemonCardDTO();
                    minimalCard.setId(card.getId());
                    minimalCard.setName(card.getName());
                    minimalCard.setImages(card.getImages());
                    return minimalCard;
                })
                .collect(Collectors.toList());
    }
}