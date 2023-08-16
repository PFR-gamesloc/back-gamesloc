package pfr.backgamesloc.shared.repositories;

import org.springframework.data.repository.CrudRepository;
import pfr.backgamesloc.shared.entities.Order;

import java.util.List;

public interface OrderRepository extends CrudRepository<Order, Integer> {
    List<Order> findOrderByCustomer_CustomerId(Integer id);

    List<Order> findAllByOrderByOrderIdAsc();
}
