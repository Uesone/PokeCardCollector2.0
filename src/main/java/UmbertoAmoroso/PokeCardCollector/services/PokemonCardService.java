package UmbertoAmoroso.PokeCardCollector.services;

import UmbertoAmoroso.PokeCardCollector.dto.PokemonCardDTO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class PokemonCardService {

    @Value("${external.api.url}")
    private String apiPokemonUrl;

    @Value("${external.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    // Metodo per cercare carte Pokémon basato su una query
    public List<PokemonCardDTO> searchPokemonCards(String query, int page, int pageSize) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Api-Key", apiKey);  // Aggiunta dell'API key negli headers

        // Formatta la query per assicurarsi che segua la sintassi corretta per la ricerca
        String url = apiPokemonUrl + "/cards?q=name:" + query + "&page=" + page + "&pageSize=" + pageSize;
        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            System.out.println("Calling URL: " + url);  // Log per vedere l'URL chiamato
            ResponseEntity<PokemonCardResponse> response = restTemplate.exchange(url, HttpMethod.GET, entity, PokemonCardResponse.class);
            return Optional.ofNullable(response.getBody())
                    .map(PokemonCardResponse::getData)
                    .orElse(List.of()); // Ritorna una lista vuota se data è null
        } catch (HttpClientErrorException e) {
            System.err.println("Errore nella ricerca delle carte: " + e.getMessage());
            return List.of();  // Ritorna una lista vuota in caso di errore
        }
    }

    // Classe di supporto per gestire la risposta JSON dell'API Pokémon
    @Setter
    @Getter
    public static class PokemonCardResponse {
        private List<PokemonCardDTO> data;
    }
}
