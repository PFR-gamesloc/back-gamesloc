package pfr.backgamesloc.games.dal;

import org.springframework.data.repository.CrudRepository;
import pfr.backgamesloc.games.dal.entities.Game;

import java.util.List;

public interface GameRepository extends CrudRepository<Game, Integer> {

    Game findGameByGameId(Integer id);

    List<Game> findGamesByCustomersLike_CustomerId(Integer id);

    List<Game> findAllByOrderByGameIdAsc();

}