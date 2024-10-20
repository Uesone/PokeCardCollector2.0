package UmbertoAmoroso.PokeCardCollector.controllers;

import UmbertoAmoroso.PokeCardCollector.entities.Collezione;
import UmbertoAmoroso.PokeCardCollector.services.CollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/admin/collections")
public class AdminBackOfficeController {

    @Autowired
    private CollectionService collectionService;

    // Accessibile solo agli admin per ottenere tutte le collezioni
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<Collezione>> getAllCollections() {
        List<Collezione> collections = collectionService.getAllCollections();
        return ResponseEntity.ok(collections);
    }

    // Accessibile solo agli admin per eliminare una collezione
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{collectionId}")
    public ResponseEntity<Void> deleteCollectionAsAdmin(@PathVariable UUID collectionId) {
        collectionService.deleteCollectionAsAdmin(collectionId);
        return ResponseEntity.noContent().build();
    }
}
