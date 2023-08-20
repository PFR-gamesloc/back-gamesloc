package pfr.backgamesloc.shared.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pfr.backgamesloc.games.DAL.entities.Tag;
import pfr.backgamesloc.shared.repositories.TagRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagServices {

    private final TagRepository tagRepository;

    public List<Tag> getAll() {
        return (List<Tag>) this.tagRepository.findAll();
    }

    public Tag getTagById(Integer tagId) {
        return this.tagRepository.findById(tagId).orElseThrow(() -> new EntityNotFoundException("this tag doesn't exist"));
    }
}
