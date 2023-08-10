package pfr.backgamesloc.shared.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pfr.backgamesloc.games.DAL.entities.Tag;
import pfr.backgamesloc.shared.repositories.TagRepository;

import java.util.List;

@Service
public class TagServices {
    @Autowired
    private TagRepository tagRepository;

    public List<Tag> getAll() {
        return (List<Tag>) this.tagRepository.findAll();
    }

    public Tag getTagById(Integer tagId) {
        return this.tagRepository.findByTagId(tagId);
    }
}
