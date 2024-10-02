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

    @Autowired
    private UtenteRepository utenteRepository;

    public Collezione save(NewCollectionDTO body, UUID utenteId) {
        Utente utente = utenteRepository.findById(utenteId).orElseThrow(() -> new RuntimeException("Utente non trovato"));
        Collezione newCollection = new Collezione();
        newCollection.setName(body.name());
        newCollection.setDescription(body.description());
        newCollection.setUtente(utente);
        return collezioneRepository.save(newCollection);
    }

    public List<Collezione> getCollections(UUID utenteId) {
        return collezioneRepository.findByUtenteId(utenteId);
    }
}