package UmbertoAmoroso.PokeCardCollector.services;

import UmbertoAmoroso.PokeCardCollector.controllers.PokemonCardDTO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class PokemonCardService {

    @Value("${APICARD_URL}")
    private String apiPokemonUrl;

    @Value("${APICARD_KEY}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    // Metodo per cercare carte Pokémon basato su una query
    public List<PokemonCardDTO> searchPokemonCards(String query, int page, int pageSize) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Api-Key", apiKey);  // Aggiunta dell'API key negli headers

        String url = apiPokemonUrl + "/cards?q=" + query + "&page=" + page + "&pageSize=" + pageSize;
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<PokemonCardResponse> response = restTemplate.exchange(url, HttpMethod.GET, entity, PokemonCardResponse.class);

        // Check for null response body and return empty list if necessary
        return Optional.ofNullable(response.getBody())
                .map(PokemonCardResponse::getData)
                .orElse(List.of()); // Return an empty list if data is null
    }

    // Metodo per ottenere i dettagli di una singola carta tramite ID
    public Optional<PokemonCardDTO> getPokemonCardById(String cardId) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Api-Key", apiKey);

        String url = apiPokemonUrl + "/cards/" + cardId;
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<PokemonCardResponse> response = restTemplate.exchange(url, HttpMethod.GET, entity, PokemonCardResponse.class);

        // Use Optional to safely retrieve the first card or empty if no result
        return Optional.ofNullable(response.getBody())
                .map(PokemonCardResponse::getData)
                .stream()
                .flatMap(List::stream)
                .findFirst();  // Return the first card if available
    }

    // Classe di supporto per gestire la risposta JSON dell'API Pokémon
    @Setter
    @Getter
    public static class PokemonCardResponse {
        private List<PokemonCardDTO> data;
    }
}
