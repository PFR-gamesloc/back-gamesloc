package pfr.backgamesloc.shared.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pfr.backgamesloc.games.DAL.entities.Type;

import java.util.Optional;

@Repository
public interface TypeRepository extends CrudRepository<Type, Integer> {
}
