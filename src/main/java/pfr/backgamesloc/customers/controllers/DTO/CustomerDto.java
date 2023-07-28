package pfr.backgamesloc.customers.controllers.DTO;

import lombok.Data;
import pfr.backgamesloc.adresses.DAL.entities.Address;
import pfr.backgamesloc.orders.DAL.entities.Order;

import java.util.List;

@Data
public class CustomerDto {

    private Integer customerId;

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    private Address address;
}
