package pfr.backgamesloc.admin.controllers;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pfr.backgamesloc.shared.controller.dto.OrderDTO;
import pfr.backgamesloc.shared.entities.Order;
import pfr.backgamesloc.shared.services.OrderServices;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/admin/order")
@RequiredArgsConstructor
public class AdminOrderController {
    private final ModelMapper modelMapper;

    private final OrderServices orderServices;

    @GetMapping("/all")
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
