package pfr.backgamesloc.customers.controllers.DTO;

import lombok.Data;

@Data
public class CustomerToCreateDto {

    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private String phoneNumber;
    private Integer numberAddress;
    private String complementaryNumber;
    private String streetName;
    private String ComplementaryAddress;
    private Integer cityId;

}
