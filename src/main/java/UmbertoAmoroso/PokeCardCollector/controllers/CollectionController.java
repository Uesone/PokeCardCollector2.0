package UmbertoAmoroso.PokeCardCollector.controllers;

import UmbertoAmoroso.PokeCardCollector.dto.NewCollectionDTO;
import UmbertoAmoroso.PokeCardCollector.dto.NewCollectionResponseDTO;
import UmbertoAmoroso.PokeCardCollector.entities.Collezione;
import UmbertoAmoroso.PokeCardCollector.entities.Utente;
import UmbertoAmoroso.PokeCardCollector.services.CollectionService;
import UmbertoAmoroso.PokeCardCollector.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/collections")
public class CollectionController {

    @Autowired
    private CollectionService collectionService;

    @Autowired
    private UserService userService; // Service per recuperare l'utente autenticato

    // Crea una nuova collezione
    @PostMapping("")
    public NewCollectionResponseDTO saveCollection(@RequestBody NewCollectionDTO body, Authentication authentication) {
        Utente utente = userService.getCurrentUser(authentication); // Recupera l'utente dal token JWT
        Collezione newCollection = collectionService.save(body, utente);
        return new NewCollectionResponseDTO(newCollection.getId());
    }

    // Visualizza tutte le collezioni dell'utente autenticato
    @GetMapping("")
    public List<Collezione> getCollections(Authentication authentication) {
        Utente utente = userService.getCurrentUser(authentication); // Recupera l'utente autenticato
        return collectionService.getCollectionsByUser(utente.getId());
    }

    // Aggiorna una collezione esistente
    @PutMapping("/{collectionId}")
    public Collezione updateCollection(@PathVariable UUID collectionId, @RequestBody NewCollectionDTO body, Authentication authentication) {
        Utente utente = userService.getCurrentUser(authentication); // Recupera l'utente autenticato
        return collectionService.updateCollection(collectionId, body, utente);
    }

    // Cancella una collezione
    @DeleteMapping("/{collectionId}")
    public void deleteCollection(@PathVariable UUID collectionId, Authentication authentication) {
        Utente utente = userService.getCurrentUser(authentication); // Recupera l'utente autenticato
        collectionService.deleteCollection(collectionId, utente);
    }
}
