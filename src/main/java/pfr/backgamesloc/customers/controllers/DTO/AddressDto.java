package pfr.backgamesloc.customers.controllers.DTO;

import lombok.Data;
import pfr.backgamesloc.customers.DAL.entities.City;

@Data
public class AddressDto {
    private Integer numberAddress;
    private String complementaryNumber;
    private String streetName;
    private String ComplementaryAddress;
    private City city;
}
