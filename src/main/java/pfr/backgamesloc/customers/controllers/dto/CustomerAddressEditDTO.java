package pfr.backgamesloc.customers.controllers.dto;

import lombok.Data;

@Data
public class CustomerAddressEditDTO {

    private Integer numberAddress;

    private String complementaryNumber;

    private String streetName;

    private String ComplementaryAddress;

    private String postalCode;

    private String cityName;
}
