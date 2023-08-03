package pfr.backgamesloc.shared.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pfr.backgamesloc.shared.entities.Order;
import pfr.backgamesloc.shared.repositories.OrderRepository;

import java.util.List;

@Service
public class OrderServices {

    @Autowired
    private OrderRepository orderRepository;

    public List<Order> getAllOrder() {
        return (List<Order>) this.orderRepository.findAll();
    }

}
