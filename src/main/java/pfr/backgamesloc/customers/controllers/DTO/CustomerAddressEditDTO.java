package pfr.backgamesloc.customers.controllers.DTO;

import lombok.Data;

@Data
public class CustomerAddressEditDTO {

    private Integer numberAddress;

    private String complementaryNumber;

    private String streetName;

    private String ComplementaryAddress;

    private Number postalCode;

    private String cityName;
}
