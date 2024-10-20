package UmbertoAmoroso.PokeCardCollector.services;

import UmbertoAmoroso.PokeCardCollector.dto.PokemonCardApiResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ExternalCardService {

    @Value("${APICARD_URL}")
    private String apiUrl;

    @Value("${APICARD_KEY}")
    private String apiKey;

    public PokemonCardApiResponse searchCards(String query) {
        String url = apiUrl + "?q=" + query;

        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(url, PokemonCardApiResponse.class);
    }
}
