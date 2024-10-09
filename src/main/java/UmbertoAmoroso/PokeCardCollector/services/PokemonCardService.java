package UmbertoAmoroso.PokeCardCollector.services;

import UmbertoAmoroso.PokeCardCollector.dto.PokemonCardApiResponse;
import UmbertoAmoroso.PokeCardCollector.dto.PokemonCardDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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

    // Metodo che accetta l'ID della carta e la preferenza holo
// Nella classe PokemonCardService
    public PokemonCardDTO getCardById(String apiId, boolean isHolo) {
        String url = pokemonApiUrl + "/cards/" + apiId;

        if (isHolo) {
            url += "?q=isHolo:true";
        }

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Api-Key", pokemonApiKey);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<PokemonCardApiResponse> response = restTemplate.exchange(
                url, HttpMethod.GET, entity, PokemonCardApiResponse.class
        );

        // Processa i dati dalla risposta
        PokemonCardApiResponse cardApiResponse = response.getBody();

        if (cardApiResponse != null) {
            // Mappa i campi della risposta API
            return new PokemonCardDTO(
                    cardApiResponse.getData().getName(),
                    cardApiResponse.getData().getImages().getLarge(), // Assumendo che tu voglia l'immagine grande
                    cardApiResponse.getData().getSet().getName(),
                    cardApiResponse.getData().getRarity(),
                    UUID.randomUUID(),
                    apiId,
                    1, // Quantità di default
                    isHolo,
                    "Mint" // Condizione di default
            );
        }

        throw new RuntimeException("Card not found");
    }

    public List<PokemonCardDTO> searchCardsByName(String name, boolean isHolo) {
        String url = pokemonApiUrl + "/cards?q=name:" + name;
        if (isHolo) {
            url += "+isHolo:true";
        }

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Api-Key", pokemonApiKey);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        // Effettua la richiesta e ottieni la risposta
        ResponseEntity<PokemonCardApiResponse[]> response = restTemplate.exchange(
                url, HttpMethod.GET, entity, PokemonCardApiResponse[].class
        );

        PokemonCardApiResponse[] cardsData = response.getBody();

        if (cardsData != null && cardsData.length > 0) {
            return Arrays.stream(cardsData)
                    .map(card -> new PokemonCardDTO(
                            card.getData().getName(),
                            card.getData().getImages().getLarge(), // Assume che vuoi l'immagine grande
                            card.getData().getSet().getName(),
                            card.getData().getRarity(),
                            UUID.randomUUID(), // UUID per identificare l'oggetto locale
                            card.getData().getId(),
                            1, // Quantità di default, personalizzabile
                            isHolo,
                            "Mint" // Condizione di default, personalizzabile
                    ))
                    .collect(Collectors.toList());
        } else {
            throw new RuntimeException("No cards found with name: " + name);
        }
    }}

