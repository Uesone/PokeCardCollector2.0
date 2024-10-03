package UmbertoAmoroso.PokeCardCollector.services;

import UmbertoAmoroso.PokeCardCollector.dto.NewCollectionDTO;
import UmbertoAmoroso.PokeCardCollector.entities.Collezione;
import UmbertoAmoroso.PokeCardCollector.entities.Utente;
import UmbertoAmoroso.PokeCardCollector.repositories.CollezioneRepository;
import UmbertoAmoroso.PokeCardCollector.repositories.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CollectionService {

    @Autowired
    private CollezioneRepository collezioneRepository;

    // Salva una nuova collezione per l'utente autenticato
    public Collezione save(NewCollectionDTO body, Utente utente) {
        Collezione newCollection = new Collezione();
        newCollection.setName(body.name());
        newCollection.setDescription(body.description());
        newCollection.setUtente(utente);
        return collezioneRepository.save(newCollection);
    }

    // Recupera tutte le collezioni dell'utente
    public List<Collezione> getCollectionsByUser(UUID utenteId) {
        return collezioneRepository.findByUtenteId(utenteId);
    }

    // Aggiorna una collezione esistente
    public Collezione updateCollection(UUID collectionId, NewCollectionDTO body, Utente utente) {
        Collezione collection = collezioneRepository.findById(collectionId)
                .orElseThrow(() -> new RuntimeException("Collezione non trovata"));

        if (!collection.getUtente().getId().equals(utente.getId())) {
            throw new RuntimeException("Non sei autorizzato a modificare questa collezione");
        }

        collection.setName(body.name());
        collection.setDescription(body.description());
        return collezioneRepository.save(collection);
    }

    // Cancella una collezione esistente
    public void deleteCollection(UUID collectionId, Utente utente) {
        Collezione collection = collezioneRepository.findById(collectionId)
                .orElseThrow(() -> new RuntimeException("Collezione non trovata"));

        if (!collection.getUtente().getId().equals(utente.getId())) {
            throw new RuntimeException("Non sei autorizzato a eliminare questa collezione");
        }

        collezioneRepository.delete(collection);
    }

    // Metodo backoffice per visualizzare tutte le collezioni (solo per admin)
    public List<Collezione> getAllCollections() {
        return collezioneRepository.findAll();
    }

    // Metodo backoffice per eliminare una collezione come admin
    public void deleteCollectionAsAdmin(UUID collectionId) {
        Collezione collection = collezioneRepository.findById(collectionId)
                .orElseThrow(() -> new RuntimeException("Collezione non trovata"));
        collezioneRepository.delete(collection);
    }
}


