package pfr.backgamesloc.shared.DTO;

import lombok.Data;

import java.util.Date;
@Data
public class OrderDTO {

    private Integer orderId;

    private Date orderDate;

    private Date returnDate;

    private Float price;
}
