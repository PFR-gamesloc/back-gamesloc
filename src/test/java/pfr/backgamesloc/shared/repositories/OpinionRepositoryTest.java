package pfr.backgamesloc.shared.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import pfr.backgamesloc.shared.entities.Opinion;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class OpinionRepositoryTest {

    @Autowired
    OpinionRepository opinionRepository;

    @Test
    @Sql("findOpinionsByCustomer_customerId.sql")
    public void findOpinionsByCustomer_customerId()
    {
        List<Opinion> opinions = opinionRepository.findOpinionsByCustomer_customerId(1);

        assertEquals(1,opinions.size());
    }

    @Test
    @Sql("findOpinionsByGame_GameId.sql")
    public void findOpinionsByGame_GameId()
    {
        List<Opinion> opinions = opinionRepository.findOpinionsByGame_GameId(1);

        assertEquals(1,opinions.size());
    }


}
