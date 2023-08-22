package pfr.backgamesloc.shared.controller.dto;

import lombok.Data;

@Data
public class CustomerOrderDTO {

    private Integer customerId;

    private String firstName;

    private String lastName;

    private String email;
}
