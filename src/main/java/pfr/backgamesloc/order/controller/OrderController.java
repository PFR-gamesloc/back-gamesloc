package pfr.backgamesloc.order.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pfr.backgamesloc.order.controller.DTO.OrderDTO;
import pfr.backgamesloc.order.entities.Order;
import pfr.backgamesloc.order.services.OrderServices;

import java.util.ArrayList;
import java.util.List;

@RestController
public class OrderController {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    OrderServices orderServices;

    @GetMapping("/orders")
    public List<OrderDTO> getOrders() {
        List<Order> orders = this.orderServices.getAllOrder();
        List<OrderDTO> orderDTOS = new ArrayList<>();
        for (Order order : orders) {
            orderDTOS.add(this.transformOrderToOrderDTO(order));
        }
        return orderDTOS;
    }

    public OrderDTO transformOrderToOrderDTO(Order order) {
        return this.modelMapper.map(order, OrderDTO.class);
    }
}
