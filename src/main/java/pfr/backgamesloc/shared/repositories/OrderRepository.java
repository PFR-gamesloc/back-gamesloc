package pfr.backgamesloc.shared.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pfr.backgamesloc.shared.entities.Order;

import java.util.List;

@Repository
public interface OrderRepository extends CrudRepository<Order, Integer> {
    List<Order> findOrderByCustomer_CustomerId(Integer id);

    List<Order> findAllByOrderByOrderIdAsc();
}
