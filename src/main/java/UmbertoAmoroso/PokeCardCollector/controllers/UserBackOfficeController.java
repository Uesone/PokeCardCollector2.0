package UmbertoAmoroso.PokeCardCollector.controllers;

import UmbertoAmoroso.PokeCardCollector.dto.*;
import UmbertoAmoroso.PokeCardCollector.entities.Collezione;
import UmbertoAmoroso.PokeCardCollector.entities.CollezioneCarta;
import UmbertoAmoroso.PokeCardCollector.entities.Utente;
import UmbertoAmoroso.PokeCardCollector.services.CollectionService;
import UmbertoAmoroso.PokeCardCollector.services.PokemonCardService;
import UmbertoAmoroso.PokeCardCollector.services.UserService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@Setter
@RestController
@RequestMapping("/user/backoffice/collections")
public class UserBackOfficeController {

    @Autowired
    private CollectionService collectionService;

    @Autowired
    private UserService userService;

    @Autowired
    private PokemonCardService pokemonCardService;

    @PostMapping("")
    public ResponseEntity<NewCollectionResponseDTO> createCollection(@RequestBody NewCollectionDTO newCollectionDTO, Authentication authentication) {
        Utente utente = userService.getCurrentUser(authentication);
        Collezione newCollection = collectionService.save(newCollectionDTO, utente);
        return ResponseEntity.ok(new NewCollectionResponseDTO(newCollection.getId()));
    }

    @GetMapping("")
    public ResponseEntity<List<CollezioneDTO>> getUserCollections(Authentication authentication) {
        Utente utente = userService.getCurrentUser(authentication);
        List<CollezioneDTO> collections = collectionService.getCollectionsByUser(utente.getId())
                .stream()
                .map(CollezioneDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(collections);
    }

    @PostMapping("/{collectionId}/addCard")
    public ResponseEntity<PokemonCardDTO> addCardToCollection(
            @PathVariable UUID collectionId,
            @RequestBody NewCardDTO cardDTO,
            Authentication authentication) {
        Utente utente = userService.getCurrentUser(authentication);
        PokemonCardDTO addedCard = collectionService.addCardToCollection(collectionId, cardDTO, utente);
        // Aggiungi qui un costruttore con tutti i campi per istanziare un oggetto PokemonCardDTO
        return ResponseEntity.ok(new PokemonCardDTO(
                addedCard.getName(), // Questo dipende dalla tua implementazione
                addedCard.getImageUrl(),
                addedCard.getSet(),
                addedCard.getRarity(),
                addedCard.getId(),
                addedCard.getApiId(),
                addedCard.getQuantity(),
                addedCard.isHolo(),
                addedCard.getCondition()
        ));
    }

    @DeleteMapping("/{collectionId}/removeCard/{cardId}")
    public ResponseEntity<Void> removeCardFromCollection(
            @PathVariable UUID collectionId,
            @PathVariable UUID cardId,
            Authentication authentication) {
        Utente utente = userService.getCurrentUser(authentication);
        collectionService.removeCardFromCollection(collectionId, cardId, utente);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{collectionId}/cards")
    public ResponseEntity<List<PokemonCardDTO>> getCardsInCollection(
            @PathVariable UUID collectionId,
            Authentication authentication) {
        Utente utente = userService.getCurrentUser(authentication);

        List<CollezioneCarta> carte = collectionService.getCardsInCollection(collectionId, utente);
        List<PokemonCardDTO> cards = carte.stream()
                .map(card -> {
                    // Recupera i dettagli dall'API
                    PokemonCardDTO apiData = pokemonCardService.getCardById(card.getApiId(), card.holo());
                    // Usa il nuovo costruttore di `PokemonCardDTO`
                    return new PokemonCardDTO(card, apiData);
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(cards);
    }


    @PutMapping("/{collectionId}")
    public ResponseEntity<CollezioneDTO> updateCollection(
            @PathVariable UUID collectionId,
            @RequestBody NewCollectionDTO newCollectionDTO,
            Authentication authentication) {
        Utente utente = userService.getCurrentUser(authentication);
        Collezione updatedCollection = collectionService.updateCollection(collectionId, newCollectionDTO, utente);
        return ResponseEntity.ok(new CollezioneDTO(updatedCollection));
    }

    @DeleteMapping("/{collectionId}")
    public ResponseEntity<Void> deleteCollection(
            @PathVariable UUID collectionId,
            Authentication authentication) {
        Utente utente = userService.getCurrentUser(authentication);
        collectionService.deleteCollection(collectionId, utente);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/search")
    public ResponseEntity<List<PokemonCardDTO>> searchCardsByName(
            @RequestParam String name,
            @RequestParam(required = false, defaultValue = "false") boolean holo) {
        List<PokemonCardDTO> cards = pokemonCardService.searchCardsByName(name, holo);
        return ResponseEntity.ok(cards);
    }

}
