package pfr.backgamesloc.games.services;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pfr.backgamesloc.customers.DAL.CustomerRepository;
import pfr.backgamesloc.customers.DAL.entities.Customer;
import pfr.backgamesloc.games.DAL.GameRepository;
import pfr.backgamesloc.games.DAL.entities.Game;

import java.util.List;

@Service
@Transactional
public class GameService {

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private CustomerRepository customerRepository;

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

    public Game editGameById(Integer gameId, Game game) {
        game.setGameId(gameId);
        return this.createANewGame(game);
    }

    public void deleteById(Integer gameId) {
        this.gameRepository.deleteById(gameId);
    }
}
