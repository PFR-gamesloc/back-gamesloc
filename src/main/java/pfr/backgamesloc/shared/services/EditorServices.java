package pfr.backgamesloc.shared.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pfr.backgamesloc.games.DAL.entities.Editor;
import pfr.backgamesloc.shared.repositories.EditorRepository;

import java.util.List;

@Service
public class EditorServices {
    @Autowired
    private EditorRepository editorRepository;

    public List<Editor> getAll() {
        return (List<Editor>) this.editorRepository.findAll();
    }

    public Editor getEditorById(Integer editorId) {
        return this.editorRepository.findByEditorId(editorId);
    }
}
