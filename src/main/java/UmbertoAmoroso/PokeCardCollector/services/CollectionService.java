package UmbertoAmoroso.PokeCardCollector.services;

import UmbertoAmoroso.PokeCardCollector.dto.PokemonCardDTO;
import UmbertoAmoroso.PokeCardCollector.dto.NewCollectionDTO;
import UmbertoAmoroso.PokeCardCollector.entities.Collezione;
import UmbertoAmoroso.PokeCardCollector.entities.CollezioneCarta;
import UmbertoAmoroso.PokeCardCollector.entities.Utente;
import UmbertoAmoroso.PokeCardCollector.repositories.CollezioneCartaRepository;
import UmbertoAmoroso.PokeCardCollector.repositories.CollezioneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CollectionService {

    @Autowired
    private CollezioneRepository collezioneRepository;

    @Autowired
    private CollezioneCartaRepository collezioneCartaRepository;

    @Autowired
    private PokemonCardService pokemonCardService;

    // Aggiungi una carta a una collezione (usa NewCardDTO come input)
    public PokemonCardDTO addCardToCollection(UUID collectionId, PokemonCardDTO cardDTO, Utente utente) {
        Collezione collezione = collezioneRepository.findById(collectionId)
                .orElseThrow(() -> new RuntimeException("Collezione non trovata"));

        if (!collezione.getUtente().getId().equals(utente.getId())) {
            throw new RuntimeException("Non autorizzato");
        }

        // Effettuiamo una chiamata all'API Pokemon TCG per ottenere informazioni dettagliate sulla carta
        PokemonCardDTO cardDetails = pokemonCardService.searchCardsByName(cardDTO.getName(), cardDTO.getRarity() != null)
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Carta non trovata"));

        CollezioneCarta collezioneCarta = new CollezioneCarta();
        collezioneCarta.setApiId(cardDetails.getId());  // Usa il metodo getId da PokemonCardDTO
        collezioneCarta.setCollezione(collezione);
        collezioneCarta.setQuantity(cardDTO.getQuantity());
        collezioneCarta.setHolo(cardDTO.getRarity().equals("Holo"));
        collezioneCarta.setCondition(cardDTO.getRarity());

        collezioneCartaRepository.save(collezioneCarta);
        return cardDetails;
    }

    // Crea una nuova collezione per l'utente autenticato
    public Collezione save(NewCollectionDTO body, Utente utente) {
        Collezione collezione = new Collezione();
        collezione.setName(body.getName());
        collezione.setDescription(body.getDescription());
        collezione.setUtente(utente);
        return collezioneRepository.save(collezione);
    }

    // Restituisce tutte le collezioni dell'utente
    public List<Collezione> getCollectionsByUser(UUID utenteId) {
        return collezioneRepository.findByUtenteId(utenteId);
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

        return collezione.getCarte();
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
            throw new RuntimeException("Non autorizzato");
        }

        collezione.setName(body.getName());
        collezione.setDescription(body.getDescription());
        return collezioneRepository.save(collezione);
    }

    // Cancella una collezione per l'utente autenticato
    public void deleteCollection(UUID collectionId, Utente utente) {
        Collezione collezione = collezioneRepository.findById(collectionId)
                .orElseThrow(() -> new RuntimeException("Collezione non trovata"));

        if (!collezione.getUtente().getId().equals(utente.getId())) {
            throw new RuntimeException("Non autorizzato");
        }

        collezioneRepository.delete(collezione);
    }
}
