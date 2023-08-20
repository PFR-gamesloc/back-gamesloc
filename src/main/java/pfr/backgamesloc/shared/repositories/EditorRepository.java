package pfr.backgamesloc.shared.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pfr.backgamesloc.games.DAL.entities.Editor;

@Repository
public interface EditorRepository extends CrudRepository<Editor, Integer> {
}
