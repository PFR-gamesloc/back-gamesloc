package pfr.backgamesloc.games.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pfr.backgamesloc.games.DAL.entities.Game;
import pfr.backgamesloc.games.controllers.DTO.GameDTO;
import pfr.backgamesloc.games.services.GameService;

@RestController
public class GameController {

    @Autowired
    private GameService gameService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("game/{id}")
    public GameDTO getGameById(@PathVariable Integer id){
        Game game = this.gameService.getGameById(id);
        return this.transformGameToGameDTO(game);
    }

    private GameDTO transformGameToGameDTO(Game game){
        return this.modelMapper.map(game, GameDTO.class);
    }
}
