package pfr.backgamesloc.shared.repositories;

import org.springframework.data.repository.CrudRepository;
import pfr.backgamesloc.games.DAL.entities.Editor;

public interface EditorRepository extends CrudRepository<Editor, Integer> {
    Editor findByEditorId(Integer editorId);
}
