package UmbertoAmoroso.PokeCardCollector.controllers;

import UmbertoAmoroso.PokeCardCollector.dto.CollectionDTO;
import UmbertoAmoroso.PokeCardCollector.entities.User;
import UmbertoAmoroso.PokeCardCollector.services.CollectionService;
import UmbertoAmoroso.PokeCardCollector.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/collections")
public class CollectionController {

    @Autowired
    private CollectionService collectionService;

    @Autowired
    private UserService userService;

    // Ottiene le collezioni per l'utente autenticato
    @GetMapping("/user")
    public List<CollectionDTO> getUserCollections(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findByUsername(userDetails.getUsername()); // Ottieni l'utente autenticato
        return collectionService.getUserCollections(user.getId());  // Passa l'ID dell'utente al servizio
    }

    // Crea una nuova collezione per l'utente autenticato
    @PostMapping("/user")
    public CollectionDTO createCollection(@RequestBody CollectionDTO collectionDTO, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findByUsername(userDetails.getUsername()); // Ottieni l'utente autenticato
        return collectionService.createCollection(user.getId(), collectionDTO.getName());  // Passa l'ID dell'utente e il nome della collezione al servizio
    }

    // Aggiunge una carta a una collezione specifica
    @PostMapping("/{collectionId}/addCard")
    public void addCardToCollection(
            @PathVariable Long collectionId,
            @RequestBody Map<String, String> cardData,
            @AuthenticationPrincipal UserDetails userDetails) {
        String cardId = cardData.get("cardId");
        String imageUrl = cardData.get("imageUrl");

        User user = userService.findByUsername(userDetails.getUsername()); // Verifica l'utente autenticato
        collectionService.addCardToCollection(user.getId(), collectionId, cardId, imageUrl);  // Associa l'utente alla collezione
    }

    // Elimina una collezione specifica
    @DeleteMapping("/{collectionId}")
    public void deleteCollection(@PathVariable Long collectionId, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findByUsername(userDetails.getUsername()); // Verifica l'utente autenticato
        collectionService.deleteCollection(user.getId(), collectionId);  // Associa l'utente alla collezione
    }

    // Rimuove una carta da una collezione specifica
    @DeleteMapping("/{collectionId}/removeCard")
    public void deleteCardFromCollection(
            @PathVariable Long collectionId,
            @RequestParam String cardId,
            @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findByUsername(userDetails.getUsername()); // Verifica l'utente autenticato
        collectionService.deleteCardFromCollection(user.getId(), collectionId, cardId);  // Associa l'utente alla collezione
    }
}
