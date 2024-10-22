package UmbertoAmoroso.PokeCardCollector.controllers;

import UmbertoAmoroso.PokeCardCollector.dto.PokemonCardDTO;
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
                    PokemonCardDTO detailedCard = new PokemonCardDTO();
                    detailedCard.setId(card.getId());
                    detailedCard.setName(card.getName());
                    detailedCard.setImages(card.getImages());
                    detailedCard.setHp(card.getHp());
                    detailedCard.setRarity(card.getRarity());
                    detailedCard.setArtist(card.getArtist());

                    // Aggiungi i tipi della carta
                    detailedCard.setTypes(card.getTypes());

                    // Aggiungi il set, se presente
                    if (card.getSet() != null) {
                        PokemonCardDTO.SetDetail setDetail = new PokemonCardDTO.SetDetail();
                        setDetail.setId(card.getSet().getId());
                        setDetail.setName(card.getSet().getName());
                        setDetail.setSeries(card.getSet().getSeries());
                        setDetail.setPrintedTotal(card.getSet().getPrintedTotal());
                        setDetail.setReleaseDate(card.getSet().getReleaseDate());
                        detailedCard.setSet(setDetail); // Assegna il set
                    }

                    // Aggiungi i prezzi di mercato, se disponibili
                    if (card.getTcgplayer() != null && card.getTcgplayer().getPrices() != null) {
                        PokemonCardDTO.TcgPlayer.Prices prices = card.getTcgplayer().getPrices();
                        detailedCard.setTcgplayer(new PokemonCardDTO.TcgPlayer());
                        detailedCard.getTcgplayer().setPrices(new PokemonCardDTO.TcgPlayer.Prices());

                        if (prices.getNormal() != null) {
                            detailedCard.getTcgplayer().getPrices().setNormal(prices.getNormal());
                        }
                        if (prices.getHolofoil() != null) {
                            detailedCard.getTcgplayer().getPrices().setHolofoil(prices.getHolofoil());
                        }
                        if (prices.getReverseHolofoil() != null) {
                            detailedCard.getTcgplayer().getPrices().setReverseHolofoil(prices.getReverseHolofoil());
                        }
                    }

                    return detailedCard;
                })
                .collect(Collectors.toList());
    }
}
