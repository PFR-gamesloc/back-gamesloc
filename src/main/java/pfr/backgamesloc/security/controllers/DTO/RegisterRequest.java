package pfr.backgamesloc.security.controllers.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data @RequiredArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

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
