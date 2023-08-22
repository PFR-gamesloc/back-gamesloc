package pfr.backgamesloc.shared.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pfr.backgamesloc.games.dal.entities.Editor;
import pfr.backgamesloc.shared.repositories.EditorRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EditorServices {

    private final EditorRepository editorRepository;

    public List<Editor> getAll() {
        return (List<Editor>) this.editorRepository.findAll();
    }

    public Editor getEditorById(Integer editorId) {
        return this.editorRepository. findById(editorId).orElseThrow(() -> new EntityNotFoundException("this Editor doesn't exist"));
    }
}
