package pfr.backgamesloc.games.dal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import pfr.backgamesloc.games.dal.entities.Game;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;

@DataJpaTest
public class GameRepositoryTest {

    @Autowired
    private GameRepository gameRepository;


    @Test
    @Sql("GameBDDTest.sql")
    public void findAllByOrderByGameIdAsc() {
        List<Game> games = gameRepository.findAllByOrderByGameIdAsc();
        assertThat(games).hasSize(3);
        assertThat(games.get(0).getGameId()).isEqualTo(1);
        assertThat(games.get(1).getGameId()).isEqualTo(2);
        assertThat(games.get(2).getGameId()).isEqualTo(3);
    }

    @Test
    @Sql("GameBDDTestLikeCustomer.sql")
    public void findGamesByCustomersLike_CustomerId() {
        List<Game> games = gameRepository.findGamesByCustomersLike_CustomerId(1);
        assertThat(games.get(0).getGameName()).isEqualTo("Game 1");
       // assertThat(games.get(1).getGameName()).isEqualTo("Game 2");
    }
}