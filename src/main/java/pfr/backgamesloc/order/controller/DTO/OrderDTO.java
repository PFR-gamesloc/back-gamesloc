package pfr.backgamesloc.order.controller.DTO;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class OrderDTO {

    private Integer orderId;

    private Date orderDate;

    private Date returnDate;

    private Float price;

    @JsonManagedReference
    private CustomerOrderDTO customer;

    @JsonManagedReference
    private List<GameOrderDTO> games;
}
