package pfr.backgamesloc.order.repositories;

import org.springframework.data.repository.CrudRepository;
import pfr.backgamesloc.order.entities.Order;

public interface OrderRepository extends CrudRepository<Order, Integer> {

}
