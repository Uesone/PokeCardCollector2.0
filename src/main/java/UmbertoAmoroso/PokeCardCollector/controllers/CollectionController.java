package UmbertoAmoroso.PokeCardCollector.controllers;

import UmbertoAmoroso.PokeCardCollector.dto.CollezioneDTO;
import UmbertoAmoroso.PokeCardCollector.dto.NewCollectionDTO;
import UmbertoAmoroso.PokeCardCollector.dto.NewCollectionResponseDTO;
import UmbertoAmoroso.PokeCardCollector.entities.Collezione;
import UmbertoAmoroso.PokeCardCollector.entities.Utente;
import UmbertoAmoroso.PokeCardCollector.services.CollectionService;
import UmbertoAmoroso.PokeCardCollector.services.UserService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@RestController
@RequestMapping("/collections")
public class CollectionController {

    @Autowired
    private CollectionService collectionService;

    @Autowired
    private UserService userService;

    @PostMapping("")
    public NewCollectionResponseDTO saveCollection(@RequestBody NewCollectionDTO body, Authentication authentication) {
        Utente utente = userService.getCurrentUser(authentication);
        Collezione newCollection = collectionService.save(body, utente);
        return new NewCollectionResponseDTO(newCollection.getId());
    }

    @GetMapping("")
    public List<CollezioneDTO> getCollections(Authentication authentication) {
        Utente utente = userService.getCurrentUser(authentication);
        return collectionService.getCollectionsByUser(utente.getId())
                .stream()
                .map(CollezioneDTO::new)
                .collect(Collectors.toList());
    }

    @PutMapping("/{collectionId}")
    public CollezioneDTO updateCollection(@PathVariable UUID collectionId, @RequestBody NewCollectionDTO body, Authentication authentication) {
        Utente utente = userService.getCurrentUser(authentication);
        Collezione updatedCollection = collectionService.updateCollection(collectionId, body, utente);
        return new CollezioneDTO(updatedCollection);
    }

    @DeleteMapping("/{collectionId}")
    public void deleteCollection(@PathVariable UUID collectionId, Authentication authentication) {
        Utente utente = userService.getCurrentUser(authentication);
        collectionService.deleteCollection(collectionId, utente);
    }
}

