package pfr.backgamesloc.shared.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pfr.backgamesloc.games.dal.entities.Tag;
@Repository
public interface TagRepository extends CrudRepository<Tag, Integer> {
}
