package pfr.backgamesloc.shared.repositories;

import org.springframework.data.repository.CrudRepository;
import pfr.backgamesloc.games.DAL.entities.Tag;

public interface TagRepository extends CrudRepository<Tag, Integer> {
    Tag findByTagId(Integer tagId);
}
