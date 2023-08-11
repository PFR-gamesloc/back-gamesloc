package pfr.backgamesloc.games.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pfr.backgamesloc.games.DAL.entities.Game;
import pfr.backgamesloc.games.controllers.DTO.GameDTO;
import pfr.backgamesloc.games.services.GameService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/product")
public class GameController {

    @Autowired
    private GameService gameService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("game/{id}")
    public GameDTO getGameById(@PathVariable Integer id) {
        Game game = this.gameService.getGameById(id);
        return this.transformGameTOGameDTO(game);
    }

    @GetMapping("customer/{id}/favs")
    public List<GameDTO> findCustomerFavs(@PathVariable("id") Integer id) {
        List<Game> games = this.gameService.findFavsByCustomerId(id);
        List<GameDTO> gameDTOList = new ArrayList<>();
        for (Game game : games) {
            gameDTOList.add(this.transformGameTOGameDTO(game));
        }
        return gameDTOList;
    }

    @GetMapping("/games")
    public List<GameDTO> getAll() {
        List<Game> games = this.gameService.getAll();
        List<GameDTO> gameDTOS = new ArrayList<>();
        for (Game game : games) {
            gameDTOS.add(this.transformGameTOGameDTO(game));
        }
        return gameDTOS;
    }

    public GameDTO transformGameTOGameDTO(Game game) {
        return this.modelMapper.map(game, GameDTO.class);
    }

}
