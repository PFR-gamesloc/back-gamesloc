package pfr.backgamesloc.customers.controllers.DTO;

import lombok.Data;

import pfr.backgamesloc.customers.DAL.entities.Address;
import pfr.backgamesloc.order.entities.Opinion;
import pfr.backgamesloc.order.entities.Order;

import java.util.List;

@Data
public class CustomerDto {

    private Integer customerId;

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    private Address address;

    private List<Order> orders;

    private List<Opinion> opinions;







}
