package UmbertoAmoroso.PokeCardCollector.controllers;



import UmbertoAmoroso.PokeCardCollector.entities.Collezione;
import UmbertoAmoroso.PokeCardCollector.services.CollectionService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@RestController
@RequestMapping("/admin/backoffice")
public class AdminBackOfficeController {

    @Autowired
    private CollectionService collectionService;

    @GetMapping("/collections")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Collezione> getAllCollections() {
        return collectionService.getAllCollections();
    }

    @DeleteMapping("/collections/{collectionId}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteCollection(@PathVariable UUID collectionId) {
        collectionService.deleteCollectionAsAdmin(collectionId);
    }
}
