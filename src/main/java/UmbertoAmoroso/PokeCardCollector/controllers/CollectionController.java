package UmbertoAmoroso.PokeCardCollector.controllers;

import UmbertoAmoroso.PokeCardCollector.dto.NewCollectionDTO;
import UmbertoAmoroso.PokeCardCollector.dto.NewCollectionResponseDTO;
import UmbertoAmoroso.PokeCardCollector.entities.Collezione;
import UmbertoAmoroso.PokeCardCollector.services.CollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/collections")
public class CollectionController {

    @Autowired
    private CollectionService collectionService;

    @PostMapping("")
    public NewCollectionResponseDTO saveCollection(@RequestBody NewCollectionDTO body, @RequestParam UUID utenteId) {
        Collezione newCollection = collectionService.save(body, utenteId);
        return new NewCollectionResponseDTO(newCollection.getId());
    }

    @GetMapping("")
    public List<Collezione> getCollections(@RequestParam UUID utenteId) {
        return collectionService.getCollections(utenteId);
    }
}