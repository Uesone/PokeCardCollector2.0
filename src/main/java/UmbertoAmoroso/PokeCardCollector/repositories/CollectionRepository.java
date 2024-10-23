package UmbertoAmoroso.PokeCardCollector.repositories;

import UmbertoAmoroso.PokeCardCollector.entities.Collection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CollectionRepository extends JpaRepository<Collection, Long> {
    // Trova tutte le collezioni per un determinato utente
    List<Collection> findByUser_Id(Long userId);

    // Trova una collezione specifica per un utente
    Optional<Collection> findByIdAndUser_Id(Long collectionId, Long userId);
}
