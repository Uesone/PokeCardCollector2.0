package UmbertoAmoroso.PokeCardCollector.controllers;

import UmbertoAmoroso.PokeCardCollector.dto.NewCollectionDTO;
import UmbertoAmoroso.PokeCardCollector.dto.NewCollectionResponseDTO;
import UmbertoAmoroso.PokeCardCollector.entities.Collezione;
import UmbertoAmoroso.PokeCardCollector.entities.Utente;
import UmbertoAmoroso.PokeCardCollector.services.CollectionService;
import UmbertoAmoroso.PokeCardCollector.services.UserService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping("")
    public NewCollectionResponseDTO saveCollection(@RequestBody NewCollectionDTO body, Authentication authentication) {
        Utente utente = userService.getCurrentUser(authentication);
        return new NewCollectionResponseDTO(collectionService.save(body, utente).getId());
    }

    @GetMapping("")
    public List<Collezione> getCollections(Authentication authentication) {
        Utente utente = userService.getCurrentUser(authentication);
        return collectionService.getCollectionsByUser(utente.getId());
    }

    @DeleteMapping("/{collectionId}")
    public void deleteCollection(@PathVariable UUID collectionId, Authentication authentication) {
        Utente utente = userService.getCurrentUser(authentication);
        collectionService.deleteCollection(collectionId, utente);
    }
}
