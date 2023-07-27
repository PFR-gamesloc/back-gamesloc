package pfr.backgamesloc.games.DAL;

import org.springframework.data.repository.CrudRepository;
import pfr.backgamesloc.games.DAL.entities.Game;

public interface GameRepository extends CrudRepository<Game, Long> {
}
