package UmbertoAmoroso.PokeCardCollector.controllers;

import UmbertoAmoroso.PokeCardCollector.dto.CollectionDTO;
import UmbertoAmoroso.PokeCardCollector.services.CollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/collections")
public class CollectionController {

    @Autowired
    private CollectionService collectionService;

    @GetMapping("/user/{userId}")
    public List<CollectionDTO> getUserCollections(@PathVariable Long userId) {
        return collectionService.getUserCollections(userId);
    }

    @PostMapping("/user/{userId}")
    public CollectionDTO createCollection(@PathVariable Long userId, @RequestBody CollectionDTO collectionDTO) {
        return collectionService.createCollection(userId, collectionDTO.getName());
    }


    @PostMapping("/{collectionId}/addCard")
    public void addCardToCollection(
            @PathVariable Long collectionId,
            @RequestBody Map<String, String> cardData) { // Usa @RequestBody per accettare JSON nel body
        String cardId = cardData.get("cardId");  // Ottieni cardId dal JSON
        String imageUrl = cardData.get("imageUrl");  // Ottieni imageUrl dal JSON

        collectionService.addCardToCollection(collectionId, cardId, imageUrl);
    }


    @DeleteMapping("/{collectionId}")
    public void deleteCollection(@PathVariable Long collectionId) {
        collectionService.deleteCollection(collectionId);
    }

    @DeleteMapping("/{collectionId}/removeCard")
    public void deleteCardFromCollection(
            @PathVariable Long collectionId,
            @RequestParam String cardId) {
        collectionService.deleteCardFromCollection(collectionId, cardId);
    }
}
