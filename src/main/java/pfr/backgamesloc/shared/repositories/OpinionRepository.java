package pfr.backgamesloc.shared.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pfr.backgamesloc.shared.entities.Opinion;

import java.util.List;

@Repository
public interface OpinionRepository extends CrudRepository<Opinion, Integer> {

    List<Opinion> findOpinionsByCustomer_customerId(Integer customerId);
    List<Opinion> findOpinionsByGame_GameId(Integer gameId);
}
