package pfr.backgamesloc.shared.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pfr.backgamesloc.shared.entities.Order;
import pfr.backgamesloc.shared.repositories.OrderRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServices {

    private final OrderRepository orderRepository;

    public List<Order> getAllOrder() {
        return this.orderRepository.findAllByOrderByOrderIdAsc();
    }

}
