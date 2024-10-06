package UmbertoAmoroso.PokeCardCollector.controllers;

import UmbertoAmoroso.PokeCardCollector.dto.NewCardDTO;
import UmbertoAmoroso.PokeCardCollector.dto.NewCollectionDTO;
import UmbertoAmoroso.PokeCardCollector.dto.NewCollectionResponseDTO;
import UmbertoAmoroso.PokeCardCollector.entities.Collezione;
import UmbertoAmoroso.PokeCardCollector.entities.CollezioneCarta;
import UmbertoAmoroso.PokeCardCollector.entities.Utente;
import UmbertoAmoroso.PokeCardCollector.services.CollectionService;
import UmbertoAmoroso.PokeCardCollector.services.UserService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
@Getter
@Setter

@RestController
@RequestMapping("/user/backoffice/collections")
public class UserBackOfficeController {

    @Autowired
    private CollectionService collectionService;

    @Autowired
    private UserService userService;

    // Creazione di una nuova collezione
    @PostMapping("")
    public ResponseEntity<NewCollectionResponseDTO> createCollection(@RequestBody NewCollectionDTO newCollectionDTO, Authentication authentication) {
        Utente utente = userService.getCurrentUser(authentication);
        Collezione newCollection = collectionService.save(newCollectionDTO, utente);
        return ResponseEntity.ok(new NewCollectionResponseDTO(newCollection.getId()));
    }

    // Recupera tutte le collezioni dell'utente autenticato
    @GetMapping("")
    public ResponseEntity<List<Collezione>> getUserCollections(Authentication authentication) {
        Utente utente = userService.getCurrentUser(authentication);
        List<Collezione> collections = collectionService.getCollectionsByUser(utente.getId());
        return ResponseEntity.ok(collections);
    }

    // Aggiungi una carta a una collezione
    @PostMapping("/{collectionId}/addCard")
    public ResponseEntity<CollezioneCarta> addCardToCollection(
            @PathVariable UUID collectionId,
            @RequestBody NewCardDTO cardDTO,
            Authentication authentication) {
        Utente utente = userService.getCurrentUser(authentication);
        CollezioneCarta addedCard = collectionService.addCardToCollection(collectionId, cardDTO, utente);
        return ResponseEntity.ok(addedCard);
    }

    // Rimuovi una carta da una collezione
    @DeleteMapping("/{collectionId}/removeCard/{cardId}")
    public ResponseEntity<Void> removeCardFromCollection(
            @PathVariable UUID collectionId,
            @PathVariable UUID cardId,
            Authentication authentication) {
        Utente utente = userService.getCurrentUser(authentication);
        collectionService.removeCardFromCollection(collectionId, cardId, utente);
        return ResponseEntity.noContent().build();
    }

    // Visualizza tutte le carte in una collezione
    @GetMapping("/{collectionId}/cards")
    public ResponseEntity<List<CollezioneCarta>> getCardsInCollection(
            @PathVariable UUID collectionId,
            Authentication authentication) {
        Utente utente = userService.getCurrentUser(authentication);
        List<CollezioneCarta> cards = collectionService.getCardsInCollection(collectionId, utente);
        return ResponseEntity.ok(cards);
    }

    // Aggiorna una collezione esistente
    @PutMapping("/{collectionId}")
    public ResponseEntity<Collezione> updateCollection(
            @PathVariable UUID collectionId,
            @RequestBody NewCollectionDTO newCollectionDTO,
            Authentication authentication) {
        Utente utente = userService.getCurrentUser(authentication);
        Collezione updatedCollection = collectionService.updateCollection(collectionId, newCollectionDTO, utente);
        return ResponseEntity.ok(updatedCollection);
    }

    // Elimina una collezione
    @DeleteMapping("/{collectionId}")
    public ResponseEntity<Void> deleteCollection(
            @PathVariable UUID collectionId,
            Authentication authentication) {
        Utente utente = userService.getCurrentUser(authentication);
        collectionService.deleteCollection(collectionId, utente);
        return ResponseEntity.noContent().build();
    }
}
