package pfr.backgamesloc.games.DAL;

import org.springframework.data.repository.CrudRepository;
import pfr.backgamesloc.games.DAL.entities.Game;

import java.util.List;

public interface GameRepository extends CrudRepository<Game, Long> {
}
