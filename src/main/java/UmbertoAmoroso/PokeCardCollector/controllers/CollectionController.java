package UmbertoAmoroso.PokeCardCollector.controllers;

import UmbertoAmoroso.PokeCardCollector.dto.NewCollectionDTO;
import UmbertoAmoroso.PokeCardCollector.dto.NewCollectionResponseDTO;
import UmbertoAmoroso.PokeCardCollector.entities.Collezione;
import UmbertoAmoroso.PokeCardCollector.entities.Utente;
import UmbertoAmoroso.PokeCardCollector.services.CollectionService;
import UmbertoAmoroso.PokeCardCollector.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/collections")
public class CollectionController {

    @Autowired
    private CollectionService collectionService;

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<NewCollectionResponseDTO> createCollection(@RequestBody NewCollectionDTO newCollectionDTO) {
        Utente currentUser = userService.getCurrentUser();
        Collezione newCollection = collectionService.save(newCollectionDTO, currentUser);
        return ResponseEntity.ok(new NewCollectionResponseDTO(newCollection.getId(), newCollection.getName()));
    }

    @GetMapping("/user")
    public ResponseEntity<List<Collezione>> getUserCollections() {
        Utente currentUser = userService.getCurrentUser();
        List<Collezione> collections = collectionService.getCollectionsByUser(currentUser.getId());
        return ResponseEntity.ok(collections);
    }

    @DeleteMapping("/{collectionId}")
    public ResponseEntity<Void> deleteCollection(@PathVariable UUID collectionId) {
        Utente currentUser = userService.getCurrentUser();
        collectionService.deleteCollection(collectionId, currentUser);
        return ResponseEntity.noContent().build();
    }
}
