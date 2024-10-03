package UmbertoAmoroso.PokeCardCollector.repositories;

import UmbertoAmoroso.PokeCardCollector.entities.Collezione;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CollezioneRepository extends JpaRepository<Collezione, UUID> {
    List<Collezione> findByUtenteId(UUID utenteId);
}
