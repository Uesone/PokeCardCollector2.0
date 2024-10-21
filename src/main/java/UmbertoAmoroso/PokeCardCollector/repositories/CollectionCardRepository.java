package UmbertoAmoroso.PokeCardCollector.repositories;

import UmbertoAmoroso.PokeCardCollector.entities.Collection;
import UmbertoAmoroso.PokeCardCollector.entities.CollectionCard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CollectionCardRepository extends JpaRepository<CollectionCard, Long> {
    List<CollectionCard> findByCollection(Collection collection);
}