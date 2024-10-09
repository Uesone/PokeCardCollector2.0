package UmbertoAmoroso.PokeCardCollector.controllers;

import UmbertoAmoroso.PokeCardCollector.dto.NewCardDTO;
import UmbertoAmoroso.PokeCardCollector.dto.PokemonCardDTO;
import UmbertoAmoroso.PokeCardCollector.entities.Utente;
import UmbertoAmoroso.PokeCardCollector.services.CollectionService;
import UmbertoAmoroso.PokeCardCollector.services.PokemonCardService;
import UmbertoAmoroso.PokeCardCollector.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/user/backoffice/cards")
public class UserCardController {

    @Autowired
    private PokemonCardService pokemonCardService;

    @Autowired
    private CollectionService collectionService;

    @Autowired
    private UserService userService;


    // Ricerca carte per nome con opzione holo
    @GetMapping("/search")
    public ResponseEntity<List<PokemonCardDTO>> searchCardsByName(
            @RequestParam String name,
            @RequestParam(required = false, defaultValue = "false") boolean holo) {
        List<PokemonCardDTO> cards = pokemonCardService.searchCardsByName(name, holo);
        return ResponseEntity.ok(cards);
    }



    // Aggiungi una carta a una collezione
    @PostMapping("/collections/{collectionId}/add")
    public ResponseEntity<?> addCardToCollection(@PathVariable UUID collectionId,
                                                 @RequestParam String cardId,
                                                 @RequestParam int quantity,
                                                 @RequestParam boolean holo,
                                                 @RequestParam String condition,
                                                 Authentication authentication) {
        Utente utente = userService.getCurrentUser(authentication);

        // Crea un oggetto NewCardDTO con tutti i parametri
        NewCardDTO newCardDTO = new NewCardDTO(cardId, holo, quantity, condition);

        // Passa il NewCardDTO al servizio
        return ResponseEntity.ok(collectionService.addCardToCollection(collectionId, newCardDTO, utente));
    }
}
