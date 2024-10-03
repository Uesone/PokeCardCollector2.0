package UmbertoAmoroso.PokeCardCollector.repositories;

import UmbertoAmoroso.PokeCardCollector.entities.CollezioneCarta;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface CollezioneCartaRepository extends JpaRepository<CollezioneCarta, UUID> {}
