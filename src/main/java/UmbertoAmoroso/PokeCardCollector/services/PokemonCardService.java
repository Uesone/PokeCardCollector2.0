package UmbertoAmoroso.PokeCardCollector.services;

import UmbertoAmoroso.PokeCardCollector.dto.PokemonCardDTO;
import UmbertoAmoroso.PokeCardCollector.dto.PokemonCardApiResponse;
import UmbertoAmoroso.PokeCardCollector.dto.PokemonCardData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PokemonCardService {

    @Autowired
    private ExternalCardService externalCardService;

    public List<PokemonCardDTO> searchCardsByName(String name, boolean isHolo) {
        String query = "name:" + name;
        if (isHolo) {
            query += " holo:true";
        }

        PokemonCardApiResponse response = externalCardService.searchCards(query);

        // Converti la lista di PokemonCardData in PokemonCardDTO
        return response.getData().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Metodo per convertire PokemonCardData in PokemonCardDTO
    private PokemonCardDTO convertToDTO(PokemonCardData cardData) {
        PokemonCardDTO cardDTO = new PokemonCardDTO();
        cardDTO.setId(cardData.getId());
        cardDTO.setName(cardData.getName());
        cardDTO.setSupertype(cardData.getSupertype());
        cardDTO.setSubtypes(cardData.getSubtypes());
        cardDTO.setHp(cardData.getHp());
        cardDTO.setTypes(cardData.getTypes());
        cardDTO.setEvolvesTo(cardData.getEvolvesTo());
        cardDTO.setRules(cardData.getRules());
        cardDTO.setAttacks(cardData.getAttacks());
        cardDTO.setWeaknesses(cardData.getWeaknesses());
        cardDTO.setRetreatCost(cardData.getRetreatCost());
        cardDTO.setConvertedRetreatCost(cardData.getConvertedRetreatCost());
        cardDTO.setSet(cardData.getSet());
        cardDTO.setNumber(cardData.getNumber());
        cardDTO.setArtist(cardData.getArtist());
        cardDTO.setRarity(cardData.getRarity());
        cardDTO.setNationalPokedexNumbers(cardData.getNationalPokedexNumbers());
        cardDTO.setLegalities(cardData.getLegalities());
        cardDTO.setImages(cardData.getImages());
        cardDTO.setTcgplayer(cardData.getTcgplayer());
        return cardDTO;
    }
}
