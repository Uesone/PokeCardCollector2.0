package UmbertoAmoroso.PokeCardCollector.services;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;

import UmbertoAmoroso.PokeCardCollector.dto.PokemonCardDTO;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Service
public class PokemonCardService {

    @Value("${APICARD_URL}")
    private String pokemonApiUrl;

    @Value("${APICARD_KEY}")
    private String pokemonApiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    public List<PokemonCardDTO> searchCardsByName(String name) {
        String url = pokemonApiUrl + "/cards?q=name:" + name;

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Api-Key", pokemonApiKey);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<PokemonCardDTO[]> response = restTemplate.exchange(
                url, HttpMethod.GET, entity, PokemonCardDTO[].class
        );

        return Arrays.asList(response.getBody());
    }

    public PokemonCardDTO getCardById(String cardId) {
        String url = pokemonApiUrl + "/cards/" + cardId;

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Api-Key", pokemonApiKey);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<PokemonCardDTO> response = restTemplate.exchange(
                url, HttpMethod.GET, entity, PokemonCardDTO.class
        );

        return response.getBody();
    }
}
