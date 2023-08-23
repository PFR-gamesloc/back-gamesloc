package pfr.backgamesloc.games.services;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import pfr.backgamesloc.customers.dal.CustomerRepository;
import pfr.backgamesloc.customers.dal.entities.Customer;
import pfr.backgamesloc.games.dal.GameRepository;
import pfr.backgamesloc.games.dal.entities.Game;
import pfr.backgamesloc.shared.entities.Opinion;
import pfr.backgamesloc.shared.entities.Order;
import pfr.backgamesloc.shared.repositories.OpinionRepository;
import pfr.backgamesloc.shared.repositories.OrderRepository;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class GameService {


    private final GameRepository gameRepository;

    private final CustomerRepository customerRepository;

    private final OpinionRepository opinionRepository;

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
        Game game = this.gameRepository.findById(gameId).
                orElseThrow(() -> new EntityNotFoundException("this game doesn't exist"));

        this.gameRepository.deleteById(game.getGameId());
        return true;
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
