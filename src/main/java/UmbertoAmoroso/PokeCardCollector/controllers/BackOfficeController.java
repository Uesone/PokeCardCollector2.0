package UmbertoAmoroso.PokeCardCollector.controllers;



import UmbertoAmoroso.PokeCardCollector.entities.Collezione;
import UmbertoAmoroso.PokeCardCollector.services.CollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/admin/backoffice")
public class BackOfficeController {

    @Autowired
    private CollectionService collectionService;

    // Visualizza tutte le collezioni (solo per admin)
    @GetMapping("/collections")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Collezione> getAllCollections() {
        return collectionService.getAllCollections();
    }

    // Elimina una collezione (solo per admin)
    @DeleteMapping("/collections/{collectionId}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteCollection(@PathVariable UUID collectionId) {
        collectionService.deleteCollectionAsAdmin(collectionId);
    }
}
