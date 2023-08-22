package pfr.backgamesloc.games.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import pfr.backgamesloc.customers.dal.CustomerRepository;
import pfr.backgamesloc.customers.dal.entities.Customer;
import pfr.backgamesloc.games.dal.GameRepository;
import pfr.backgamesloc.games.dal.entities.Game;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class GameServiceTest {

    @MockBean
    private GameRepository gameRepository;

    @MockBean
    private CustomerRepository customerRepository;

    @Autowired
    private GameService gameService;

    @Test
    void testGetGameById() {
        Game game = new Game();
                game.setGameId(1);
                game.setGameName("Jeux 1");
        when(gameRepository.findGameByGameId(1)).thenReturn(game);

        Game result = gameService.getGameById(1);

        assertThat(result.getGameName()).isEqualTo("Jeux 1");
    }

    @Test
    void findFavsByCustomerId(){
        Customer customer = new Customer();
        customer.setCustomerId(1);

        List<Game> favoriteGames = new ArrayList<>();
        Game game1 = new Game();
        game1.setGameId(1);
        game1.setGameName("Fav1");
        favoriteGames.add(game1);

        Game game2 = new Game();
        game2.setGameId(2);
        game2.setGameName("Fav2");
        favoriteGames.add(game2);

        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(gameRepository.findGamesByCustomersLike_CustomerId(1)).thenReturn(favoriteGames);

        List<Game> result = gameService.findFavsByCustomerId(1);

        assertEquals(favoriteGames, result);
    }

    @Test
    void getAllGame() {

        List<Game> ListGames = new ArrayList<>();
        Game game1 = new Game();
        game1.setGameId(1);
        game1.setGameName("Fav1");
        ListGames.add(game1);

        Game game2 = new Game();
        game2.setGameId(2);
        game2.setGameName("Fav2");
        ListGames.add(game2);

        when(gameRepository.findAllByOrderByGameIdAsc()).thenReturn(ListGames);

        List<Game> result = gameService.getAll();

        assertEquals(2, result.size());
    }

    @Test
    void createNewGame(){
        Game game = new Game();
        game.setGameId(1);
        game.setGameName("New Game");

        when(gameRepository.save(game)).thenReturn(game);

        Game result = gameService.createANewGame(game);

        assertThat(result.getGameName()).isEqualTo("New Game");
    }

    @Test
    void addGameToFavorites() {
        Customer customer = new Customer();
        customer.setCustomerId(1);
        customer.setFavoriteGames(new ArrayList<>());

        Game game = new Game();
        game.setGameId(1);
        game.setGameName("Hello");
        game.setCustomersLike(new ArrayList<>());

        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(gameRepository.findById(1)).thenReturn(Optional.of(game));

        when(customerRepository.save(customer)).thenReturn(customer);
        List<Game> result = gameService.addGameToFavorites(1, 1);

        assertEquals(1, result.size());
    }

    @Test
    void removeGameToFavorites() {
        Customer customer = new Customer();
        customer.setCustomerId(1);
        customer.setFavoriteGames(new ArrayList<>());

        Game game = new Game();
        game.setGameId(1);
        game.setGameName("Hello");
        game.setCustomersLike(new ArrayList<>());
        game.getCustomersLike().add(customer);
        customer.getFavoriteGames().add(game);


        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(gameRepository.findById(1)).thenReturn(Optional.of(game));

        when(customerRepository.save(customer)).thenReturn(customer);
        List<Game> result = gameService.removeGameToFavorites(1, 1);

        assertEquals(0, result.size());
    }
}
