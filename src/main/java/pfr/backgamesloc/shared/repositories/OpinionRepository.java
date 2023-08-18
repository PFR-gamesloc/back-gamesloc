package pfr.backgamesloc.shared.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.expression.spel.ast.OpOr;
import pfr.backgamesloc.shared.entities.Opinion;

import java.util.List;

public interface OpinionRepository extends CrudRepository<Opinion, Integer> {

    List<Opinion> findOpinionsByCustomer_customerId(Integer customerId);
    List<Opinion> findOpinionsByGame_GameId(Integer gameId);
}
