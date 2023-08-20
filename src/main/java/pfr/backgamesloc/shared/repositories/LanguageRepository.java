package pfr.backgamesloc.shared.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pfr.backgamesloc.games.DAL.entities.Language;

import java.util.Optional;

@Repository
public interface LanguageRepository extends CrudRepository<Language, Integer> {
}
