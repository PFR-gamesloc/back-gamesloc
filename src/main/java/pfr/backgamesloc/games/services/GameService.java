package pfr.backgamesloc.games.services;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import pfr.backgamesloc.customers.DAL.CustomerRepository;
import pfr.backgamesloc.customers.DAL.entities.Customer;
import pfr.backgamesloc.games.DAL.GameRepository;
import pfr.backgamesloc.games.DAL.entities.Game;
import pfr.backgamesloc.games.controllers.DTO.GameEditDTO;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

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

    public Game createANewGame(GameEditDTO gameEditDTO) throws IOException {
        MultipartFile file = gameEditDTO.getImage();
        byte[] bytes = file.getBytes();
        System.out.println(file.getContentType());

        return this.gameRepository.save(new Game());
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

    public void editGameById(Integer gameId, Game game) {
        game.setGameId(gameId);
//        return this.createANewGame(game);
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
}
