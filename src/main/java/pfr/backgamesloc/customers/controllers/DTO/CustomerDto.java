package pfr.backgamesloc.customers.controllers.DTO;

import lombok.Data;

import pfr.backgamesloc.customers.DAL.entities.Address;

@Data
public class CustomerDto {

    private Integer customerId;

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    private Address address;

}
