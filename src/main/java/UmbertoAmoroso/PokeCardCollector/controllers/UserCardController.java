package UmbertoAmoroso.PokeCardCollector.controllers;

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

    @GetMapping("/search")
    public List<PokemonCardDTO> searchCardsByName(@RequestParam String name) {
        return pokemonCardService.searchCardsByName(name);
    }

    @PostMapping("/collections/{collectionId}/add")
    public ResponseEntity<?> addCardToCollection(@PathVariable UUID collectionId,
                                                 @RequestParam String cardId,
                                                 Authentication authentication) {
        Utente utente = userService.getCurrentUser(authentication);
        return ResponseEntity.ok(collectionService.addCardToCollection(collectionId, cardId, utente));
    }
}
