package UmbertoAmoroso.PokeCardCollector.services;

import UmbertoAmoroso.PokeCardCollector.dto.NewCardDTO;
import UmbertoAmoroso.PokeCardCollector.dto.NewCollectionDTO;
import UmbertoAmoroso.PokeCardCollector.dto.PokemonCardDTO;
import UmbertoAmoroso.PokeCardCollector.entities.Collezione;
import UmbertoAmoroso.PokeCardCollector.entities.CollezioneCarta;
import UmbertoAmoroso.PokeCardCollector.entities.Utente;
import UmbertoAmoroso.PokeCardCollector.repositories.CollezioneCartaRepository;
import UmbertoAmoroso.PokeCardCollector.repositories.CollezioneRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Service
public class CollectionService {

    @Autowired
    private CollezioneRepository collezioneRepository;

    @Autowired
    private CollezioneCartaRepository collezioneCartaRepository;

    @Autowired
    private PokemonCardService pokemonCardService;

    // Crea una nuova collezione per l'utente autenticato
    public Collezione save(NewCollectionDTO body, Utente utente) {
        Collezione collezione = new Collezione();
        collezione.setName(body.name());
        collezione.setDescription(body.description());
        collezione.setUtente(utente);
        return collezioneRepository.save(collezione);
    }

    // Restituisce tutte le collezioni dell'utente
    public List<Collezione> getCollectionsByUser(UUID utenteId) {
        return collezioneRepository.findByUtenteId(utenteId);
    }

    // Aggiungi una carta a una collezione
    public CollezioneCarta addCardToCollection(UUID collectionId, NewCardDTO cardDTO, Utente utente) {
        Collezione collezione = collezioneRepository.findById(collectionId)
                .orElseThrow(() -> new RuntimeException("Collezione non trovata"));

        if (!collezione.getUtente().getId().equals(utente.getId())) {
            throw new RuntimeException("Non autorizzato");
        }

        // Chiamata all'API esterna per ottenere i dettagli della carta
        PokemonCardDTO card = pokemonCardService.getCardById(cardDTO.apiId());

        CollezioneCarta collezioneCarta = new CollezioneCarta();
        collezioneCarta.setApiId(card.getId());
        collezioneCarta.setCollezione(collezione);
        collezioneCarta.setQuantity(cardDTO.quantity());
        collezioneCarta.setIsHolo(cardDTO.isHolo());
        collezioneCarta.setCondition(cardDTO.condition());

        return collezioneCartaRepository.save(collezioneCarta);
    }

    // Rimuovi una carta da una collezione
    public void removeCardFromCollection(UUID collectionId, UUID cardId, Utente utente) {
        Collezione collezione = collezioneRepository.findById(collectionId)
                .orElseThrow(() -> new RuntimeException("Collezione non trovata"));

        if (!collezione.getUtente().getId().equals(utente.getId())) {
            throw new RuntimeException("Non autorizzato");
        }

        CollezioneCarta collezioneCarta = collezioneCartaRepository.findById(cardId)
                .orElseThrow(() -> new RuntimeException("Carta non trovata"));

        collezioneCartaRepository.delete(collezioneCarta);
    }

    // Visualizza tutte le carte di una collezione
    public List<CollezioneCarta> getCardsInCollection(UUID collectionId, Utente utente) {
        Collezione collezione = collezioneRepository.findById(collectionId)
                .orElseThrow(() -> new RuntimeException("Collezione non trovata"));

        if (!collezione.getUtente().getId().equals(utente.getId())) {
            throw new RuntimeException("Non autorizzato");
        }

        return collezione.getCarte();  // Restituisce tutte le carte associate alla collezione
    }

    // Restituisce tutte le collezioni del sistema (solo per admin)
    public List<Collezione> getAllCollections() {
        return collezioneRepository.findAll();
    }

    // Cancella una collezione come admin
    public void deleteCollectionAsAdmin(UUID collectionId) {
        Collezione collezione = collezioneRepository.findById(collectionId)
                .orElseThrow(() -> new RuntimeException("Collezione non trovata"));
        collezioneRepository.delete(collezione);
    }

    // Aggiorna una collezione esistente (utente)
    public Collezione updateCollection(UUID collectionId, NewCollectionDTO body, Utente utente) {
        Collezione collezione = collezioneRepository.findById(collectionId)
                .orElseThrow(() -> new RuntimeException("Collezione non trovata"));

        if (!collezione.getUtente().getId().equals(utente.getId())) {
            throw new RuntimeException("Non sei autorizzato a modificare questa collezione");
        }

        collezione.setName(body.name());
        collezione.setDescription(body.description());
        return collezioneRepository.save(collezione);
    }

    // Cancella una collezione per l'utente autenticato
    public void deleteCollection(UUID collectionId, Utente utente) {
        Collezione collezione = collezioneRepository.findById(collectionId)
                .orElseThrow(() -> new RuntimeException("Collezione non trovata"));

        if (!collezione.getUtente().getId().equals(utente.getId())) {
            throw new RuntimeException("Non sei autorizzato a cancellare questa collezione");
        }

        collezioneRepository.delete(collezione);
    }
}




