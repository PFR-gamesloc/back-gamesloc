package pfr.backgamesloc.games.services;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import pfr.backgamesloc.customers.DAL.CustomerRepository;
import pfr.backgamesloc.customers.DAL.entities.Customer;
import pfr.backgamesloc.games.DAL.GameRepository;
import pfr.backgamesloc.games.DAL.entities.Game;
import pfr.backgamesloc.shared.controller.DTO.OrderDTO;
import pfr.backgamesloc.shared.entities.Opinion;
import pfr.backgamesloc.shared.repositories.OpinionRepository;
import pfr.backgamesloc.games.controllers.DTO.GameEditDTO;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class GameService {


    private final GameRepository gameRepository;

    private final CustomerRepository customerRepository;

    private final OpinionRepository opinionRepository;

    private final ModelMapper modelMapper;

    public Game getGameById(Integer id) {
        return this.gameRepository.findGameByGameId(id);
    }

    public List<Game> findFavsByCustomerId(Integer id) {
        return this.gameRepository.findGamesByCustomersLike_CustomerId(id);
    }

    public List<Game> getAll() {
        return this.gameRepository.findAllByOrderByGameIdAsc();
    }

    public Game createANewGame(Game game) {
        return this.gameRepository.save(game);
    }

    public Game editGameById(Integer gameId, Game game) {
        game.setGameId(gameId);
        return this.createANewGame(game);
    }

    public boolean deleteById(Integer gameId) {
        Optional<Game> gameOptional = this.gameRepository.findById(gameId);

        if (gameOptional.isPresent()) {
            this.gameRepository.deleteById(gameId);
            return true;
        } else {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Game cannot be deleted");
        }
    }

    private Game transformGameEditDTOToGame(GameEditDTO gameEditDTO) {
        return modelMapper.map(gameEditDTO, Game.class);
    }

    public List<Game> addGameToFavorites(Integer customerId, Integer gameId) {
        Customer customer = customerRepository.findById(Long.valueOf(customerId))
                .orElseThrow(() -> new EntityNotFoundException("Customer not found"));

        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new EntityNotFoundException("Game not found"));
        customer.getFavoriteGames().add(game);
        game.getCustomersLike().add(customer);
        customerRepository.save(customer);

        return customerRepository.save(customer).getFavoriteGames();
    }

    public List<Game> removeGameToFavorites(Integer customerId, Integer gameId) {
        Customer customer = customerRepository.findById(Long.valueOf(customerId))
                .orElseThrow(() -> new EntityNotFoundException("Customer not found"));

        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new EntityNotFoundException("Game not found"));
        customer.getFavoriteGames().remove(game);
        game.getCustomersLike().remove(customer);
        customerRepository.save(customer);

        return customerRepository.save(customer).getFavoriteGames();
    }

    public List<Opinion> findAllOpinions(Integer gameId) {
        return this.opinionRepository.findOpinionsByGame_GameId(gameId);
    }
}
