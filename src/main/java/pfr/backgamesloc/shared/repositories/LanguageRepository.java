package pfr.backgamesloc.shared.repositories;

import org.springframework.data.repository.CrudRepository;
import pfr.backgamesloc.games.DAL.entities.Language;

public interface LanguageRepository extends CrudRepository<Language, Integer> {
    Language findByLanguageId(Integer languageId);
}
