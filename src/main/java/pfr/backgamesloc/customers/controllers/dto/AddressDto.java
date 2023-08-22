package pfr.backgamesloc.customers.controllers.dto;

import lombok.Data;
import pfr.backgamesloc.customers.dal.entities.City;

@Data
public class AddressDto {
    private Integer numberAddress;
    private String complementaryNumber;
    private String streetName;
    private String complementaryAddress;
    private City city;
}
