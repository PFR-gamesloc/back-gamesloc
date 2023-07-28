package pfr.backgamesloc.shared.converters;


import org.hibernate.collection.spi.PersistentBag;
import org.modelmapper.AbstractConverter;
import pfr.backgamesloc.shared.DTO.OrderDTO;
import pfr.backgamesloc.shared.entities.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class OrderListToOrderDTOListConverter extends AbstractConverter<List<Order>, List<OrderDTO>> {

    @Override
    protected List<OrderDTO> convert(List<Order> source){
        List<OrderDTO> orderDTOList = new ArrayList<>();
        for (Order order : source){
            OrderDTO orderDTO = new OrderDTO();
            orderDTO.setOrderDate(order.getOrderDate());
            orderDTO.setOrderId(order.getOrderId());
            orderDTO.setPrice(order.getPrice());
            orderDTO.setReturnDate(order.getReturnDate());

            orderDTOList.add(orderDTO);
        }
        return orderDTOList;
    }
}
