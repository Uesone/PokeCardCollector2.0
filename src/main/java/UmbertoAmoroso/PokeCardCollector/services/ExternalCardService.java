package UmbertoAmoroso.PokeCardCollector.services;

import UmbertoAmoroso.PokeCardCollector.dto.CardDTO;
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
import org.springframework.web.client.HttpClientErrorException;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Service
public class ExternalCardService {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("$${APICARD_URL}")
    private String apiUrl;

    @Value("${APICARD_KEY}")
    private String apiKey;

    public CardDTO fetchCardDetails(String cardId) {
        try {
            // Imposta l'URL completo con l'ID della carta
            String url = apiUrl + "/cards/" + cardId;

            // Crea gli headers per includere la chiave API
            HttpHeaders headers = new HttpHeaders();
            headers.set("X-Api-Key", apiKey);

            // Crea una HttpEntity per la richiesta con headers
            HttpEntity<String> entity = new HttpEntity<>(headers);

            // Esegui la chiamata GET con RestTemplate
            ResponseEntity<CardDTO> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    CardDTO.class
            );

            // Restituisci il body della risposta (la card)
            return response.getBody();

        } catch (HttpClientErrorException e) {
            // Gestisci l'errore nel caso in cui la richiesta fallisca
            System.err.println("Errore nella richiesta: " + e.getStatusCode());
            return null;
        }
    }
}
