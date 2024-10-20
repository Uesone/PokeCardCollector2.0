package UmbertoAmoroso.PokeCardCollector.controllers;

import UmbertoAmoroso.PokeCardCollector.dto.NewCardDTO;
import UmbertoAmoroso.PokeCardCollector.dto.PokemonCardDTO;
import UmbertoAmoroso.PokeCardCollector.entities.Utente;
import UmbertoAmoroso.PokeCardCollector.services.CollectionService;
import UmbertoAmoroso.PokeCardCollector.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/user/cards")
public class UserCardController {

    @Autowired
    private CollectionService collectionService;

    @Autowired
    private UserService userService;

    @PostMapping("/add/{collectionId}")
    public ResponseEntity<PokemonCardDTO> addCardToCollection(@PathVariable UUID collectionId,
                                                              @RequestBody NewCardDTO newCardDTO) {
        Utente currentUser = userService.getCurrentUser();

        // Conversione da NewCardDTO a PokemonCardDTO
        PokemonCardDTO pokemonCardDTO = new PokemonCardDTO();
        pokemonCardDTO.setName(newCardDTO.getName());
        pokemonCardDTO.setRarity(newCardDTO.getCondition()); // Mappiamo 'condition' con 'rarity'
        // Altri dati possono essere aggiunti dal servizio che interagisce con l'API

        PokemonCardDTO addedCard = collectionService.addCardToCollection(collectionId, pokemonCardDTO, currentUser);
        return ResponseEntity.ok(addedCard);
    }

    @DeleteMapping("/remove/{collectionId}/{cardId}")
    public ResponseEntity<Void> removeCardFromCollection(@PathVariable UUID collectionId,
                                                         @PathVariable UUID cardId) {
        Utente currentUser = userService.getCurrentUser();
        collectionService.removeCardFromCollection(collectionId, cardId, currentUser);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/collection/{collectionId}")
    public ResponseEntity<?> getCardsInCollection(@PathVariable UUID collectionId) {
        Utente currentUser = userService.getCurrentUser();
        return ResponseEntity.ok(collectionService.getCardsInCollection(collectionId, currentUser));
    }
}
