package pfr.backgamesloc.games.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pfr.backgamesloc.games.DAL.GameRepository;
import pfr.backgamesloc.games.DAL.entities.Game;

import java.util.List;

@Service
public class GameService {

    @Autowired
    private GameRepository gameRepository;

    public Game getGameById(Integer id) {
        return this.gameRepository.findGameByGameId(id);
    }

    public List<Game> findfavsByCUstomerId(Integer id){
        return this.gameRepository.findGamesByCustomersLike_CustomerId(id);
    }
}
