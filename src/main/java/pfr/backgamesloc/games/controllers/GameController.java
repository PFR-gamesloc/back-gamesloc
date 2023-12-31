package pfr.backgamesloc.games.controllers;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pfr.backgamesloc.admin.services.FileStorageService;
import pfr.backgamesloc.customers.controllers.dto.CustomerOpinionDto;
import pfr.backgamesloc.games.dal.entities.Game;
import pfr.backgamesloc.games.controllers.dto.GameDTO;
import pfr.backgamesloc.games.controllers.dto.OpinionDTO;
import pfr.backgamesloc.games.services.GameService;
import pfr.backgamesloc.shared.entities.Opinion;

import java.io.EOFException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;

    private final ModelMapper modelMapper;

    private final FileStorageService storageService;

    @GetMapping("game/{id}")
    public ResponseEntity<GameDTO> getGameById(@PathVariable Integer id) {
        Game game = this.gameService.getGameById(id);
        return  new ResponseEntity<>(this.modelMapper.map(game, GameDTO.class), HttpStatus.OK);
    }

    @GetMapping("customer/{id}/favs")
    public ResponseEntity<List<GameDTO>> findCustomerFavs(@PathVariable("id") Integer id) {
        List<Game> games = this.gameService.findFavsByCustomerId(id);
        List<GameDTO> gameDTOList = new ArrayList<>();
        for (Game game : games) {
            gameDTOList.add(this.modelMapper.map(game, GameDTO.class));
        }
        return new ResponseEntity<>(gameDTOList, HttpStatus.OK);
    }

    @GetMapping("/games")
    public ResponseEntity<List<GameDTO>> getAll() {
        List<Game> games = this.gameService.getAll();
        List<GameDTO> gameDTOS = new ArrayList<>();
        for (Game game : games) {
            gameDTOS.add(this.modelMapper.map(game, GameDTO.class));
        }
        return  new ResponseEntity<>(gameDTOS, HttpStatus.OK);
    }

    @GetMapping("/{id}/comments")
    public ResponseEntity<List<OpinionDTO>> getAllComments(@PathVariable("id") Integer gameId){
        List<OpinionDTO> opinionDTOList = new ArrayList<>();
        List<Opinion>  opinions = this.gameService.findAllOpinions(gameId);
        for (int i = 0; i < opinions.size(); i++){
            opinionDTOList.add(modelMapper.map(opinions.get(i), OpinionDTO.class));
            opinionDTOList.get(i).setCustomer(new CustomerOpinionDto());
            opinionDTOList.get(i).getCustomer().setFirstName(opinions.get(i).getCustomer().getFirstName());
            opinionDTOList.get(i).getCustomer().setLastName(opinions.get(i).getCustomer().getLastName());
            opinionDTOList.get(i).getCustomer().setEmail(opinions.get(i).getCustomer().getEmail() );
        }

        return new ResponseEntity<>(opinionDTOList,HttpStatus.OK);
    }

    @GetMapping("/img/{fileName:.+}")
    public ResponseEntity<UrlResource> getListFiles(@PathVariable("fileName") String fileName) throws MalformedURLException, EOFException {
        UrlResource file = storageService.load(fileName);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(file);
    }

}
