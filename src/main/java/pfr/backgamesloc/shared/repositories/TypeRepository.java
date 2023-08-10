package pfr.backgamesloc.shared.repositories;

import org.springframework.data.repository.CrudRepository;
import pfr.backgamesloc.games.DAL.entities.Type;

public interface TypeRepository extends CrudRepository<Type, Integer> {
    Type findByTypeId(Integer typeId);
}
